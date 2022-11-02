package com.sk.signet.onm.common.fileutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExcelUtil {
    
    public void excelDown(Map<String, Object> paramMap, List<Map<String, Object>> dataListMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        if(dataListMap.size() > 0) {
                                                                            // Sample Data 
            String paramExcelHeader = (String) paramMap.get("excelHeader"); // 사업자 구분,사업자명,사용자 ID,사용자명,전화번호,핸드폰번호,이메일,직급,등록일,탈퇴일
            String paramExcelKey = (String) paramMap.get("excelKey");       // bizTypeNm,companyNm,userId,userNm,telNo,mobileNo,email,titleNm,joinDt,withdrawalDt
            String paramExcelWidth = (String) paramMap.get("excelWidth");   // 150,150,150,150,150,150,180,100,100,100
            String pageId = (String) paramMap.get("pageId");                // userMain
            
            int girdWithChg = 35;   //엑셀 with계산시 곱할 수
            
            String[] headerList = paramExcelHeader.toString().split(",");   // [사업자 구분, 사업자명, 사용자 ID, 사용자명, 전화번호, 핸드폰번호, 이메일, 직급, 등록일, 탈퇴일]
            String[] keyList = paramExcelKey.toString().split(",");         // [bizTypeNm, companyNm, userId, userNm, telNo, mobileNo, email, titleNm, joinDt, withdrawalDt]
            String[] widthList = null;
            
            String excelDataType = (String) paramMap.get("excelDataType");  // string,string,string,string,string,string,string,string,string,string
            String[] excelDataTypeArr = excelDataType.split(",");           // [string, string, string, string, string, string, string, string, string, string]

            if (paramExcelWidth != null) {
                widthList = paramExcelWidth.toString().split(",");          // widthList : [string, string, string, string, string, string, string, string, string, string]
            }
            
            int row = 3;
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("List");

            // 제목 폰트
            XSSFFont nameFont = workbook.createFont();
            nameFont.setBold(true);
            nameFont.setFontHeightInPoints((short) 16);

            // 제목 스타일
            CellStyle nameStyle = workbook.createCellStyle();
            nameStyle.setAlignment(HorizontalAlignment.CENTER);
            nameStyle.setFont(nameFont);

            // 데이터 헤더 스타일
            XSSFCellStyle headerStyle = setHeaderStyle( workbook );
            
            // 데이터 스타일
            XSSFCellStyle bodyStyle = setBodyStyle( workbook );
            
            // 데이터 입력
            int idxCreateRow = 0;
            int maxCol = 0;
            int maxColTitle = 0;

            // 헤더그리기
            Row bodyRow = null;
            idxCreateRow = row;
            bodyRow = sheet.createRow(idxCreateRow);
            
            //엑셀의 헤더 생성 분기처리
            // setHeight 헤더의 높이 지정, setWrapText 헤더의 문자 개행처리 유무 \n 으로 개행함.
//            if("chargeHistKepcoPayType".equals(pageId) ){
//                bodyRow.setHeight((short)700);
//                headerStyle.setWrapText(true);
//            }
            // 1st 헤더를 그리자
            for (int i = 0; i < headerList.length; i++) {   // headerList=[사업자 구분, 사업자명, 사용자 ID, 사용자명, 전화번호, 핸드폰번호, 이메일, 직급, 등록일, 탈퇴일]
                Cell cell = null;
                int clounmWidth = 200;
                clounmWidth = Integer.parseInt(widthList[i]) * girdWithChg;
                sheet.setColumnWidth(i, clounmWidth); // 컬럼 width 조정
                cell = bodyRow.createCell(i);
                cell.setCellValue(headerList[i]);
                cell.setCellStyle(headerStyle);
                
            }

            row++;

            int col = 0;
            
            //해당데이타가 int형인지 string 배열값이 해당 열과 길이가 같은지.
            Boolean excelTp = keyList.length == excelDataTypeArr.length ? true : false;
            
            for (int i = 0; i < dataListMap.size(); i++) {
                col = 0;
                Row bodyRow1 = sheet.createRow(row + i);
//                Map<String, String> excelData = excelList.get(i);
                
                Map<String, Object> excelData = dataListMap.get(i);
                
                for (int j = 0; j < keyList.length; j++) {

                    Cell cell = bodyRow1.createCell(col++);
                    cell.setCellStyle(bodyStyle);
                    
                    String excelVal = (String) excelData.get(keyList[j]);
                    if( excelTp && "int".equals(excelDataTypeArr[j]) ) {
                        cell.setCellValue(NumberUtils.toDouble(excelVal));
                    } else {
                        cell.setCellValue(excelVal);
                    }
                    
                }
                
                if (maxCol < col) {
                    maxCol = col;
                }
                
            }
            
            maxColTitle = maxCol;

            String excelNm = (String) paramMap.get("excelNm");

            // 제목 MERGE
            sheet.addMergedRegion(new CellRangeAddress(1, // 시작 행번호
                    1, // 마지막 행번호
                    0, // 시작 열번호
                    maxColTitle - 1 // 마지막 열번호
            ));

            Row nameRow = sheet.createRow(1);
            Cell nameCell = nameRow.createCell(0);
            nameCell.setCellValue(excelNm);
            nameCell.setCellStyle(nameStyle);

            // 시간 표기
            Row dateRow = sheet.createRow(2);
            Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            // 다운로드
            download(workbook, res, req, excelNm);
        }
    }

    
    @GetMapping(value = "/excelDownBig")
    public void excelDownBig() {
        
    }
    
    @PostMapping(value = "/excelUpload")
    public void excelUpload() {
        
    }

    
    private XSSFCellStyle setHeaderStyle( XSSFWorkbook workbook ) {
        // 데이터 헤더 폰트
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        byte[] b = { (byte) 0xE0, (byte) 0xE0, (byte) 0xE0 };
        
        XSSFColor backgroundColor = new XSSFColor(b, new DefaultIndexedColorMap());
        headerStyle.setFillForegroundColor(backgroundColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.index);
        headerStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         
        return headerStyle;
    }
    
    private XSSFCellStyle setBodyStyle( XSSFWorkbook workbook ) {
        XSSFCellStyle bodyStyle = workbook.createCellStyle();
        
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        bodyStyle.setTopBorderColor(IndexedColors.BLACK.index);
        bodyStyle.setLeftBorderColor(IndexedColors.BLACK.index);
        bodyStyle.setRightBorderColor(IndexedColors.BLACK.index);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         
        return bodyStyle;
    }
    
    private void download(XSSFWorkbook workbook, HttpServletResponse res, HttpServletRequest req, String strFileName) throws Exception {
        strFileName += "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        res.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(strFileName, "utf-8") + ".xlsx");
        res.setHeader("Content-disposition", "attachment;filename=" 
                + java.net.URLEncoder.encode(strFileName, "utf-8").replace("+", "%20").replace("%28","(").replace("%29",")") + ".xlsx");
        // res.setHeader("Content-Type", "application/vnd.ms-excel; charset=utf-8");
        res.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
        res.setHeader("Content-Transfer-Encoding", "binary;");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "fileDownload=true; path=/");
        
        ServletOutputStream out = res.getOutputStream();
        workbook.write(out);
        out.close();
        workbook.close();
    }
}

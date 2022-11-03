package com.sk.signet.onm.web.user.controller;


/**
 * 사용자 정보 조회 컨트롤러 
 * 
 * @packagename : com.sk.signet.onm.web.user.controller
 * @filename 	: UserController.java 
 * @since 		: 2022.10.04 
 * @description : 
 * =================================================================
 * Date				Author			Version			Note			
 * -----------------------------------------------------------------
 * 2022.10.04 		Heo, Sehwan		1.0				최초 생성
 * -----------------------------------------------------------------
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.signet.onm.common.auth.service.AuthFeignClient;
import com.sk.signet.onm.common.auth.service.AuthService;
import com.sk.signet.onm.web.user.domain.User;
import com.sk.signet.onm.web.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name="사용자", description="사용자 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/users")
	@Operation(summary = "사용자 리스트 조회", description="사용자 리스트 조회(100건)")
    public ResponseEntity<Map<String, Object>> getUsers() {
		
	    Map<String, Object> paramMap = new HashMap();
		List<Map<String, Object>> result = service.selectUserList(paramMap);
		Map<String, Object> data = new HashMap<>();
		data.put("data", result);
		
		log.debug("data:"+data);
		return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
	
	
	@Autowired
    private static AuthFeignClient authFeignClient;
	
	@GetMapping("/users/{userId}")
	@Operation(summary = "사용자조회", description="ID로 사용자 조회")
	public ResponseEntity<Map<String, Object>> getUser(@Parameter(name="userId", description="테스트ID : INSOFT1") @PathVariable String userId, HttpServletRequest req) {
	    
	    Map<String, Object> searchInfo = new HashMap<>();
	    Map<String, Object> data = new HashMap<>();
		searchInfo.put("userId", userId);
		
		
		boolean isAuth = authService.isAuthenticated(searchInfo, req);
		
		if(isAuth) {
		    User user = service.selectUser(searchInfo);
	        data.put("data", user);
		} else {
		    log.debug("authInfo : {}", data);
		    data.put("data", "fail");
            return new ResponseEntity<Map<String, Object>>(data, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
	}
	
	
	@PostMapping("/users/{userId}")
	@Operation(summary = "사용자정보수정", description="사용자 정보수정")
	public Map<String, Object> updateUser(@RequestBody Map<String, Object> paramMap) {
		
		/**
		 * TEST Data
		 * {
			  "userId": "INSOFT1",
			  "email": "jongmo.jeon@signet.com"
			}
		 */
		
		User result = service.updateUser(paramMap);
		Map<String, Object> data = new HashMap<>();
		data.put("data", result);
		
		return data;
	}
	
	
	@GetMapping("/users/excel")
    @Operation(summary = "사용자 엑셀다운로드", description="사용자 엑셀다운로드")
//	public ResponseEntity<Map<String, Object>> getUserExcelDown(@RequestParam Map<String, Object> paramMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
	public void getUserExcelDown(@RequestParam Map<String, Object> paramMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
	    
	    // test data
//	    {
//	        "isExcel":"excel"
//	    }
	    
	    
	    // TEST Data Set-up
	    String isExcel = "excel";
	    String excelNm = "사용자관리";
        String excelHeader = "사업자 구분,사업자명,사용자 ID,사용자명,전화번호,핸드폰번호,이메일,직급,등록일,탈퇴일";
        String excelKey = "bizTypeNm,companyNm,userId,userNm,telNo,mobileNo,email,titleNm,joinDt,withdrawalDt";
        String excelWidth = "150,150,150,150,150,150,180,100,100,100";
        String excelDataType = "string,string,string,string,string,string,string,string,string,string";
        
        paramMap.put("excelHeader", excelHeader);
        paramMap.put("excelKey", excelKey);
        paramMap.put("excelWidth", excelWidth);
        paramMap.put("excelDataType", excelDataType);
        paramMap.put("excelNm", excelNm);
        paramMap.put("isExcel", isExcel);
        
        service.selectUserListExcel(paramMap, req, res);
	    
//        Map<String, Object> result = service.selectUserListExcel(paramMap, req, res);
//        Map<String, Object> data = new HashMap<>();
//        data.put("data", result);
        
//        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
	
	
	
	
	
}

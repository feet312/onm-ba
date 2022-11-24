package com.sk.signet.onm.web.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.signet.onm.common.fileutil.ExcelUtil;
import com.sk.signet.onm.common.utils.StringUtil;
import com.sk.signet.onm.mapper.user.UserMapper;
import com.sk.signet.onm.web.user.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })	
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ExcelUtil excel;
	
	
//	public List<Map<String, Object>> selectUserList() {
//		List<Map<String, Object>> data = mapper.selectUserList();
//		return data;
//	}
	
	/**
	 * 사용자 단건 조회
	 * @param searchInfo
	 * @return
	 */
	public User selectUser(Map<String, Object> searchInfo) {
		User user = userMapper.selectUser(searchInfo);
		return user;
	}
	
	/**
	 * 사용자 정보 등록 
	 * @param userInfo
	 * @return
	 */
	public User insertUser(Map<String, Object> userInfo) {
        
        int result = userMapper.insertUser(userInfo);
        log.debug("insert result : "+ result);
        User resultData = userMapper.selectUser(userInfo);      
        
        return resultData;
    }
	
	/**
	 * 사용자 정보 수정
	 * @param userInfo
	 * @return
	 */
	public User updateUser(Map<String, Object> userInfo) {
		
		int result = userMapper.updateUser(userInfo);
		log.debug("update result : "+ result);
		User resultData = userMapper.selectUser(userInfo);		
		
		return resultData;
	}
	
	/**
	 * 사용자 리스트 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectUserList(Map<String, Object> param) {
	    List<Map<String, Object>> data = userMapper.selectUserList(param);
        return data;
    }
	
	/**
	 * 사용자 엑셀 다운로드
	 * @param param
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
//	public Map<String, Object> selectUserListExcel(Map<String, Object> param, HttpServletRequest req, HttpServletResponse res) throws Exception {
    public void selectUserListExcel(Map<String, Object> param, HttpServletRequest req, HttpServletResponse res) throws Exception {
//        Map<String, Object> data = new HashMap<String, Object>();
        excel.excelDown(param, userMapper.selectUserList(param), req, res);
//        data.put("data", "succes");
//        return data;
    }
	
	
	
}

package com.sk.signet.onm.web.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.signet.onm.mapper.user.UserMapper;
import com.sk.signet.onm.web.user.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("customerService")
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })	
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	
	public List<Map<String, Object>> selectUserList() {
		List<Map<String, Object>> data = mapper.selectUserList();
		return data;
	}
	
	public List<Map<String, Object>> selectUser(Map<String, Object> searchInfo) {
		List<Map<String, Object>> data = mapper.selectUser(searchInfo);
		return data;
	}
	
	public User selectUser2(Map<String, Object> searchInfo) {
		User user = mapper.selectUser2(searchInfo);
		return user;
	}
	
	public List<Map<String, Object>> updateUser(Map<String, Object> userInfo) {
		
		int result = mapper.updateUser(userInfo);
		log.debug("update result : "+ result);
		List<Map<String, Object>> resultData = mapper.selectUser(userInfo);		
		
		return resultData;
	}
}

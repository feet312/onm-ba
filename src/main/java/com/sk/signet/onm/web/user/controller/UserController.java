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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/users")
	@Operation(summary = "사용자 리스트 조회", description="사용자 리스트 조회(100건)")
    public ResponseEntity<Map<String, Object>> getUsers() {
		
		List<Map<String, Object>> result = service.selectUserList();
		Map<String, Object> data = new HashMap<>();
		data.put("data", result);
		
		log.debug("data:"+data);
		return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
	
	
	@GetMapping("/users/{userId}")
	@Operation(summary = "사용자조회", description="ID로 사용자 조회", security = @SecurityRequirement(name="bearer"))
	public ResponseEntity<Map<String, Object>> getUser(@Parameter(name="userId", description="테스트ID : INSOFT1") @PathVariable String userId) {
		Map<String, Object> searchInfo = new HashMap<>();
		searchInfo.put("userId", userId);
				
		User user = service.selectUser(searchInfo);
		Map<String, Object> data = new HashMap<>();
		data.put("data", user);
		
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
	
	
	
	
	
}

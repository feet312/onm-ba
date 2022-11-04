package com.sk.signet.onm.common.auth.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Auth 검증 관련 OpenFeign Interface 
 * 
 * @packagename : com.sk.signet.onm.common.auth.service
 * @filename 	: AuthFeignClient.java 
 * @since 		: 2022.11.04 
 * @description : 
 * =================================================================
 * Date				Author			Version			Note			
 * -----------------------------------------------------------------
 * 2022.11.04 		Heo, Sehwan		1.0				최초 생성
 * -----------------------------------------------------------------
 */

@FeignClient(name="${feign.api.auth.name}", url="${feign.api.auth.url}")
public interface AuthFeignClient {
    
    
    @RequestMapping(method=RequestMethod.POST, value="/user/authCheck")
    public Map<String, Object> userAuthCheck(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, Map<String, Object> reqBody);


}

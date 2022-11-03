package com.sk.signet.onm.common.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthFeignClient authFeignClient;
    
    @Autowired
    private JwtService jwtService;
    
    
    public Map<String, Object> userAuthCheck(Map<String, Object> reqBody, HttpServletRequest req) {
        Map<String, Object> data = new HashMap<String, Object>();
        
        try {
            String token = jwtService.getToken(req);
            
            log.info("AuthService reqBody : {}", reqBody);
            data = authFeignClient.userAuthCheck(token, reqBody);
            log.info("AuthService result : {}", data);
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        return data;
    }
    
    
    public boolean isAuthenticated(Map<String, Object> reqBody, HttpServletRequest req) {
        boolean isAuth = false;
        try {
            
            Map<String, Object> data = new HashMap<String, Object>();            
            data = this.userAuthCheck(reqBody, req);
            
            if(null != data) {
                if(data.get("data").equals("success")) {
                    isAuth = true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        
        return isAuth;
    }
    
}

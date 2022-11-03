package com.sk.signet.onm.common.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    /**
     * OpenFeign 으로 JWTToken 사용자 검증 
     * @param reqBody
     * @param req
     * @param res
     * @return
     */
    public Map<String, Object> userAuthCheck(Map<String, Object> reqBody, HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> data = new HashMap<String, Object>();
        
        try {
            
            String token = jwtService.getToken(req);            
            data = authFeignClient.userAuthCheck(token, reqBody);
            
            String newToken=jwtService.refreshToken(token);            
            res.setHeader("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION);
            res.setHeader(HttpHeaders.AUTHORIZATION, newToken); //요청 웹브라우즈에 새로 생성한 token 되돌려줌.
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        return data;
    }
    
    /**
     * 사용자 인증 여부 확인 
     * @param reqBody
     * @param req
     * @param res
     * @return
     */
    public boolean isAuthenticated(Map<String, Object> reqBody, HttpServletRequest req, HttpServletResponse res) {
        boolean isAuth = false;
        try {
            
            Map<String, Object> data = new HashMap<String, Object>();            
            data = this.userAuthCheck(reqBody, req, res);
            
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

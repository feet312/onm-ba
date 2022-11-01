package com.sk.signet.onm.web.customer.controller;

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

import com.sk.signet.onm.web.customer.controller.CustomerController;
import com.sk.signet.onm.web.customer.domain.Customer;
import com.sk.signet.onm.web.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name="고객", description="고객 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService service;
    
    @GetMapping("/customers")
    @Operation(summary = "고객 리스트 조회", description="고객 리스트 조회(100건)")
    public ResponseEntity<Map<String, Object>> getCustomers() {
        
        List<Map<String, Object>> result = service.selectCustomerList();
        Map<String, Object> data = new HashMap<>();
        data.put("data", result);
        
        log.debug("data:"+data);
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
    
    
    @GetMapping("/customers/{id}")
    @Operation(summary = "고객조회", description="ID로 고객 조회", security = @SecurityRequirement(name="bearer"))
    public ResponseEntity<Map<String, Object>> getCustomer(@Parameter(name="id", description="테스트ID : SIG0000010") @PathVariable String id) {
        Map<String, Object> searchInfo = new HashMap<>();
        searchInfo.put("id", id);
                
        Customer customer = service.selectCustomer(searchInfo);
        Map<String, Object> data = new HashMap<>();
        data.put("data", customer);
        
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
    
    
    @PostMapping("/customers/{id}")
    @Operation(summary = "고객정보수정", description="고객 정보수정")
    public Map<String, Object> updateCustomer(@RequestBody Map<String, Object> paramMap) {
        
        /**
         * TEST Data
         * {
              "customerId": "SIG0000010",
              "email": "jongmo.jeon@signet.com"
            }
         */
        
        Customer result = service.updateCustomer(paramMap);
        Map<String, Object> data = new HashMap<>();
        data.put("data", result);
        
        return data;
    }
}

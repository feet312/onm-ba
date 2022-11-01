package com.sk.signet.onm.web.customer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "고객")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

    String id;                  // 고객ID
    String customerType;        // 고객타입
    String companyId;           // 회사ID
    String customerName;        // 고객명
    String userid;
    String password;
    String zipcode;
    String address;
    String addressDetail;
    String mobile;
    String birthDay;
    String point;
    String notiYn1;
    String notiYn2;
    String notiYn3;
    String notiYn4;
    String notiYn5;
    String notiYn6;
    String notiYn7;
    String evModelId;
    String autoLogin;
    String social;
    String socialId;
    String pointUseYn;
    String agreeYn;
    String deposit;
    String disPayment;
    String agreeYn2;
    String marketAgreeYn;
    String bizNum;
    String memCardStat;
    String pricePlanId;
    String joinDate;
    String promotionId;
    String email;
    String tel;
    String bizCode;
    String bizType;
    String taxBillYn;
    String delvZipcode;
    String delvAddress;
    String delvAddressDetail;
    String emailRecvYn;
    String smsRecvYn;
    String atchFileId01;
    String atchFileId02;
    String memshipCardNo;
    String memCardPubType;
    String skHomeServiceMemYn;
    String payMethod;
    String status;
    
    String regUserId;       // 등록자
    String regDt;           // 등록일시
    String modUserId;       // 수정자
    String modDt;           // 수정일시
}

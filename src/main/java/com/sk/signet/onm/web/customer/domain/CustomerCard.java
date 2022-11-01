package com.sk.signet.onm.web.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerCard {

    String id;
    String customerId;
    String issuerCardCd;
    String billTokenKey;
    String cardNo;
    String validYymm;
    String cardAlias;
    String regDt;
    String regId;
    
}

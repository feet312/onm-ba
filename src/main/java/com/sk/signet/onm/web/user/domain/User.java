package com.sk.signet.onm.web.user.domain;

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
public class User {

	String companyId;		// 회사ID
	String userId;			// 사용자ID
	String userNm;			// 사원명
	String empNo;			// 사원번호
	String deptCd;			// 부서코드
	String useYn;			// 사용여부
	String authId;			// 권한그룹ID
	String email;			// 이메일
	String appleId;			// Apple Login ID
	String deskNo;			// 자리번호
	String techId;			// 테크ID
	String titleCd;			// 직급코드(그룹코드)
	String mobileNo;		// 핸드폰번호
	String telNo;			// 전화번호
	String gridRowCd;		// 그리드 디폴트 Row 코드(30, 50, 100)
	String receiverAlramYn;	// 찾으러온고객알람YN
	String ipSecurityYn;	// IP보안적용YN
	String memo;			// 메모
	String regUserId;		// 등록자
	String regDt;			// 등록일시
	String modUserId;		// 수정자
	String modDt;			// 수정일시
	String withdrawalDate;	// 탈퇴일자
}

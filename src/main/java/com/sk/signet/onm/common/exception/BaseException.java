package com.sk.signet.onm.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @packagename : com.sk.signet.onm.common.exception
 * @filename 	: BaseException.java 
 * @since 		: 2022.11.21 
 * @description : 
 * =================================================================
 * Date				Author			Version			Note			
 * -----------------------------------------------------------------
 * 2022.11.21 		Heo, Sehwan		1.0				최초 생성
 * -----------------------------------------------------------------
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5119762081943226106L;

    /**
     * 사용자에게 노출되는 오류 설명.
     */
    @Getter
    private String userMessage;

    /**
     * exception에 대한 프로그래밍적인 설명. 사용자에게 직접적으로 노출되지 않을 내용이며, 디버깅에 도움이 될 내용을 담음.
     */
    @Getter
    private String systemMessage;

    /**
     * 오류에 대한 업무적으로 정의한 코드(http status code가 아님)
     */
    @Getter
    @Setter
    private String code;

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    | Constructor
    |-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public BaseException() {
        super();
    }

    public BaseException(String systemMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(systemMessage, cause, enableSuppression, writableStackTrace);

        // Throwable의 message 는 BaseException에서는 systemMessage인 것으로 간주함.
        this.systemMessage = systemMessage;
    }

    public BaseException(String systemMessage, Throwable cause) {
        super(systemMessage, cause);

        // Throwable의 message 는 BaseException에서는 systemMessage인 것으로 간주함.
        this.systemMessage = systemMessage;
    }

    public BaseException(String systemMessage) {
        super(systemMessage);

        // Throwable의 message 는 BaseException에서는 systemMessage인 것으로 간주함.
        this.systemMessage = systemMessage;
    }

    public BaseException(String systemMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            String userMessage) {
        super(systemMessage, cause, enableSuppression, writableStackTrace);

        // Throwable의 message 는 BaseException에서는 systemMessage인 것으로 간주함.
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
    }
}

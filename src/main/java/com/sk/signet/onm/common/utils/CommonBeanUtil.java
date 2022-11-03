package com.sk.signet.onm.common.utils;

import com.sk.signet.onm.common.context.ApplicationContextProvider;

public class CommonBeanUtil {
    public static Object getBean(String beanName) {
        return ApplicationContextProvider.getContext().getBean(beanName);
    }
    public static <T> Object getBean(Class<T> clz) {
        return ApplicationContextProvider.getContext().getBean(clz);
    }
}

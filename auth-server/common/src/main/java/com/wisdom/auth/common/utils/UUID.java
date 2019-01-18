package com.wisdom.auth.common.utils;

/**
 * Created by yxs on 2019/1/18.
 */
public class UUID {

    public static String uuid32 () {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}

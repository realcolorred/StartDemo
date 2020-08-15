package com.example.common.util;

import java.util.UUID;

/**
 * Created by lenovo on 2019/3/13.
 */
public class UUIDUtil {

    public static String getUUID() {
        return uuid32(UUID.randomUUID());
    }
    
    private static String uuid32(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        StringBuilder str = new StringBuilder();
        str.append(digits(msb >> 32, 8) + digits(msb >> 16, 4) + digits(msb, 4));
        str.append(digits(lsb >> 48, 4) + digits(lsb, 12));
        return str.toString();
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}

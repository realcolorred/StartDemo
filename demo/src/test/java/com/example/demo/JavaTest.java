package com.example.demo;

import java.util.Scanner;

/**
 * Created by new on 2020/8/1.
 *
 * 判断两个ip是否在同一个子网上
 */
public class JavaTest {

    public static void main(String[] ages) {
        try (Scanner s = new Scanner(System.in)) {
            String str = "";
            while (!str.equals(END)) {
                str = checkIPMask(s);
                if (!str.equals(END)) {
                    System.out.println(str);
                }
            }

        }
    }

    public static String ERROR_INPUT  = "ERROR_INPUT";
    public static String SAME_NETWORK = "SAME_NETWORK";
    public static String DIFF_NETWORK = "DIFF_NETWORK";
    public static String END = "END";

    public static String checkIPMask(Scanner s) {
        try {
            if(!s.hasNext()){
                return END;
            }
            String mask = s.nextLine();
            String ip1 = s.nextLine();
            String ip2 = s.nextLine();
            if (isRightIP(mask) && isRightIP(ip1) && isRightIP(ip2)) {
                if ((getIPVaule(mask) & getIPVaule(ip1)) == (getIPVaule(mask) & getIPVaule(ip2))) {
                    return SAME_NETWORK;
                } else {
                    return DIFF_NETWORK;
                }
            } else {
                return ERROR_INPUT;
            }
        } catch (Exception e) {
            return ERROR_INPUT;
        }
    }

    public static long getIPVaule(String ip) {
        String bitStr = "";
        String[] nums = ip.split("\\.");
        for (String num : nums) {
            int i = Integer.parseInt(num);
            String bit = Integer.toBinaryString(i);
            while (bit.length() < 8) {
                bit = "0" + bit;
            }
            bitStr += bit;
        }
        return Long.parseLong(bitStr, 2);
    }

    public static boolean isRightIP(String ip) {
        if (ip == null) {
            return false;
        }
        if (ip.matches("[0-9]{1,3}(\\.[0-9]{1,3}){3}")) {
            String[] nums = ip.split("\\.");
            if (nums.length != 4) {
                return false;
            }
            for (String num : nums) {
                try {
                    int i = Integer.parseInt(num);
                    if (i < 0 || i > 255) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}

package com.example.demo;

import com.example.common.util.StringUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by new on 2020/8/21.
 */
public class DetermineWordEncode {

    @Test
    public void test() {
        //获取系统默认编码
        System.out.println("系统默认编码：" + System.getProperty("file.encoding"));
        //系统默认字符编码
        System.out.println("系统默认字符编码：" + Charset.defaultCharset());
        //操作系统用户使用的语言
        System.out.println("系统默认语言：" + System.getProperty("user.language"));
        System.out.println();

        determineWordEncode("璇锋眰缁撴灉", "请求结果");
        determineWordEncode("鏅烘収涓\uE15E彴", "智慧中台");

        determineWordEncode("閿涗汞", "锛乯");
        determineWordEncode("锛乯", "！j");

        determineWordEncode("閺屻儴顕楅幋鎰\uE100\uE760", "鏌ヨ\uE1D7鎴愬姛");
        determineWordEncode("鏌ヨ\uE1D7鎴愬姛", "查询成功");

        determineWordEncode("鐠囬攱鐪伴崷鏉挎絻", "璇锋眰鍦板潃");
        determineWordEncode("璇锋眰鍦板潃", "请求地址");

    }

    public static void determineWordEncode(String word, String expectWord) {
        Set<String> encodeList = new HashSet<>();
        encodeList.add("UTF-8");
        encodeList.add("GBK");
        encodeList.add("ASCII");
        encodeList.add("ISO-8859-1");
        encodeList.add("GB2312");
        encodeList.add("UTF-16");

        /*for (String encode1 : encodeList) {
            for (String encode2 : encodeList) {
                String newStr = null;
                try {
                    newStr = new String(word.getBytes(encode1), encode2);
                } catch (UnsupportedEncodingException e) {
                }
                if (StringUtil.isNotEmpty(expectWord)) {
                    if (expectWord.equals(newStr)) {
                        System.out.println(encode1 + "->" + encode2);
                        return;
                    }
                } else {
                    System.out.println(encode1 + "->" + encode2 + ": " + newStr);
                }
            }
        }*/

        for (String encode1 : encodeList) {
            String encode2 = "UTF-8";

            String newStr = null;
            try {
                newStr = new String(word.getBytes(encode1), encode2);
            } catch (UnsupportedEncodingException e) {
            }
            if (StringUtil.isNotEmpty(expectWord)) {
                if (expectWord.equals(newStr)) {
                    System.out.println(encode1 + "->" + encode2 + ": " + word + "->" + newStr);
                    return;
                }
            } else {
                System.out.println(encode1 + "->" + encode2 + ": " + newStr);

            }
        }
    }
}

package com.sera;

import org.junit.Test;

/**
 * 测试正则
 * Created by wangqing on 16/7/22.
 */
public class TestPassword {

    @Test
    public void test(){
        String[] sa = {"12345", "abcde", "12abc", "12?@#", "?#$abcd", "12abcd#?"};
        String regex = "(?=.*?[a-zA-Z]+.*?)(?=.*?[1-9]+.*?)(?=.*?[\\p{Punct}]+.*?).*";
        for (String s : sa) {
            System.out.printf("%s:%s\n", s, s.matches(regex));
        }
    }

}

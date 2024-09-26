package com.zb.tools;

import org.junit.Test;
import org.python.jline.internal.TestAccessible;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.apache.coyote.http11.Constants.a;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class test {
    @Test
    public void test() throws UnsupportedEncodingException {

        String path = "http://localhost:8080/images/result/1/picture/00000/img4_00000.jpg";
        String encodedUrl = URLEncoder.encode(path, "UTF-8");
        System.out.println(encodedUrl);

    }
}

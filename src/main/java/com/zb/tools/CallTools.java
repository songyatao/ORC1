package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallTools {
    public static void Call(String arg, String pythonPath, BufferedReader in) throws IOException, InterruptedException {
        String[] args1 = new String[]{"python", pythonPath, arg};
        Process proc = Runtime.getRuntime().exec(args1);
        in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        proc.waitFor();
        System.out.println(sb.toString());
    }
}

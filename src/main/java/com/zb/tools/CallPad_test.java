package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallPad_test {
    public static void Call(String fileName, BufferedReader in, int caseId) throws IOException, InterruptedException {
        String arg1 = AppRootPath.getappRootPath_ori() + fileName;//原始图片的路径
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        String arg2 = AppRootPath.getappRootPath_result() + caseId;
        System.out.println(arg1);
        System.out.println(arg2);
        String[] args1 = new String[]{"python", AppRootPath.getappRootPath_python() + "pad_test.py", arg1, arg2};
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

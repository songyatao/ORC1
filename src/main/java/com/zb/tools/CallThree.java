package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallThree {
    public static void Call(BufferedReader in, int caseId, String fileName) throws IOException, InterruptedException {
//        String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result\\1\\img1\\picture";
        String arg1 = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";
        String[] args1 = new String[]{"python", AppRootPath.getappRootPath_python() + "Three_Dimensional_recognition.py", arg1};
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

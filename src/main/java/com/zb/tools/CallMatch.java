package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallMatch {
    public static void Call(BufferedReader in, int caseId) throws IOException, InterruptedException {

        String arg1 = AppRootPath.getappRootPath_images();
        String arg2 = AppRootPath.getappRootPath_result() + caseId + "\\" + "picture";
        String arg3 = "result" + "\\" + caseId;

        String[] args1 = new String[]{"python", AppRootPath.getappRootPath_python() + "match.py", arg1, arg2, arg3};
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

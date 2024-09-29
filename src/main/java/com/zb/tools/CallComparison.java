package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallComparison {
    public static void Call(BufferedReader in, String arg0,String arg2) throws IOException, InterruptedException {

        String pythonPath = AppRootPath.getappRootPath_python() + "BiDdui.py";
        String[] args1 = new String[]{"python", pythonPath, arg0,arg2};
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

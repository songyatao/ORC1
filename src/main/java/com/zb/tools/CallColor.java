package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallColor {
    public static void Call(BufferedReader in, int caseId, String fileName) throws IOException, InterruptedException {
        String arg = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";
        String pythonPath = AppRootPath.getappRootPath_python() + "COLOR.py";
        CallTools.Call(arg, pythonPath, in);
    }
}

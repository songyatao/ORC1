package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallFour {
    public static void Call(BufferedReader in, int caseId) throws IOException, InterruptedException {
        String arg = AppRootPath.getappRootPath_result() + caseId +  "\\picture";
        String pythonPath = AppRootPath.getappRootPath_python() + "Four_Dimensional_recognition.py";
        CallTools.Call(arg, pythonPath, in);
    }
}

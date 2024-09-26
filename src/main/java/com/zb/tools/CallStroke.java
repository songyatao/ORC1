package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallStroke {
    public static void Call(BufferedReader in, int caseId) throws IOException, InterruptedException {

        String arg = AppRootPath.getappRootPath_result() + caseId + "\\" + "picture";
        String pythonPath = AppRootPath.getappRootPath_python() + "HengShu.py";
        CallTools.Call(arg, pythonPath, in);
    }
}

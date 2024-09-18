package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallFour {
    public static void Call(BufferedReader in, int caseId, String fileName) throws IOException, InterruptedException {
//        String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result\\" + caseId + "\\" + fileName + "\\" + "picture";
        String arg = AppRootPath.getappRootPath_result() + caseId + "/" + fileName + "/picture";
        String pythonPath = AppRootPath.getappRootPath_python() + "Four_Dimensional_recognition.py";
        CallTools.Call(arg, pythonPath, in);
    }
}

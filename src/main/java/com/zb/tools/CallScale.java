package com.zb.tools;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class CallScale {
    public static void Call(BufferedReader in, String path) throws IOException, InterruptedException {

        String arg = path;
        String pythonPath = AppRootPath.getappRootPath_python() + "Small.py";
        CallTools.Call(arg, pythonPath, in);
    }
}

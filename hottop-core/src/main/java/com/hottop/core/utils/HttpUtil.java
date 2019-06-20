package com.hottop.core.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http 请求工具类
 */
public class HttpUtil {
    /**
     * 发送http请求
     * @param requestUrl 请求地址
     * @param requestMethod GET or POST
     * @param outputStr 请求数据
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);// 设置请求方式（GET/POST）
<<<<<<< HEAD
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
=======
            conn.setRequestProperty("content-type", "com.hottop.application/x-www-form-urlencoded");
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
            if (null != outputStr) {  // 当outputStr不为null时向输出流写数据
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (ConnectException ce) {
            LogUtil.error("连接超时"+CommonUtil.printStackTraceElements(ce.getStackTrace()));
        } catch (Exception e) {
            LogUtil.error("https请求异常：{}"+ CommonUtil.printStackTraceElements(e.getStackTrace()));
        }finally { // 释放资源
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (Exception e){}
            conn.disconnect();
        }
        return null;
    }
}

/**
 * *******************************************************************
 *
 * @AUTHOR：YOLANDA
 * @DATE：Apr 27, 201512:48:19 PM
 * @DESCRIPTION：create the File, and add the content.
 * ====================================================================
 * Copyright © 56iq. All Rights Reserved
 * *********************************************************************
 */
package com.github.callanna.metarialframe.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author YOLANDA
 * @Time Apr 27, 2015 12:48:19 PM
 */
public class OSUtil {

    /**
     * 用户UserAgent
     */
    private static String userAgent = null;

    /**
     * 获取用户手机UserAgent
     *
     * @param context
     * @author YOLANDA
     */
    public static void initUserAgent(Context context) {
        WebView webview = new WebView(context);
        WebSettings settings = webview.getSettings();
        userAgent = settings.getUserAgentString();
        LogUtil.i("UserAgent：" + userAgent);
    }

    /**
     * 获取手机UserAgent
     *
     * @return
     * @author YOLANDA
     */
    public static String getUserAgent() {
        return userAgent;
    }

    /**
     * 关闭输入法
     *
     * @param context
     * @author YOLANDA
     */
    public static void closeInputMethod(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = context.getCurrentFocus();
        if (inputMethodManager != null && focusView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开输入法
     *
     * @param context
     * @author joe
     */
    public static void openInputMethod(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = context.getCurrentFocus();
        if (inputMethodManager != null && focusView != null) {
            focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(focusView, 0);
        }
    }

    /**
     * 拿到状态栏的高度
     *
     * @param context
     * @return
     * @author YOLANDA
     */
    public static int getStatusBarHeight(Activity context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 震动一下手机
     *
     * @param context
     * @author YOLANDA
     */
    public static void vibrate(Context context) {
        Vibrator vibratorInstance = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibratorInstance.vibrate(200L);
    }
    /*
        * 获取CPU序列号
        *
        * @return CPU序列号(16位) 读取失败为"0000000000000000"
        */
    public static String GetCPUSerial() {
        String cpuAddress = "";
        try {
            String result = cpuAddress;
            // String RootCmd = "/system/bin/cat /proc/cpuinfo";
            // result = OSUtil.getRunCmd(RootCmd, 1500);
            result = loadFileAsString("/proc/cpuinfo");
            if (!result.equals("")) {
                result = result.substring(result.indexOf("Serial") + 6, result.length());
                result = result.substring(result.indexOf(":") + 1, result.length());
                result = result.trim();
                cpuAddress = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cpuAddress;
    }

        /**
         * 读取文本文件
         *
         * @param filePath
         * @return
         * @throws IOException
         */
        public static String loadFileAsString(String filePath) {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(filePath));
                char[] buf = new char[1024];
                int numRead = 0;
                while ((numRead = reader.read(buf)) != -1) {
                    String readData = String.valueOf(buf, 0, numRead);
                    fileData.append(readData);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return fileData.toString();
        }

    /**
     * 获取指定程序信息
     */
    public static ApplicationInfo getApplicationInfo(Context context, String pkg) {
        try {
            return context.getPackageManager().getApplicationInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getAndroidId(Context context) {
        String androidId = "";
        if (androidId == "")
            try {
                androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return androidId;
    }


    /**
     * 调用系统分享
     */
    public static void shareToOtherApp(Context context,String title,String content, String dialogTitle ) {
        Intent intentItem = new Intent(Intent.ACTION_SEND);
        intentItem.setType("text/plain");
        intentItem.putExtra(Intent.EXTRA_SUBJECT, title);
        intentItem.putExtra(Intent.EXTRA_TEXT, content);
        intentItem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intentItem, dialogTitle));
    }

    /**
     * need < uses-permission android:name =“android.permission.GET_TASKS” />
     * 判断是否前台运行
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void startSystemSettingAcivity(Context context){
        Intent intent = new Intent("/");
         ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
    }
}

package com.zking.utools;

import android.text.TextUtils;
import android.util.Log;

import static com.zking.utools.ZkUtils.debug;

public final class LogUtils {

    public static String tagPrefix = "";
    private static final String TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final String LEFT_BORDER = "║ ";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";

    /**
     * 得到tag（所在类.方法（L:行））
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s(L:%d)";
        tag = String.format(tag, new Object[]{callerClazzName, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        //给tag设置前缀
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return tag;
    }

    public static void eHttp(String str) {
        if (debug) {
            Log.e(generateTag(), str);
        }
    }

    public static void wNormal(String str) {
        StringBuffer buffer = new StringBuffer();
        if (debug) {
            buffer.append(" \n")
                    .append(TOP_BORDER)
                    .append("\n")
                    .append(LEFT_BORDER)
                    .append(str)
                    .append("\n")
                    .append(BOTTOM_BORDER);
            Log.w("normal-", buffer.toString());
        }
    }
}

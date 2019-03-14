package com.zking.utools;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：ZhaoTieJin
 * date :  2017/3/23
 * String Utils
 */
public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    /**
     * 判断字符串是否为空（字符串删除空格之后）
     * is null or its length is 0 or it is made by space
     * <p>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断字符串是否为空（字符串未删除空格）
     * is null or its length is 0
     * <p>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 获取字符串长度
     * get length of CharSequence
     * <p>
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str
     * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * <p>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * 首字母大写
     * capitalize first letter
     * <p>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * 字符串转utf-8
     * encoded in utf-8
     * <p>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 字符串转utf-8，如果有异常，返回一个默认的字符串
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg,
                Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * process special char in html
     * <p>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source
                : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * <p>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 尽量用于文本展示，不要参与计算
     * 会保留两位小数
     *
     * @param input
     * @return 123-->￥123.00    123456.389-->￥123,456.39
     */
    public static String formatRMBPrice(String input) {
        String result = "";
        try {
            double value = Double.valueOf(input);
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINESE);
            result = nf.format(value);
        } catch (Exception nfe) {
        }

        return result;
    }

    /**
     * 小数计算，小数点后四舍五入，保留2位
     * 注意此时price是整数的话，返回结果只有一位小数，比如 123456-->123456.0
     */
    public static double decimalRoundHalfUp(double price) {
        BigDecimal b = new BigDecimal(price);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 强制保留两位小数
     *
     * @param num
     * @return
     */
    public static String forceTwoDecimals(double num) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(num);
    }

    /**
     * 确保非空字符串
     *
     * @param str 原始字符串
     * @return 如果为空，就return ""
     */
    public static String toNonEmptyString(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static String toNonEmptyString(String str, String defaultStr) {
        if (isEmpty(str)) {
            return isEmpty(defaultStr) ? "" : defaultStr;
        } else {
            return str;
        }
    }

    /**
     * 校验手机号-中国
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileCN(String mobiles) {
        Pattern p = Pattern
                .compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 校验手机号-新加坡
     * https://blog.csdn.net/MissEel/article/details/76467878
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileSG(String mobiles) {
        Pattern p = Pattern
                .compile("^([8|9])\\d{7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码格式, 密码必须由6至14位数字，字母或下划线组成
     *
     * @param pwd 密码，
     * @return 是否符合要求
     */
    public static boolean matchRulePwd(String pwd) {
        Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,14}");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证身份证格式
     *
     * @param ID 身份证号
     * @return 匹配返回true
     */
    public static boolean matchID(String ID) {
        if (isEmpty(ID)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        // 通过Pattern获得Matcher
        Matcher m = p.matcher(ID);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return 匹配返回true
     */
    public static boolean matchEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 身份证 工具
     */
    public static final class CheckIdCardUtils {
        /**
         * 中国公民身份证号码最小长度。
         */
        public static final int CHINA_ID_MIN_LENGTH = 15;

        /**
         * 中国公民身份证号码最大长度。
         */
        public static final int CHINA_ID_MAX_LENGTH = 18;

        /**
         * 每位加权因子
         */
        public static final int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        /**
         * 数字验证
         *
         * @param val
         * @return 提取的数字。
         */
        public static boolean isNum(String val) {
            return !isEmpty(val) && val.matches("^[0-9]{1,}");
        }

        /**
         * 将字符数组转换成数字数组
         *
         * @param ca 字符数组
         * @return 数字数组
         */
        public static int[] converCharToInt(char[] ca) {
            int len = ca.length;
            int[] iArr = new int[len];
            try {
                for (int i = 0; i < len; i++) {
                    iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return iArr;
        }

        /**
         * 将power和值与11取模获得余数进行校验码判断
         *
         * @param iSum
         * @return 校验位
         */
        public static String getCheckCode18(int iSum) {
            String sCode = "";
            switch (iSum % 11) {
                case 10:
                    sCode = "2";
                    break;
                case 9:
                    sCode = "3";
                    break;
                case 8:
                    sCode = "4";
                    break;
                case 7:
                    sCode = "5";
                    break;
                case 6:
                    sCode = "6";
                    break;
                case 5:
                    sCode = "7";
                    break;
                case 4:
                    sCode = "8";
                    break;
                case 3:
                    sCode = "9";
                    break;
                case 2:
                    sCode = "x";
                    break;
                case 1:
                    sCode = "0";
                    break;
                case 0:
                    sCode = "1";
                    break;
                default:
                    break;
            }
            return sCode;
        }

        /**
         * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
         *
         * @param iArr
         * @return 身份证编码。
         */
        public static int getPowerSum(int[] iArr) {
            int iSum = 0;
            if (power.length == iArr.length) {
                for (int i = 0; i < iArr.length; i++) {
                    for (int j = 0; j < power.length; j++) {
                        if (i == j) {
                            iSum = iSum + iArr[i] * power[j];
                        }
                    }
                }
            }
            return iSum;
        }

        /**
         * 将15位身份证号码转换为18位
         *
         * @param idCard 15位身份编码
         * @return 18位身份编码
         */
        public static String conver15CardTo18(String idCard) {
            String idCard18 = "";
            if (idCard.length() != CHINA_ID_MIN_LENGTH) {
                return null;
            }
            if (isNum(idCard)) {
                // 获取出生年月日
                String birthday = idCard.substring(6, 12);
                Date birthDate = null;
                try {
                    birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                if (birthDate != null) {
                    cal.setTime(birthDate);
                }
                // 获取出生年(完全表现形式,如：2010)
                String sYear = String.valueOf(cal.get(Calendar.YEAR));
                idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
                // 转换字符数组
                char[] cArr = idCard18.toCharArray();
                if (cArr != null) {
                    int[] iCard = converCharToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    // 获取校验位
                    String sVal = getCheckCode18(iSum17);
                    if (sVal.length() > 0) {
                        idCard18 += sVal;
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
            return idCard18;
        }

        /**
         * 根据身份编号获取生日月
         *
         * @param idCard 身份编号
         * @return 生日(MM)
         */
        public static Short getMonthByIdCard(String idCard) {
            Integer len = idCard.length();
            if (len < CHINA_ID_MIN_LENGTH) {
                return null;
            } else if (len == CHINA_ID_MIN_LENGTH) {
                idCard = conver15CardTo18(idCard);
            }
            return Short.valueOf(idCard.substring(10, 12));
        }

        /**
         * 根据身份编号获取生日天
         *
         * @param idCard 身份编号
         * @return 生日(dd)
         */
        public static Short getDateByIdCard(String idCard) {
            Integer len = idCard.length();
            if (len < CHINA_ID_MIN_LENGTH) {
                return null;
            } else if (len == CHINA_ID_MIN_LENGTH) {
                idCard = conver15CardTo18(idCard);
            }
            return Short.valueOf(idCard.substring(12, 14));
        }

        /**
         * 根据身份证号，自动获取对应的星座
         *
         * @param idCard 身份证号码
         * @return 星座
         */
        public static int getConstellationById(String idCard) {
            if (isEmpty(idCard)) {
                return -1;
            }
            if (!matchID(idCard)) {
                return -1;
            }
            int month = getMonthByIdCard(idCard);
            int day = getDateByIdCard(idCard);
            int constellation = -1;

            if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
                constellation = 10; // 水瓶座
            } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
                constellation = 11; // 双鱼座
            } else if ((month == 3 && day > 20) || (month == 4 && day <= 19)) {
                constellation = 0; // 白羊座
            } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
                constellation = 1; // 金牛座
            } else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
                constellation = 2; // 双子座
            } else if ((month == 6 && day > 21) || (month == 7 && day <= 22)) {
                constellation = 3; // 巨蟹座
            } else if ((month == 7 && day > 22) || (month == 8 && day <= 22)) {
                constellation = 4; // 狮子座
            } else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
                constellation = 5; // 处女座
            } else if ((month == 9 && day >= 23) || (month == 10 && day <= 23)) {
                constellation = 6; // 天秤座
            } else if ((month == 10 && day > 23) || (month == 11 && day <= 22)) {
                constellation = 7; // 天蝎座
            } else if ((month == 11 && day > 22) || (month == 12 && day <= 21)) {
                constellation = 8; // 射手座
            } else if ((month == 12 && day > 21) || (month == 1 && day <= 19)) {
                constellation = 9; // 魔羯座
            }
            return constellation;
        }
    }

    /**
     * 时间戳转化为字符串时间
     *
     * @param date_str
     * @return
     */
    public static String timeToDateStamp(String date_str) {
        if (isEmpty(date_str)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long timeStamp = 0;
        if (date_str.equals("0")) {
            return "--";
        }
        long date = Long.valueOf(date_str);
        return dateFormat.format(new Date(date));
    }

    /**
     * 校验密码，（要求：字母+数字，6-8位）
     *
     * @param str
     * @return
     */
    public static boolean checkPwd(String str) {
        Pattern p = Pattern.compile("^(?=.*[a-zA-Z0-9].*)(?=.*[a-zA-Z\\\\W].*)(?=.*[0-9\\\\W].*).{8,20}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /////////////////////////获取图片路径-start/////////////////////////////

    /**
     * 获取图片路径
     *
     * @param context
     * @param imageUri
     * @return
     */
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    /////////////////////////获取图片路径-end/////////////////////////////

    /**
     * 分钟转天、时、分
     *
     * @param min
     * @return day hour min
     */
    public static String minToDayHourMin(Double min) {
        String html = "0m";
        if (min != null) {
            Double m = (Double) min;
            String format;
            Object[] array;
            Integer days = (int) (m / (60 * 24));
            Integer hours = (int) (m / (60) - days * 24);
            Integer minutes = (int) (m - hours * 60 - days * 24 * 60);
            if (days > 0) {
                format = "%1$,dd%2$,dh%3$,dm";
                array = new Object[]{days, hours, minutes};
            } else if (hours > 0) {
                format = "%1$,dh%2$,dm";
                array = new Object[]{hours, minutes};
            } else {
                format = "%1$,dm";
                array = new Object[]{minutes};
            }
            html = String.format(format, array);
        }

        return html;
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 两次点击按钮之间的点击间隔不能少于2000毫秒
     *
     * @return
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否可用
     *
     * @param context
     * @return
     */
    private static boolean isGPSAvailable(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGPSOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机系统时区转换时间
     *
     * @param date
     * @return
     */
    public static String formatDate2GMT(String date) {
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式
        localFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = localFormater.parse(date);
        } catch (android.net.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        localFormater.setTimeZone(TimeZone.getDefault());
        return gpsUTCDate == null ? date : localFormater.format(gpsUTCDate.getTime());
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 中文返回“zh” 、英语返回“en”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取SDK版本
     *
     * @return SDK版本
     */
    public static String getSDKVersion() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)--需要申请
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 从距离类型字符串中提取数值
     * eg："22.22 km"
     *
     * @param distanceStr
     * @return
     */
    public static String ExtractKmStr(String distanceStr) {
        String distancePattern = "(?<distance>\\d*\\.?\\d*)\\skm";
        Pattern distanceP = Pattern.compile(distancePattern);
        Matcher matcher = distanceP.matcher(distanceStr);
        if (matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1).toString();
        }
        return null;
    }

    /**
     * 从时间类型字符串中提取数值--正则表达式
     * eg："4 hours 36 mins"
     *
     * @param timeStr
     * @return
     */
    public static String[] ExtractTimeStr(String timeStr) {
        String timePattern = "(?:(?<hours>\\d*)\\shours)?\\s?(?<mins>\\d*)\\smins";
        Pattern timeP = Pattern.compile(timePattern);
        Matcher timeMatcher = timeP.matcher(timeStr);
        if (timeMatcher.find()) {
            String hours = timeMatcher.groupCount() > 1 ? timeMatcher.group(1) : null;
            String mins = timeMatcher.groupCount() > 1 ? timeMatcher.group(2) : timeMatcher.group(1);

            System.out.println("hours: " + hours + " mins: " + mins);
            return new String[]{hours, mins};
        }
        return null;
    }

    /**
     * 从时间类型字符串中提取数值--时间格式
     * eg："4 hours 36 mins"
     *
     * @param timeStr
     * @return
     */
    public static HashMap<Integer, Integer> ExtractTimeStr2(String timeStr) {
        SimpleDateFormat simpleDateFormat;
        HashMap<Integer, Integer> map = new HashMap<>();
        if (timeStr.contains("hours")) {
            simpleDateFormat = new SimpleDateFormat("HH 'hours' mm 'mins'");
        } else {
            simpleDateFormat = new SimpleDateFormat("mm 'mins'");
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(timeStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.put(1, calendar.get(Calendar.HOUR));
        map.put(2, calendar.get(Calendar.MINUTE));
        return map;
    }

    /**
     * 根据手机系统时区转换时间-只保留年月日
     *
     * @param date
     * @return
     */
    public static String formatDate2GMT_2(String date) {
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd");//当地时间格式
        localFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = localFormater.parse(date);
        } catch (android.net.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        localFormater.setTimeZone(TimeZone.getDefault());
        return gpsUTCDate == null ? date : localFormater.format(gpsUTCDate.getTime());
    }
}

package com.snake.mcf.common.utils;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.FrameworkException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符串操作类
 *
 * @author hengoking
 * @author (2018 / 1 / 22 add by xieguojun)
 * @version 1.0
 * @since 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * UTF8编码器
     */
    public static final Charset UTF8 = Charset.forName(PayplatformConstant.ENCODE_UTF_8);
    
    public static final String STAR = "*";

    private static final char[] BIN_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final Map<Character, Byte> HEX_BIN = new HashMap<>();
    
    static {
        HEX_BIN.put('0', (byte) 0);
        HEX_BIN.put('1', (byte) 1);
        HEX_BIN.put('2', (byte) 2);
        HEX_BIN.put('3', (byte) 3);
        HEX_BIN.put('4', (byte) 4);
        HEX_BIN.put('5', (byte) 5);
        HEX_BIN.put('6', (byte) 6);
        HEX_BIN.put('7', (byte) 7);
        HEX_BIN.put('8', (byte) 8);
        HEX_BIN.put('9', (byte) 9);

        HEX_BIN.put('a', (byte) 10);
        HEX_BIN.put('b', (byte) 11);
        HEX_BIN.put('c', (byte) 12);
        HEX_BIN.put('d', (byte) 13);
        HEX_BIN.put('e', (byte) 14);
        HEX_BIN.put('f', (byte) 15);

        HEX_BIN.put('A', (byte) 10);
        HEX_BIN.put('B', (byte) 11);
        HEX_BIN.put('C', (byte) 12);
        HEX_BIN.put('D', (byte) 13);
        HEX_BIN.put('E', (byte) 14);
        HEX_BIN.put('F', (byte) 15);
    }

    private StringUtils() {
    }

    /**
     * 将指定字节数组采用UTF-8编码成字符串
     *
     * @param bytes
     * @return
     */
    public static String createString(byte[] bytes) {
        try {
            return new String(bytes, PayplatformConstant.ENCODE_UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * 将字符串按照UTF-8解码成字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        try {
            return str.getBytes(PayplatformConstant.ENCODE_UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new FrameworkException(e);
        }
    }

    public static final byte[] getBytesUtf8(String str) {
        return getBytes(str);
    }

    /**
     * 将多个字符串组很成一个字符串.使用指定的分隔符分隔
     *
     * @param separator 分隔符
     * @param values    待join的字符串
     * @return
     */
    /*public static String join(String separator, String... values) {
        if (values.length < 1)
            return null;

        StringBuilder result = new StringBuilder();
        for (String value : values) {
            if (result.length() > 0)
                if (!isEmpty(separator)) {
                    result.append(separator);
                }
            result.append(value);
        }

        return result.toString();
    }*/

    /**
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }


    /**
     * @param buffer
     * @param repet  是否重复匹配
     * @param oldVal
     * @param newVal
     */
    public final static void replaceValue(StringBuilder buffer, boolean repet, String oldVal, String newVal) {

        while (true) {
            final int pos = buffer.indexOf(oldVal);
            if (pos < 0) {
                return;
            }

            buffer.replace(pos, pos + oldVal.length(), newVal);

            if (!repet) {
                break;
            }
        }

    }

    public final static String replaceValue(String orgin, String... values) {
        StringBuilder buffer = new StringBuilder(orgin.length() * 2);

        buffer.append(orgin);
        if (values.length > 0) {
            for (int i = 0; i < values.length; ++i) {
                replaceValue(buffer, i + 1, values[i]);
            }
        }

        return buffer.toString();

    }

    /**
     * 使用值替换指定的占位符
     *
     * @param buffer
     * @param index
     * @param value
     */
    public final static void replaceValue(StringBuilder buffer, int index, String value) {

        final String key = "{" + index + "}";
        replaceValue(buffer, true, key, value);
    }

    public static String toHtml(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            switch (c) {
                case ' ':
                    sb.append("&nbsp;");
                    break;
                case '\n':
                    sb.append("<br>");
                    break;
                case '\r':
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&#34;");
                    break;
                case '\\':
                    sb.append("&#92;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        } else {
            if (start < 0) {
                start += str.length();
            }

            if (start < 0) {
                start = 0;
            }

            return start > str.length() ? "" : str.substring(start);
        }
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    /*public static String join(Object[] array) {
        return join((Object[]) array, (String) null);
    }

    public static String join(Object[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }



    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Collection collection, char separator) {
        return collection == null ? null : join(collection.iterator(), separator);
    }

    public static String join(Collection collection, String separator) {
        return collection == null ? null : join(collection.iterator(), separator);
    }*/

    /**
     * @param fmt    错误消息格式字符串，使用{}替代参数，每一{}替代一个参数
     * @param params 参数清单
     * @return
     */
    public static final String format(String fmt, Object... params) {
        StringBuilder builder = new StringBuilder(fmt);

        if (params != null) {
            int pos = 0;
            for (Object param : params) {
                pos = builder.indexOf("{}", pos);
                if (pos < 0) {
                    break;
                }

                builder.replace(pos, pos + 2, param == null ? null : param.toString());
            }
        }
        return builder.toString();
    }

    /**
     * 将val转换为boolean值，字母不区分大小写
     * <li>true:数字1、T、Y、YES</li>
     * <li>false:其余</li>
     *
     * @param val
     * @return
     */
    public static final boolean toBoolean(String val) {
        if (val == null) {
            return false;
        }
        if ("T".equalsIgnoreCase(val) || "TRUE".equalsIgnoreCase(val)) {
            return true;
        }
        if ("Y".equalsIgnoreCase(val) || "YES".equalsIgnoreCase(val)) {
            return true;
        }
        if ("1".equalsIgnoreCase(val)) {
            return true;
        }

        return false;
    }

    public static final int toInt(String val, int defaultVal) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static final int toInt(String val) {
        return toInt(val, 0);
    }

    public static final String yesOrNo(boolean v) {
        return v ? "是" : "否";
    }


    /**
     * 将二进制数据转换为十六进制字符串表示
     *
     * @param b 字节数组
     * @return
     */
    public static final String binToHex(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(BIN_HEX[(b[i] & 0xf0) >>> 4]);
            sb.append(BIN_HEX[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hex
     * @return
     */
    public static final byte[] hexToBin(String hex) {
        int len = hex.length();
        if (len % 2 != 0) {
            throw new FrameworkException("不合法的十六进制字符串");
        }
        for (int i = 0; i < len; i++) {
            if (!HEX_BIN.containsKey(hex.charAt(i))) {
                throw new FrameworkException(format("包含非法十六进制字符[{}]", hex.charAt(i)));
            }
        }
        byte[] bytes = new byte[len / 2];

        for (int i = 0; i < len; i = i + 2) {
            int j = i / 2;
            int h = HEX_BIN.get(hex.charAt(i)) << 4;
            int l = HEX_BIN.get(hex.charAt(i + 1));
            bytes[j] = (byte) ((h & 0xf0) | (l & 0x0f));
        }

        return bytes;
    }

    public static final String fill(String oldStr, char fillChar, int length) {
        StringBuilder sb = new StringBuilder();
        if (oldStr == null) {
            oldStr = "";
        }
        for (int i = 0, len = length - oldStr.length(); i < len; ++i) {
            sb.append(fillChar);
        }
        sb.append(oldStr);
        return sb.toString();
    }
    
    /**
     * 根据传入字符串的长度替换为*
     * @oldStr  原始字符串
     * @类型   phone 保留前三位   email 替换@之前名称
     * @return
     */
    public static final String PHONE = "PHONE";
    public static final String EMAIL = "EMAIL";
    public static final String replaceStar(String oldStr, String type) {
    	if(null != type && StringUtils.isNotEmpty(type) && type.equals(PHONE)) {
    		oldStr = oldStr.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1***$2");
    	}else if(null != type && StringUtils.isNotEmpty(type) &&  type.equals(EMAIL)) {
    		oldStr = oldStr.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1***$3$4");
    	}else {
    		if(null != oldStr && StringUtils.isNotEmpty(oldStr)) {
    			if(oldStr.length() == 2) {
    				oldStr = oldStr.replaceAll("([\\d\\D]{1})(.*)", "$1***");
    			}else if(oldStr.length() == 3){
    				oldStr = oldStr.replaceAll("^(.{1})(.*?)(.{1})$", "$1***$3");
    			}else {
    				oldStr = oldStr.replaceAll("^(.{2})(.*?)(.{1})$", "$1***$3");
    			}
    			
    		}
    	}
        return oldStr;
    }
    
    public static void main(String[] args) {
    	System.out.println(StringUtils.replaceStar("中文地", ""));
	}
}

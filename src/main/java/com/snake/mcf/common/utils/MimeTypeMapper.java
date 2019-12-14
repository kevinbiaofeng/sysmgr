package com.snake.mcf.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MimeTypeMapper {

    private static HashMap<String, String> mapping = new HashMap<>();

    static {
        mapping.put(".pdf", "application/pdf");
        //mapping.put(".doc", "application/msword");
        mapping.put(".jpg", "image/jpeg");
        mapping.put(".bmp", "image/bmp");
        mapping.put(".png", "image/png");
    }

    public MimeTypeMapper(Properties properties) {
        mapping.clear();
        for (Map.Entry entry : properties.entrySet()) {
            mapping.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    /**
     * 获取mime type
     *
     * @param extName
     * @return
     */
    public static String getMimeType(String extName) {
        if(StringUtils.isEmpty(extName))
            return null;
        return mapping.get(extName.toLowerCase());
    }

}

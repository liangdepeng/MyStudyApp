package com.dpzz.lib_base.util;

import android.util.Base64;

public class UrlUtil {

    /**
     * 编码
     *
     * @param content
     * @return
     */
    public static String encode(byte[] content) {
        return Base64.encodeToString(content,Base64.DEFAULT);
    }

    /**
     * 解码
     *
     * @param source
     * @return
     */
    public static String decode(String source) {
        return new String(Base64.decode(source,Base64.DEFAULT));
    }

}

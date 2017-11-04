package org.wx.weixiao.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Daniel on 2017/5/5.
 */
public class DocumentHelper {
    private static String content;
    private static final String RESOURCES_FILE_NAME = "document.txt";

    public static String getDocumentContent() {
        if (content == null) {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(DocumentHelper.class
                        .getResourceAsStream("/" + RESOURCES_FILE_NAME),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            content = bufferedReader.lines().reduce("", (e1, e2) -> e1 + e2 + "\n");

        }
        return content;
    }

    public static void main(String args[]){
        System.out.println(getDocumentContent());
    }
}

package com.bifan.applib.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * created by ： bifan-wei
 */

public class StringUtil {
    /**
     * @param urlPath
     * @param isFile
     * @return --------------------
     * TODO 将url里面的中文路径的斜杠捕捉转为正常，同时中文转码
     */
    public static String ecodeUrlWithUTf8(String urlPath, Boolean isFile) {
        if (urlPath == null)
            return "";
        urlPath = urlPath.replaceAll("\\\\+", "/");// 替换所有反斜杠
        String decodedUrl;
        try {
            URL url = new URL(urlPath);
            decodedUrl = "http://" + url.getHost() + ":" + url.getPort();
            String path = url.getPath();
            if (decodedUrl.length() < urlPath.length()) {
                String path1 = urlPath.substring(decodedUrl.length(), urlPath.length());
                if (path.length() < path1.length()) {
                    path = path1;
                }
            }
            String[] paths = path.split("\\/");
            String fileExtension = "";
            if (isFile && paths.length > 0 && paths[paths.length - 1].contains(".")) {// 最后一个有.
                // ，说明可能是文件后缀
                String lastStr = paths[paths.length - 1];
                String[] splits = lastStr.split("\\.");
                fileExtension = "." + splits[splits.length - 1];
                int index = lastStr.length() - fileExtension.length();
                paths[paths.length - 1] = lastStr.substring(0, index);

            }
            String decodePath = "";
            for (int i = 0; i < paths.length; i++) {
                if (paths[i] != null && paths[i].length() > 0) {// 这里传入的链接必须是uft-8编码
                    String str = URLEncoder.encode(paths[i], "UTF-8").replace("+", "%20");
                    decodePath = decodePath + "/" + str;
                }
            }
            decodedUrl = decodedUrl + decodePath + fileExtension;
        } catch (Exception e) {
            return urlPath;
        }

        return decodedUrl;
    }

    /**
     * @return url 路径有错或者没有后缀，返回null
     * --------------------
     * TODO 获取url文件的文件后缀
     */
    public static String getUrlFileExtension(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            String identifiedStr = urlString.substring(0, urlString.indexOf(path) + path.length());// 已识别字符串，只是因为有些文件名是特殊字符，URL无法识别
            if (identifiedStr.length() < urlString.length()) {// 比原本长度短，说明最后有些字符没识别到
                path = path + urlString.substring(identifiedStr.length(), urlString.length());
            }
            String[] paths = path.split("\\/");
            String fileExtension;
            if (paths.length > 0 && paths[paths.length - 1].contains(".")) {
                // //
                // ，说明可能是文件后缀
                String lastStr = paths[paths.length - 1];
                String[] splits = lastStr.split("\\.");
                fileExtension = splits[splits.length - 1];
                return fileExtension;
            }

            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

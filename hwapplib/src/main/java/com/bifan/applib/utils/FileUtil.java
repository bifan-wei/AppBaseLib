package com.bifan.applib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class FileUtil {

    /**
     * @param url 本地文件路径，没有后缀名，返回空字符
     * @return
     */
    public static String getLocalFileExtension(String url) {
        File file = new File(url);
        if (file.exists()) {
            String fileName = file.getName();
            if (!fileName.contains(".")) {
                return "";
            }
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * 通过递归获取文件夹大小
     *
     * @param path
     * @return 获取文件夹大小
     */
    public static long getDirectorySize(String path) {
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    long size = 0;
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                size += getDirectorySize(f.getAbsolutePath());
                            } else {
                                size += f.length();
                            }
                        }
                    }
                    return size;

                } else {
                    return file.length();
                }
            }

        }
        return 0;
    }

    /**
     * 通过递归删除文件夹下的文件
     *
     * @param path
     * @return
     */
    public static void deleteDirectoryFiles(String path) {
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();

                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteDirectoryFiles(f.getAbsolutePath());
                            } else {
                                f.delete();
                            }
                        }
                    }


                } else {
                    file.delete();
                }
            }

        }
    }



    /**
     * @param byteSize 字节大小
     * @return 字节大小转化为显示字符，小于MB的计算KB,大于1MB的计算MB
     */
    public static String byteSizeToKBorMBString(long byteSize) {
        if (byteSize <= 0) {
            return "0B";
        }
        if (byteSize < 1024)
            return byteSize + "B";

        int kb = Math.round(byteSize / 1024);

        if (kb >= 1024) {
            float mb = FormatUtil.getFloat_KeepTwoDecimalPlaces(((float) kb) / 1024);
            return mb + " MB";
        }

        return kb + " KB";
    }


    /**
     * @param path
     * @return 判断文件是否存在
     */
    public static Boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }


    /**
     * @param path 创建文件
     * @return
     */
    public static Boolean createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * @param path 创建目录
     */
    public static void mkdirs(String path) {
        File file = new File(path);
        file.mkdirs();
    }

    /**
     * 调用该方法前确保文件是存在
     *
     * @param saveStr      要写入的字符串
     * @param filepath     文件路径
     * @param inAppendMode 是否是后面追加模式
     *                     -------------------
     *                     TODO 将字符串写入指定文件
     *                     --------------------
     */
    public static void writeToFile(String saveStr, String filepath, Boolean inAppendMode) {
        try {
            FileWriter writer = new FileWriter(filepath, inAppendMode);
            writer.write(saveStr);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

    /**
     * 读取指定文件字符数据
     *
     * @param filepath
     * @return 文件不存在是返回""
     * --------------------
     */
    public static String readFromFile(String filepath) {
        try {
            FileReader reader = new FileReader(filepath);
            StringBuffer stringBuffer = new StringBuffer();

            int length = 512;
            char[] cs = new char[length];
            int numS = 0;
            while ((numS = reader.read(cs, 0, length)) != -1) {

                if (numS < length && numS > 0) {
                    char[] cs1 = new char[numS];
                    System.arraycopy(cs, 0, cs1, 0, numS);
                    stringBuffer.append(cs1);
                } else {
                    stringBuffer.append(cs);

                }

            }
            reader.close();
            return stringBuffer.toString();

        } catch (IOException e1) {

            e1.printStackTrace();
        }
        return "";
    }

    /**
     * @param fileFrom   要复制的源文件路径
     * @param savePathTo 要复制保存的路径
     * @return --------------------
     * TODO 复制文件到指定文件
     * --------------------
     */
    public static Boolean copyFile(String fileFrom, String savePathTo) {
        return copyFile(new File(fileFrom), savePathTo, null);
    }

    /**
     * @param fileFrom
     * @param savePathTo
     * @param copyListener 文件复制监听
     *                     *TODO 复制文件到指定文件
     * @return
     */
    public static Boolean copyFile(String fileFrom, String savePathTo, FileCopyListener copyListener) {
        return copyFile(new File(fileFrom), savePathTo, copyListener);
    }

    /**
     * @param fileFrom   要复制的源文件
     * @param savePathTo 要复制保存的路径
     * @return 返回复制是否成功
     * --------------------
     * TODO
     */
    public static Boolean copyFile(File fileFrom, String savePathTo, FileCopyListener copyListener) {

        if (fileFrom == null || !fileFrom.exists()) {
            if (copyListener != null) {
                copyListener.onFail();
            }
            return false;
        }

        File file = new File(savePathTo);
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();
        }

        if (!file.exists()) {
            if (copyListener != null) {
                copyListener.onFail();
            }
            return false;
        }

        try {
            if (copyListener != null) {
                copyListener.onStart();
            }
            long sumLength = fileFrom.length();
            long copiedNum = 0;
            FileInputStream fis = new FileInputStream(fileFrom);
            FileOutputStream outputStream = new FileOutputStream(file);
            int byteCount = 512;
            int readNum = 0;
            byte[] buffer = new byte[byteCount];
            try {
                while ((readNum = (fis.read(buffer))) != -1) {
                    outputStream.write(buffer, 0, readNum);
                    copiedNum = copiedNum + readNum;
                    if (copyListener != null) {
                        copyListener.onProgress(copiedNum, sumLength);
                    }
                }

                if (copyListener != null) {
                    copyListener.onProgress(sumLength, sumLength);
                    copyListener.onEnd();
                }
                fis.close();
                outputStream.close();
                return true;
            } catch (IOException e) {

                e.printStackTrace();
            } finally {

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        if (copyListener != null) {
            copyListener.onFail();
        }

        return false;
    }

    public interface FileCopyListener {
        /**
         * 开始复制
         */
        void onStart();

        /**
         * 复制失败
         */
        void onFail();

        /**
         * 复制结束
         */
        void onEnd();

        /**
         * @param copiedNum 已复制长度
         * @param sumNum    总长度
         */
        void onProgress(long copiedNum, long sumNum);
    }
}

package com.iris.blog.components.oss.cloud;

import com.iris.blog.utils.DateUtil;
import com.iris.blog.utils.LambdaUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.UUID;

/**
 * 云存储(支持七牛、阿里云、腾讯云、本地)
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
public abstract class AbstractCloudStorageService {
    /**
     * 云存储配置信息
     */
    CloudStorageConfig config;

    /**
     * 文件路径
     *
     * @param directory 目录
     * @param suffix    后缀
     * @return 返回上传路径
     */
    public String getPath(String directory, String suffix, String fileName) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String path;
        if(StringUtils.isNotBlank(fileName)){
            //文件路径
            path = DateUtil.getLocalDateUnsignedStr() + "/" + fileName;
        }else {
            //文件路径
            path = DateUtil.getLocalDateUnsignedStr() + "/" + uuid;
        }

        if (StringUtils.isNotBlank(config.getDirectoryList()) && StringUtils.isNotBlank(directory)) {
            for (String item : LambdaUtil.StringTolistComma(config.getDirectoryList())) {
                if (directory.equals(item)) {
                    path = directory + "/" + path;
                }
            }
        }

        if(StringUtils.isNotBlank(fileName)){
            return path;
        }
        return path + "." + suffix;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @param directory 目录名
     * @return 返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix, String directory);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @param directory      目录名
     * @return 返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix, String directory);
    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @param directory      目录名
     * @param fileName      文件名
     * @return 返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix, String directory,String fileName);
    /**
     * 删除文件
     * @param fileName      文件名
     */
    public abstract void deleteFile(String fileName);

    /**
     * 获取文件路径
     * @param suffix      后缀
     * @param directory      目录名
     * @param fileName      文件名
     * @return 返回http地址
     */
    public abstract String getFilePath(String suffix, String directory,String fileName);

}

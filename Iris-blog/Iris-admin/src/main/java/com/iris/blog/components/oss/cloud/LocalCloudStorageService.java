package com.iris.blog.components.oss.cloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地上传
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Component
@Slf4j
public class LocalCloudStorageService extends AbstractCloudStorageService {

    public LocalCloudStorageService(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        File file = new File(config.getLocalPath() + File.separator + path);
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            log.error("文件上传失败 ex={}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return config.getLocalDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix, String directory) {
        return upload(data, getPath(directory, suffix,null));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix, String directory) {
        return upload(inputStream, getPath(directory, suffix,null));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix, String directory, String fileName) {
        return upload(inputStream, getPath(directory, suffix,fileName));
    }

    @Override
    public void deleteFile(String fileName) {

        try {
            File file = new File(fileName);
            FileUtils.delete(file);
        } catch (IOException e) {
            log.error("文件删除失败 ex={}", e.getMessage(), e);
        }
    }

    @Override
    public String getFilePath(String suffix, String directory, String fileName) {
        return getPath(directory, suffix,fileName);
    }
}

package com.iris.blog.components.oss.cloud;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Slf4j
@Component
public class AliyunCloudStorageService extends AbstractCloudStorageService {

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        OSSClient client = getOssClient();
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
            client.shutdown();
        } catch (Exception e) {
            log.error("文件上传失败 ex={}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return config.getAliyunDomain() + "/" + path;
    }

    @NotNull
    private OSSClient getOssClient() {
        OSSClient client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
        return client;
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
            OSSClient client = getOssClient();
            client.deleteBucket(fileName);
        } catch (Exception e) {
            log.error("文件删除失败 ex={}", e.getMessage(), e);
        }
    }

    @Override
    public String getFilePath(String suffix, String directory, String fileName) {
        return getPath(directory, suffix,fileName);
    }
}
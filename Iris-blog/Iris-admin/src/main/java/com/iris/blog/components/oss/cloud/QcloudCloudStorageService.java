package com.iris.blog.components.oss.cloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云存储
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Component
@Slf4j
public class QcloudCloudStorageService extends AbstractCloudStorageService {
    private COSCredentials credentials;
    private ClientConfig clientConfig;

    public QcloudCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        if (StringUtils.isBlank(config.getQcloudSecretId()) || StringUtils.isBlank(config.getQcloudSecretKey())) {
            return;
        }
        init();
    }

    private void init() {
        //1、初始化用户身份信息(secretId, secretKey)
        credentials = new BasicCOSCredentials(config.getQcloudSecretId(), config.getQcloudSecretKey());

        //2、设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig = new ClientConfig(new Region(config.getQcloudRegion()));
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            COSClient client = getCosClient();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());
            String bucketName = config.getQcloudBucketName() + "-" + config.getQcloudAppId();
            PutObjectRequest request = new PutObjectRequest(bucketName, path, inputStream, metadata);
            PutObjectResult result = client.putObject(request);

            client.shutdown();
            if (result.getETag() == null) {
                log.error("文件上传失败");
            }
        } catch (IOException e) {
            log.error("文件上传失败 ex={}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return config.getQcloudDomain() + "/" + path;
    }

    @NotNull
    private COSClient getCosClient() {
        COSClient client = new COSClient(credentials, clientConfig);
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
            COSClient client = getCosClient();
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
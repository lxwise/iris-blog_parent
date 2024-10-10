package com.iris.blog.components.oss.cloud;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Component
@Slf4j
public class QiniuCloudStorageService extends AbstractCloudStorageService {
    private UploadManager uploadManager;
    private String token;

    private BucketManager bucketManager;

    public QiniuCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        if (StringUtils.isBlank(config.getQiniuAccessKey()) || StringUtils.isBlank(config.getQiniuSecretKey())) {
            return;
        }
        init();
    }

    private void init() {
        Configuration configuration = new Configuration(Region.autoRegion());
        Auth auth = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey());
        uploadManager = new UploadManager(configuration);

        token = auth.uploadToken(config.getQiniuBucketName());

        bucketManager = new BucketManager(auth,configuration);

    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                log.error("文件上传失败 ex={}", res);
            }
        } catch (Exception e) {
            log.error("文件上传失败 ex={}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return config.getQiniuDomain() + "/" + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {

        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            bucketManager.delete(config.getQiniuBucketName(), fileName);
        } catch (QiniuException e) {
            log.error("文件删除失败 ex={}", e.getMessage(), e);
        }

    }

    @Override
    public String getFilePath(String suffix, String directory, String fileName) {
        return getPath(directory, suffix,fileName);
    }
}

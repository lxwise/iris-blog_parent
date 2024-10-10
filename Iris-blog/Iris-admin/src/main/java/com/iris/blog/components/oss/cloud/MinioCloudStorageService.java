package com.iris.blog.components.oss.cloud;

import com.iris.blog.common.ResultCode;
import com.iris.blog.common.exception.BusinessException;
import com.qcloud.cos.COSClient;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * MinIO 存储
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
@Slf4j
public class MinioCloudStorageService extends AbstractCloudStorageService {
    private MinioClient minioClient;

    public MinioCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        if (StringUtils.isBlank(config.getMinioAccessKey()) || StringUtils.isBlank(config.getMinioSecretKey())) {
            return;
        }
        //初始化
        init();
    }

    private void init() {
        try {
            minioClient =getMinioClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private MinioClient getMinioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(config.getMinioEndPoint())
                        .credentials(config.getMinioAccessKey(), config.getMinioSecretKey())
                        .build();
        return minioClient;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }


    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            // 如果BucketName不存在，则创建
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(config.getMinioBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(config.getMinioBucketName()).build());
            } else {
                log.error("Bucket {} already exists.", config.getMinioBucketName());
            }

            // 根据文件扩展名确定Content-Type
            String contentType = getContentType(path);

            // 设置Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", contentType);
            headers.put("Content-Disposition", "inline"); // 设置为inline，使图片在浏览器中直接显示

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(path)
                    .bucket(config.getMinioBucketName())
                    .stream(inputStream, inputStream.available(), -1)
                    .headers(headers) // 添加headers
                    .build();

            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("文件上传失败 ex={}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR);
        }

        return config.getMinioEndPoint() + "/" + config.getMinioBucketName() + "/" + path;
    }

    private String getContentType(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "tiff":
                return "image/tiff";
            case "svg":
                return "image/svg+xml";
            default:
                return "application/octet-stream"; // 默认值，表示未知类型
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
    public void deleteFile(String fileUrl) {

        try {
            if(StringUtils.isNotBlank(fileUrl)){
                String prefix = config.getMinioEndPoint() + "/" + config.getMinioBucketName();
                String url = fileUrl.replace(prefix, "");
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(config.getMinioBucketName()).object(url).build());
            }
        } catch (Exception e) {
            log.error("文件删除失败 ex={}", e.getMessage());
            throw new BusinessException(ResultCode.FILE_DELETE_ERROR);
        }
    }

    @Override
    public String getFilePath(String suffix, String directory, String fileName) {
        return getPath(directory, suffix,fileName);
    }
}

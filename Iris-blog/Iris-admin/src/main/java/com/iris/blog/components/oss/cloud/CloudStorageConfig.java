package com.iris.blog.components.oss.cloud;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 云存储配置信息
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型 1：七牛  2：阿里云  3：腾讯云  4：本地上传")
    private Integer type;

    @ApiModelProperty(value = "oss文件目录列表,多个目录用逗号隔开")
    private String directoryList;

    @ApiModelProperty(value = "七牛绑定的域名")
    private String qiniuDomain;

//    @ApiModelProperty(value = "七牛路径前缀")
//    private String qiniuPrefix;

    @ApiModelProperty(value = "七牛ACCESS_KEY")
    private String qiniuAccessKey;

    @ApiModelProperty(value = "七牛SECRET_KEY")
    private String qiniuSecretKey;

    @ApiModelProperty(value = "七牛存储空间名")
    private String qiniuBucketName;

    @ApiModelProperty(value = "阿里云绑定的域名")
    private String aliyunDomain;

//    @ApiModelProperty(value = "阿里云路径前缀")
//    private String aliyunPrefix;

    @ApiModelProperty(value = "阿里云EndPoint")
    private String aliyunEndPoint;

    @ApiModelProperty(value = "阿里云AccessKeyId")
    private String aliyunAccessKeyId;

    @ApiModelProperty(value = "阿里云AccessKeySecret")
    private String aliyunAccessKeySecret;

    @ApiModelProperty(value = "阿里云BucketName")
    private String aliyunBucketName;

    @ApiModelProperty(value = "腾讯云绑定的域名")
    private String qcloudDomain;

//    @ApiModelProperty(value = "腾讯云路径前缀")
//    private String qcloudPrefix;

    @ApiModelProperty(value = "腾讯云AppId")
    private Integer qcloudAppId;

    @ApiModelProperty(value = "腾讯云SecretId")
    private String qcloudSecretId;

    @ApiModelProperty(value = "腾讯云SecretKey")
    private String qcloudSecretKey;

    @ApiModelProperty(value = "腾讯云BucketName")
    private String qcloudBucketName;

    @ApiModelProperty(value = "腾讯云COS所属地区")
    private String qcloudRegion;

    @ApiModelProperty(value = "本地上传绑定的域名")
    private String localDomain;

//    @ApiModelProperty(value = "本地上传路径前缀")
//    private String localPrefix;

    @ApiModelProperty(value = "本地上传存储目录")
    private String localPath;

    @ApiModelProperty(value = "Minio EndPoint")
    private String minioEndPoint;

    @ApiModelProperty(value = "MinIO accessKey")
    private String minioAccessKey;

    @ApiModelProperty(value = "MinIO secretKey")
    private String minioSecretKey;

    @ApiModelProperty(value = "MinIO BucketName")
    private String minioBucketName;

    @ApiModelProperty(value = "MinIO上传路径前缀")
    private String minioPrefix;

}

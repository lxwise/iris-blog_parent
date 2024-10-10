package com.iris.blog.components.oss.enums;

/**
 * OSS类型枚举
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
public enum OssTypeEnum {
    /**
     * 七牛云
     */
    QINIU(1),
    /**
     * 阿里云
     */
    ALIYUN(2),
    /**
     * 腾讯云
     */
    QCLOUD(3),
    /**
     * 本地
     */
    LOCAL(4),
    /**
     * MinIO
     */
    MINIO(5);

    private int value;

    OssTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
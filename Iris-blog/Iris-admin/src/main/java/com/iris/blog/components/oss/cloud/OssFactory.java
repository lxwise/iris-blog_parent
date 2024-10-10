package com.iris.blog.components.oss.cloud;

import com.alibaba.fastjson.JSON;
import com.iris.blog.common.constant.SystemParamCodeConstant;
import com.iris.blog.components.oss.enums.OssTypeEnum;
import com.iris.blog.service.SysConfigService;
import com.iris.blog.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传Factory
 *
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Slf4j
public final class OssFactory {

    private static SysConfigService configService;
    static {
        OssFactory.configService = SpringContextUtils.getBean(SysConfigService.class);
    }

    public static AbstractCloudStorageService build() {
        String paramValue = configService.getValueByCode(SystemParamCodeConstant.SYS_OSS_CONFIG_KEY);
        CloudStorageConfig config = JSON.parseObject(paramValue, CloudStorageConfig.class);
        //获取云存储配置信息
        if (config.getType() == OssTypeEnum.QINIU.value()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == OssTypeEnum.ALIYUN.value()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == OssTypeEnum.QCLOUD.value()) {
            return new QcloudCloudStorageService(config);
        } else if (config.getType() == OssTypeEnum.LOCAL.value()) {
            return new LocalCloudStorageService(config);
        }else if(config.getType() == OssTypeEnum.MINIO.value()){
            return new MinioCloudStorageService(config);
        }

        return null;
    }

}
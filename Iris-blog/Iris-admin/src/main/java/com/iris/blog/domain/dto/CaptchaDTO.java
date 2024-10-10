package com.iris.blog.domain.dto;

import lombok.Data;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 验证码DTO
 */
@Data
public class CaptchaDTO {

    /**
     * 画布宽度
     **/
    private Integer canvasWidth;
    /**
     * 画布高度
     **/
    private Integer canvasHeight;
    /**
     * 阻塞块宽度
     **/
    private Integer blockWidth;
    /**
     * 阻塞块高度
     **/
    private Integer blockHeight;
    /**
     * 随机移动字符串
     **/
    private String randomStr;
    /**
     * 验证值
     **/
    private String captchaValue;
    /**
     * 画布的base64
     **/
    private String canvasBase64;

    /**
     * 阻塞块的base64
     **/
    private String blockBase64;
    /**
     * 阻塞块的横轴坐标
     **/
    private Integer blockX;
    /**
     * 阻塞块的纵轴坐标
     **/
    private Integer blockY;
    /**
     * 阻塞块凸凹半径
     **/
    private Integer blockRadius;
}

package com.iris.blog.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @create 2024-06
 * @description:
 */
@Data
public class GatewayAccessLogVO {

    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求完成时间
     */
    private LocalDateTime time;
    /**
     * 响应状态码
     */
    private Integer status;
    /**
     * 请求方式
     */
    private String rm;
    /**
     * 协议及版本
     */
    private String protocol;
    /**
     * 端口号
     */
    private String port;
    /**
     * 请求URI
     */
    private String uri;
    /**
     * 请求参数
     */
    private String args;
    /**
     * 响应字节大小
     */
    private Long size;
    /**
     * 请求花费时间
     */
    private String rt;
    /**
     * 客户端信息
     */
    private String agent;
}

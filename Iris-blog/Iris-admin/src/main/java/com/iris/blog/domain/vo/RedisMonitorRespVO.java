package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Properties;

@ApiModel(description = "Redis监控信息")
@Data
@Builder
@AllArgsConstructor
public class RedisMonitorRespVO {

    @ApiModelProperty(value = "Redis info指令结果具体字段查看Redis文档")
    private Properties info;

    @ApiModelProperty(value = "Redis key数量")
    private Long dbSize;

    @ApiModelProperty(value = "CommandStat数组")
    private List<CommandStat> commandStats;

    @ApiModel(description = "Redis命令统计结果")
    @Data
    @Builder
    @AllArgsConstructor
    public static class CommandStat {

        @ApiModelProperty(value = "Redis命令")
        private String command;

        @ApiModelProperty(value = "调用次数")
        private Long calls;

        @ApiModelProperty(value = "消耗CPU秒数")
        private Long usec;

    }

}
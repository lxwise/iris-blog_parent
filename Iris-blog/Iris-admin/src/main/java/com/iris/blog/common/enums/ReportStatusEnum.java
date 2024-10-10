package com.iris.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ReportStatusEnum {

    PROCESSING(0, "处理中"),

    COMPLETED(1, "已完成"),

    FILED(-1, "下载失败");


    private final Integer status;

    private final String name;

    /**
     * 枚举数据map化处理，为便于获取枚举数据.
     */
//    public static final Map<Integer, String> MAP = EnumUtil
//            .toMap(ReportStatusEnum.class, ReportStatusEnum::getStatus, ReportStatusEnum::getName);

}

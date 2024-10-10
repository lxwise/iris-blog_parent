package com.iris.blog.domain.vo;

import com.iris.blog.common.annotation.FillEnumMessage;
import com.iris.blog.common.enums.AreaTypeEnum;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Data
public class IPAddressVO {

    /**
     * ip
     */
    private String ip;
    /**
     * 操作系统信息
     */
    private String device;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * ip详细地址
     */
    private String fullLocation;

    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;

    private AreaTypeEnum area;

    @FillEnumMessage(enumFieldName = "area", enumFieldType = AreaTypeEnum.class, fileName = "name")
    private String areaName;

}

package com.iris.blog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * 分页查询公共响应参数
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页查询公共响应参数")
public class PageBean<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", dataType = "integer")
    private Integer page;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", position = 1, dataType = "integer")
    private Integer totalPages;

    /**
     * 记录总数
     */
    @ApiModelProperty(value = "记录总数", position = 2, dataType = "integer")
    private Long total;

    /**
     * 当前页的数据列表
     */
    @ApiModelProperty(value = "当前页的数据列表", position = 3, dataType = "array")
    private List<T> records;

}

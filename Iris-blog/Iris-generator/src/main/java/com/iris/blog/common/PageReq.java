package com.iris.blog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 分页查询公共请求参数
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页查询公共请求参数")
public class PageReq<T> {

    public static final String ORDER_DEFAULT = "ASC";
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", position = 1, dataType = "integer",example = "1")
    @Min(value = 1, message = "{page.min}")
    private int page = 1;
    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", position = 2, dataType = "integer",example = "10")
    @Size(min = 1, max = 100, message = "{pageSize.size}")
    private int pageSize = 10;
    /**
     * 排序方式 DESC ESC
     */
    @ApiModelProperty(value = "排序方式 默认ASC",example = "DESC")
    @Size(max = 30)
    private String order;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段",example = "create_time")
    @Size(max = 40)
    private String sort;

    @ApiModelProperty("各个接口方法独立的请求参数")
    protected @Valid @NotNull(
            message = "{action.NotNull}"
    ) T action;

    public T getAction() {
        return this.action;
    }

    public void setAction(final T action) {
        this.action = action;
    }

    public String toString() {
        return "Req(action=" + this.getAction() + ")";
    }



    public PageReq(final T action) {
        this.action = action;
    }

    public String getOrder() {
        if(StringUtils.isBlank(this.order)){
            this.order = ORDER_DEFAULT;
        }
        return order;
    }
    public String getSort() {
        return sort;
    }
}

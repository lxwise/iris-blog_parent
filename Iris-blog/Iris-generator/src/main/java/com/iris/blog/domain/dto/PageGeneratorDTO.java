package com.iris.blog.domain.dto;

import com.iris.blog.common.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author lstar
 * @create 2023-04
 * @description:
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PageGeneratorDTO extends PageReq {

    @ApiModelProperty(value = "表名",example = "blog_article")
    private String tableName;
}

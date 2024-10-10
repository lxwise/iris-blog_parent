package com.iris.blog.domain.vo;

import com.iris.blog.dao.entity.ArticleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 首页统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHomeStatisticsDataVO {
    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer viewCount;

    /**
     * 留言量
     */
    @ApiModelProperty(value = "留言量")
    private Long messageCount;

    /**
     * 用户量
     */
    @ApiModelProperty(value = "用户量")
    private Long userCount;

    /**
     * 文章量
     */
    @ApiModelProperty(value = "文章量")
    private Long articleCount;

    /**
     * 分类统计
     */
    @ApiModelProperty(value = "分类统计")
    private List<ArticleCategoryVO> categoryVOList;

    /**
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表")
    private List<ArticleTagVO> tagVOList;

    /**
     * 文章贡献统计
     */
    @ApiModelProperty(value = "文章贡献统计")
    private List<SystemHomeContributeStatisticsVO> articleStatisticsList;

    /**
     * 文章浏览量排行
     */
    @ApiModelProperty(value = "文章浏览量排行")
    private List<ArticleVO> articleRankVOList;

    /**
     * 一周访问量
     */
    @ApiModelProperty(value = "一周访问量")
    private List<SystemHomeVisitStatisticsVO> userViewVOList;

    /**
     * 网关数据统计
     */
    @ApiModelProperty(value = "网关数据统计")
    private SystemHomeGatewayStatisticsVO gatewayStatisticsVO;
}
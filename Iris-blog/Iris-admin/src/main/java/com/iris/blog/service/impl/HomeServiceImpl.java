package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iris.blog.common.R;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.domain.vo.*;
import com.iris.blog.domain.vo.app.AppCarouselVO;
import com.iris.blog.domain.vo.app.AppBlogInfoVO;
import com.iris.blog.service.*;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.IPUtils;
import com.iris.blog.utils.LambdaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author lstar
 * @create 2024-06
 * @description: 首页统计
 */
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleCategoryService articleCategoryService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private GatewayLogService gatewayLogService;

    @Resource
    private LeaveMessageService leaveMessageService;

    @Resource
    private UserService userService;

    @Resource
    private SiteConfigService siteConfigService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CarouselService carouselService;

    @Override
    public R statistics() {

        //网关统计
        SystemHomeGatewayStatisticsVO gatewayStatisticsVO = gatewayLogService.requestStatistics();
        // 访问量
        int viewCount = 0;
        List<SystemHomeVisitStatisticsVO> userViewCount = gatewayLogService.getUserViewCount();
        if(CollectionUtils.isNotEmpty(userViewCount)){
            viewCount = LambdaUtil.listToSumIntBy(userViewCount, SystemHomeVisitStatisticsVO::getUv);
        }

        // 留言量
        Long messageCount = leaveMessageService.count(null);
        // 用户量
        Long userCount = userService.count(null);
        // 文章量
        Long articleCount = articleService.getCount();

        SystemHomeStatisticsDataVO vo = SystemHomeStatisticsDataVO.builder()
                .viewCount(viewCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .gatewayStatisticsVO(gatewayStatisticsVO)
                .build();
        return R.ok(vo);
    }

    @Override
    public R dataPanel() {
        // 分类数据
        List<ArticleCategoryVO> categoryRespList = articleCategoryService.articleCategoryCount();
        // 标签数据
        List<ArticleTagVO> tagVOList = articleTagService.selectList(null);
        // 查询用户浏览
        List<SystemHomeVisitStatisticsVO> userViewRespList = gatewayLogService.getUserViewCount();
        // 文章贡献
        List<SystemHomeContributeStatisticsVO> articleStatisticsList = articleService.selectArticleStatistics();
        //文章排行
        List<ArticleEntity> articles = articleService.list(new LambdaQueryWrapper<ArticleEntity>()
                .select(ArticleEntity::getViews, ArticleEntity::getTitle, ArticleEntity::getId)
                .eq(ArticleEntity::getStatus, BaseNumberEnum.ONE.getCode())
                .orderByDesc(ArticleEntity::getViews).last("limit 6"));

        List<ArticleVO> articleVOS = BeanUtil.sourceToTarget(articles, ArticleVO.class);
        SystemHomeStatisticsDataVO vo = SystemHomeStatisticsDataVO.builder()
                .articleStatisticsList(articleStatisticsList)
                .tagVOList(tagVOList)
                .categoryVOList(categoryRespList)
                .userViewVOList(userViewRespList)
                .articleRankVOList(articleVOS)
                .build();
        return R.ok(vo);
    }

    /************************app接口*************************************************/
    @Override
    public R<AppBlogInfoVO> getSiteConfigInfo() {

        // 文章数量
        Long articleCount = articleService.getCount();
        // 分类数量
        Long categoryCount = articleCategoryService.count();
        // 标签数量
        Long tagCount = articleTagService.count();
        // 博客访问量
        Integer count = redisUtil.getKeyValue(RedisKeyConstant.BLOG_VIEW_COUNT, Integer.class);
        String viewCount = Optional.ofNullable(count).orElse(0).toString();
        // 网站配置
        SiteConfigVO siteConfigInfo = siteConfigService.getSiteConfigInfo();

        AppBlogInfoVO infoVO = BeanUtil.newBean(siteConfigInfo, AppBlogInfoVO.class);
        infoVO.setArticleCount(articleCount);
        infoVO.setCategoryCount(categoryCount);
        infoVO.setTagCount(tagCount);
        infoVO.setViewCount(viewCount);

        return R.ok(infoVO);
    }

    @Override
    public R<IPAddressVO> report() {
        // 生成唯一用户标识
        String uuid = IPUtils.getRemoteId();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisUtil.isMemBer(RedisKeyConstant.UNIQUE_VISITOR, md5)) {
            // 保存唯一标识
            redisUtil.addToSet(RedisKeyConstant.UNIQUE_VISITOR, md5);
        }
        // 访问量+1
        redisUtil.incrementKey(RedisKeyConstant.BLOG_VIEW_COUNT);

        return R.ok(IPUtils.getIpAddressByTencent(null));
    }

    @Override
    public List<AppCarouselVO> getCarouselList() {
        return this.carouselService.getCarouselList();
    }

    @Override
    public R<String> getAboutMe() {

        SiteConfigVO siteConfigInfo = siteConfigService.getSiteConfigInfo();
        if(siteConfigInfo == null){
         return R.ok();
        }
        return R.ok(StringUtils.isBlank(siteConfigInfo.getAboutMe())? null : siteConfigInfo.getAboutMe());
    }
}

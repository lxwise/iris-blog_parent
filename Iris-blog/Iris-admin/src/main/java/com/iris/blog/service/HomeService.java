package com.iris.blog.service;

import com.iris.blog.common.R;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.domain.vo.app.AppBlogInfoVO;
import com.iris.blog.domain.vo.app.AppCarouselVO;

import java.util.List;

/**
 * @author lstar
 * @create 2024-06
 * @description:
 */
public interface HomeService {

    /**
     * 首页统计信息
     * @return
     */
    R statistics();

    /**
     * 首页数据看板
     * @return
     */
    R dataPanel();

    /************************app接口*************************************************/
    /**
     * 获取网站配置信息
     * @return
     */
    R<AppBlogInfoVO> getSiteConfigInfo();

    /**
     * 增加访问量
     */
    R<IPAddressVO> report();

    /**
     * 首页轮播图列表
     * @return
     */
    List<AppCarouselVO> getCarouselList();

    /**
     * 关于我信息
     * @return
     */
    R<String> getAboutMe();
}

import request from '@/config/axios'

export interface SiteConfigVO {
        id: number;
        userAvatar: string;
        touristAvatar: string;
        siteName: string;
        siteAddress: string;
        siteIntro: string;
        siteNotice: string;
        createSiteTime: string;
        recordNumber: string;
        authorAvatar: string;
        siteAuthor: string;
        articleCover: string;
        aboutMe: string;
        email:string;
        github: string;
        gitee: string;
        qqGroup: string;
        qq: string;
        commentCheck: number;
        messageCheck: number;
        isReward: number;
        wechatCode: string;
        alipayCode: string;
        emailNotice: number;
        socialList: string;
        loginList: string;
        isMusic: number;
        musicId: string;
        createTime: Date;
        updateTime: Date;

}
// 网站配置详情
export const getSiteConfig = async () => {
    return await request.get({url: '/system/site/config/selectSiteConfigInfo'})
}

// 新增网站配置
export const saveSiteConfig= async (data: SiteConfigVO) => {
    return await request.post({url: '/system/site/config/save', data})
}

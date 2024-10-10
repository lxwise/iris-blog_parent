import request from '@/config/axios'

export interface CarouselVO {
    id: number;
    imgUrl: string;
    status: number;
    createTime: Date;
    updateTime: Date;

}


// 首页轮播列表
export const getCarouselPage = async (data: PageParam) => {
    return await request.post({url: '/system/carousel/list', data})
}

// 新增首页轮播
export const createCarousel = async (data: CarouselVO) => {
    return await request.post({url: '/system/carousel/save', data})
}

// 删除首页轮播
export const deleteCarousel = async (id: number) => {
    const data: number[] = [id]
    return await request.post({url: '/system/carousel/delete', data})
}

// 首页轮播状态修改
export const updateCarouselStatus = async (id: number) => {
    return await request.get({url: '/system/carousel/update/status', params: {id: id}})
}


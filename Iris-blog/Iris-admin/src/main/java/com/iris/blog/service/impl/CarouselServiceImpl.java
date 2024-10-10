package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.domain.vo.app.AppCarouselVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.CarouselMapper;
import com.iris.blog.dao.entity.CarouselEntity;
import com.iris.blog.domain.dto.CarouselDTO;
import com.iris.blog.domain.vo.CarouselVO;
import com.iris.blog.service.CarouselService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 首页轮播
 */
@Service("carouselService")
@Slf4j
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, CarouselEntity> implements CarouselService {

    @Override
    public R selectCarouselPageList(PageReq req){

        Page<CarouselEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<CarouselEntity>lambdaQuery()
                        .orderByDesc(CarouselEntity::getId));
        PageBean<CarouselVO> pageBean = PageUtil.pageBean(page, CarouselVO.class);
        return R.ok(pageBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveCarousel(CarouselDTO dto){
        CarouselEntity entity = BeanUtil.newBean(dto, CarouselEntity.class);
        this.save(entity);
        return R.ok();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeCarouselByIds(List<Integer> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateStatusById(Long id) {
        CarouselEntity carouselentity = this.checkExist(id);
        carouselentity.setStatus(!carouselentity.getStatus());
        this.updateById(carouselentity);
        return R.ok();
    }

    @Override
    public List<AppCarouselVO> getCarouselList() {
        List<CarouselEntity> list = this.list(Wrappers.<CarouselEntity>lambdaQuery().eq(CarouselEntity::getStatus, true).orderByDesc(CarouselEntity::getId));
        return list.stream().map(item -> BeanUtil.newBean(item, AppCarouselVO.class)).toList();

    }


    public CarouselEntity checkExist(Long id){
        CarouselEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}
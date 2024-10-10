package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.DictDataEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.DictDataDTO;
import com.iris.blog.domain.search.SearchDictDataDTO;
import com.iris.blog.domain.vo.DictDataVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */
public interface DictDataService extends IService<DictDataEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectDictDataList(PageReq<SearchDictDataDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectDictDataById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveDictData(DictDataDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateDictData(DictDataDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeDictDataByIds(List<Long> ids);

    /**
     * 字典数据下拉
     * @param dictType
     * @return
     */
    R<List<DictDataVO>> selectList(String dictType);

    /**
     * 根据字典类型修改
     * @param oldDictType
     * @param dictType
     */
    void updateDictDataByType(String oldDictType,String dictType);
}


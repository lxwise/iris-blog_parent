package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.DictTypeEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.DictTypeDTO;
import com.iris.blog.domain.search.SearchDictTypeDTO;
import com.iris.blog.domain.vo.DictTypeVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典类型
 */
public interface DictTypeService extends IService<DictTypeEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectDictTypeList(PageReq<SearchDictTypeDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectDictTypeById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveDictType(DictTypeDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateDictType(DictTypeDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeDictTypeByIds(List<Long> ids);


    /**
     * 字典类型下拉
     * @return
     */
    R<List<DictTypeVO>> selectList();

    /**
     * 校验是否存在
     * @param id
     * @return
     */
    DictTypeEntity checkExist(Long id);

}


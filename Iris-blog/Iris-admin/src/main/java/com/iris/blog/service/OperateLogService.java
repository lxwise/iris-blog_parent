package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.OperateLogEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.OperateLogDTO;
/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */
public interface OperateLogService extends IService<OperateLogEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectOperateLogList(PageReq<OperateLogDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectOperateLogById( Long id);


    /**
     * 删除
     * @param ids
     * @return
     */
    R removeOperateLogByIds(List<Long> ids);

}


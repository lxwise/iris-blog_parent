package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchDictTypeDTO;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.iris.blog.domain.vo.DictTypeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.DictTypeDTO;
import com.iris.blog.service.DictTypeService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典类型
 */
@Api(tags = "字典类型管理")
@Validated
@RestController
@RequestMapping("/system/dict/type")
public class DictTypeController {
    @Resource
    private DictTypeService dictTypeService;

    @ApiOperation(value = "字典类型列表", httpMethod = "POST", response = R.class, notes = "字典类型列表")
    @SaCheckPermission("system:dict:query")
    @PostMapping("/list")
    public R selectDictTypeList(@RequestBody @Valid  PageReq<SearchDictTypeDTO> req){

        return dictTypeService.selectDictTypeList(req);
    }

    @ApiOperation(value = "字典类型信息", httpMethod = "GET", response = R.class, notes = "字典类型信息")
    @SaCheckPermission("system:dict:query")
    @GetMapping("/info")
    public R selectDictTypeById( Long id){

        return dictTypeService.selectDictTypeById(id);
    }

    @ApiOperation(value = "保存字典类型", httpMethod = "POST", response = R.class, notes = "保存字典类型")
    @SaCheckPermission("system:dict:save")
    @PostMapping("/save")
    public R saveDictType(@RequestBody @Valid DictTypeDTO dictType){

        return dictTypeService.saveDictType(dictType);
    }

    @ApiOperation(value = "修改字典类型", httpMethod = "POST", response = R.class, notes = "修改字典类型")
    @SaCheckPermission("system:dict:update")
    @PostMapping("/update")
    public R updateDictType(@RequestBody @Valid DictTypeDTO dictType){

        return dictTypeService.updateDictType(dictType);
    }


    @ApiOperation(value = "删除字典类型", httpMethod = "POST", response = R.class, notes = "删除字典类型")
    @SaCheckPermission("system:dict:delete")
    @OperateLog(value = "删除字典类型")
    @PostMapping("/delete")
    public R removeDictTypeByIds(@RequestBody List<Long> ids){

        return dictTypeService.removeDictTypeByIds(ids);
    }

    @SaCheckLogin
    @GetMapping("select")
    @ApiOperation(value = "字典类型下拉", httpMethod = "GET", response = R.class, notes = "字典类型下拉")
    public R<List<DictTypeVO>> selectList(){

        return dictTypeService.selectList();
    }

}

package com.iris.blog.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchDictDataDTO;
import com.iris.blog.domain.vo.DictDataVO;
import com.iris.blog.domain.vo.DictTypeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.DictDataDTO;
import com.iris.blog.service.DictDataService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */

@Api(tags = "字典数据管理")
@Validated
@RestController
@RequestMapping("/system/dict/data")
public class DictDataController {
    @Resource
    private DictDataService dictDataService;

    @ApiOperation(value = "字典数据列表", httpMethod = "POST", response = R.class, notes = "字典数据列表")
    @SaCheckPermission("system:dict:query")
    @PostMapping("/list")
    public R selectDictDataList(@RequestBody @Valid  PageReq<SearchDictDataDTO> req){

        return dictDataService.selectDictDataList(req);
    }

    @ApiOperation(value = "字典数据信息", httpMethod = "GET", response = R.class, notes = "字典数据信息")
    @SaCheckPermission("system:dict:query")
    @GetMapping("/info")
    public R selectDictDataById( Long id){

        return dictDataService.selectDictDataById(id);
    }

    @ApiOperation(value = "保存字典数据", httpMethod = "POST", response = R.class, notes = "保存字典数据")
    @SaCheckPermission("system:dict:save")
    @PostMapping("/save")
    public R saveDictData(@RequestBody @Valid DictDataDTO dictData){

        return dictDataService.saveDictData(dictData);
    }

    @ApiOperation(value = "修改字典数据", httpMethod = "POST", response = R.class, notes = "修改字典数据")
    @SaCheckPermission("system:dict:update")
    @PostMapping("/update")
    public R updateDictData(@RequestBody @Valid DictDataDTO dictData){

        return dictDataService.updateDictData(dictData);
    }


    @ApiOperation(value = "删除字典数据", httpMethod = "POST", response = R.class, notes = "删除字典数据")
    @OperateLog(value = "删除字典数据")
    @SaCheckPermission("system:dict:delete")
    @PostMapping("/delete")
    public R removeDictDataByIds(@RequestBody List<Long> ids){

        return dictDataService.removeDictDataByIds(ids);
    }

    @SaCheckLogin
    @GetMapping("select")
    @ApiOperation(value = "字典数据下拉", httpMethod = "GET", response = R.class, notes = "字典数据下拉")
    public R<List<DictDataVO>> selectList(String dictType){

        return dictDataService.selectList(dictType);
    }

}

package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.components.oss.dto.UploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.OssFileDTO;
import com.iris.blog.service.OssFileService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件
 */

@Api(tags = "文件管理")
@Validated
@RestController
@RequestMapping("/oss/file")
public class OssFileController {
    @Autowired
    private OssFileService ossFileService;

    @PostMapping("/upload")
    @SaCheckPermission("server:file:upload")
    @ApiOperation(value = "上传文件", httpMethod = "POST", response = R.class, notes = "上传文件")
    public R<UploadDTO> upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return R.error("File is empty");
        }

        return R.ok(ossFileService.upload(file));
    }

    @ApiOperation(value = "列表", httpMethod = "POST", response = R.class, notes = "列表")
    @SaCheckPermission("server:file:query")
    @PostMapping("/list")
    public R selectOssFileList(@RequestBody @Valid  PageReq<OssFileDTO> req){

        return ossFileService.selectOssFileList(req);
    }

    @ApiOperation(value = "删除", httpMethod = "GET", response = R.class, notes = "删除")
    @SaCheckPermission("server:file:delete")
    @GetMapping("/delete")
    public R removeOssFileByIds(@NotNull Long id){

        return ossFileService.removeOssFileByIds(id);
    }

}

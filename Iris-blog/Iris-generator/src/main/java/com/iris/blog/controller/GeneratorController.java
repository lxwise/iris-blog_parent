package com.iris.blog.controller;

import com.iris.blog.domain.dto.PageGeneratorDTO;
import com.iris.blog.service.SysGeneratorService;
import com.iris.blog.common.R;
import com.iris.blog.utils.GenUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author lstar
 * @create 2023-04
 * @description:
 */
@RestController
@RequestMapping("/sys/generator")
public class GeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	/**
	 * 列表
	 */
	@ResponseBody
	@PostMapping("/list")
	public R list(@RequestBody @Valid PageGeneratorDTO dto){
		return sysGeneratorService.queryList(dto);
	}
	
	/**
	 * 生成代码
	 */
	@GetMapping("/code")
	public void code(String tables, HttpServletResponse response) throws IOException{
		byte[] data = sysGeneratorService.generatorCode(tables.split(","));
		Configuration config = GenUtils.getConfig();
		boolean flag = config.getBoolean("useZip");
		if(flag){
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"Iris-code.zip\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");

			IOUtils.write(data, response.getOutputStream());
		}
	}
}

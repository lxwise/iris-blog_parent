package com.iris.blog.controller.system;

import cn.dev33.satoken.stp.StpUtil;
import com.iris.blog.common.R;
import com.iris.blog.service.SseEmitterServer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 消息订阅
 */
@Api(tags = "消息订阅")
@Validated
@RestController
@RequestMapping("/system/message/sse")
public class SseController {
	/**
     * 客户端链接
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect() {
        String userId = StpUtil.getLoginIdAsString();
        return SseEmitterServer.connect(userId);
    }


}

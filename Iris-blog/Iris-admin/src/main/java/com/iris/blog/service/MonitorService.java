package com.iris.blog.service;

import com.iris.blog.common.R;

/**
 * @author lstar
 * @create 2024-04
 * @description:
 */
public interface MonitorService {

    R getSystemServerInfo();

    R getRedisMonitorInfo();

    R getServiceInfo();

}

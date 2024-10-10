package com.iris.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lstar
 * @create 2023-04
 * @description:
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁配置
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //非法SQL拦截插件
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        //阻止恶意的全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页插件 如果配置多个插件,切记分页最后添加,如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     *  设置自动注入规则
     * @date: 2021/7/19 14:52
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MybatisMetaObjectHandler();
    }
}

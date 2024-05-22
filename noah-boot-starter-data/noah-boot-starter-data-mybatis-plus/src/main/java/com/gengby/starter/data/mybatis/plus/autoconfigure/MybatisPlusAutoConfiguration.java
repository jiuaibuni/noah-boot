package com.gengby.starter.data.mybatis.plus.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.gengby.starter.core.constant.PropertiesConstants;
import com.gengby.starter.core.util.GeneralPropertySourceFactory;
import com.gengby.starter.data.mybatis.plus.autoconfigure.idgenerator.MyBatisPlusIdGeneratorConfiguration;
import com.gengby.starter.data.mybatis.plus.datapermission.DataPermissionFilter;
import com.gengby.starter.data.mybatis.plus.datapermission.DataPermissionHandlerImpl;

/**
 * MyBatis Plus 自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@AutoConfiguration
@MapperScan("${mybatis-plus.extension.mapper-package}")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(MyBatisPlusExtensionProperties.class)
@ConditionalOnProperty(prefix = "mybatis-plus.extension", name = PropertiesConstants.ENABLED, havingValue = "true")
@PropertySource(value = "classpath:default-data-mybatis-plus.yml", factory = GeneralPropertySourceFactory.class)
public class MybatisPlusAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MybatisPlusAutoConfiguration.class);

    /**
     * MyBatis Plus 插件配置
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyBatisPlusExtensionProperties properties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        MyBatisPlusExtensionProperties.DataPermissionProperties dataPermissionProperties = properties
            .getDataPermission();
        if (null != dataPermissionProperties && dataPermissionProperties.isEnabled()) {
            interceptor.addInnerInterceptor(new DataPermissionInterceptor(SpringUtil
                .getBean(DataPermissionHandler.class)));
        }
        // 分页插件
        MyBatisPlusExtensionProperties.PaginationProperties paginationProperties = properties.getPagination();
        if (null != paginationProperties && paginationProperties.isEnabled()) {
            interceptor.addInnerInterceptor(this.paginationInnerInterceptor(paginationProperties));
        }
        // 防全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * ID 生成器配置
     */
    @Configuration
    @Import({MyBatisPlusIdGeneratorConfiguration.Default.class, MyBatisPlusIdGeneratorConfiguration.CosId.class,
        MyBatisPlusIdGeneratorConfiguration.Custom.class})
    protected static class MyBatisPlusIdGeneratorAutoConfiguration {
    }

    /**
     * 数据权限处理器
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledDataPermission
    public DataPermissionHandler dataPermissionHandler(DataPermissionFilter dataPermissionFilter) {
        return new DataPermissionHandlerImpl(dataPermissionFilter);
    }

    /**
     * 分页插件配置（<a href="https://baomidou.com/pages/97710a/#paginationinnerinterceptor">PaginationInnerInterceptor</a>）
     */
    private PaginationInnerInterceptor paginationInnerInterceptor(MyBatisPlusExtensionProperties.PaginationProperties paginationProperties) {
        // 对于单一数据库类型来说，都建议配置该值，避免每次分页都去抓取数据库类型
        PaginationInnerInterceptor paginationInnerInterceptor = null != paginationProperties.getDbType()
            ? new PaginationInnerInterceptor(paginationProperties.getDbType())
            : new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(paginationProperties.isOverflow());
        paginationInnerInterceptor.setMaxLimit(paginationProperties.getMaxLimit());
        return paginationInnerInterceptor;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'MyBatis Plus' completed initialization.");
    }
}

package com.todo.task.shiro;

import cn.hutool.core.codec.Base64;
import com.todo.task.config.RedisConfig;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Author xtf
 * @Date 06/06/2020 14:49
 * @Description shiro配置
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * 相关配置
     * Realm注册到Spring容器中
     *
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm realm = new CustomRealm();
        return realm;
    }

    @Bean
    public RedisConfig redisConfig(){
        return new RedisConfig();
    }

    /**
     * 为了保证实现了Shiro内部lifecycle函数的bean执行 也是shiro的生命周期，注入LifecycleBeanPostProcessor
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 紧接着配置安全管理器，SecurityManager是Shiro框架的核心，典型的Facade模式，Shiro通过SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务。
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 自定义sessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        SessionConfig mySessionManager = new SessionConfig();

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为30天
        mySessionManager.setGlobalSessionTimeout(3600 * 1000 * 24 * 7);

        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setExpire(3600 * 1000 * 24 * 7);
        return redisSessionDAO;
    }


    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        RedisConfig redisConfig = redisConfig();
        redisManager.setHost(redisConfig.getRedisHostPort());
        redisManager.setHost(redisConfig.getRedisHostPort());
        redisManager.setPassword(redisConfig.getREDIS_PASSWORD());
        int timeout = 3600 * 1000 * 24 * 7;
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("id");
        return redisCacheManager;
    }


    /**
     * 配置cookie记住密码
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200000);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;
    }


    /**
     * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
     * 加入这项配置能解决这个bug
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 除此之外Shiro是一堆一堆的过滤链，所以要对shiro 的过滤进行设置，
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/swagger-resources", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs-ext", "anon");
        chainDefinition.addPathDefinition("/doc.html", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("favicon.ico", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/user/user/api/v1/login", "anon");
        //这里不做访问权限 访问权限由注解控制
        chainDefinition.addPathDefinition("/**", "anon");

        return chainDefinition;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        //自定义authc访问拦截器
        filtersMap.put("authc", new AuthcShiroFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        return shiroFilterFactoryBean;
    }

}

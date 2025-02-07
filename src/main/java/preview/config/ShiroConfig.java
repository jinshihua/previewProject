package preview.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import preview.filter.ClientShiroThree;
import preview.realm.MyRealm;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    MyRealm myRealm() {
        return new MyRealm();
    }
//        @Bean
//    public SessionManager sessionManager() {
//        return new DefaultWebSessionManager();
//    }

    @Bean// 配置 SecurityManager, 并设置 SessionManager
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        manager.setSessionManager(sessionManager()); // 设置 SessionManager
        return manager;
    }

    @Bean
    public SessionManager sessionManager() {
//        return new NoOpSessionManager(); // 使用 NoOpSessionManager 禁用 Shiro Session
        return new DefaultWebSessionManager();
    }

    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/doLogin", "anon");
        definition.addPathDefinition("/test/login", "anon");
//        definition.addPathDefinition("/**", "authc");
        definition.addPathDefinition("/**", "client");  // 其他路径使用自定义过滤器
        return definition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager sessionManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(sessionManager);
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setUnauthorizedUrl("/");
        // 1. 配置自定义过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("client", new ClientShiroThree());
        shiroFilter.setFilters(filters);

        // 2. 配置过滤器链
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/test/login", "anon");  //  登录接口配置为 anon
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha", "anon");
        filterMap.put("/downLoadBrower", "anon");
        filterMap.put("/getBaseOrgSource", "anon");
        filterMap.put("/common/**", "anon");
        filterMap.put("/cadastre/**", "anon");
        filterMap.put("/**", "client"); // 使用自定义过滤器
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
}

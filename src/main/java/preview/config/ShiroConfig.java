package preview.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import preview.realm.MyRealm;

@Configuration
public class ShiroConfig {
    @Bean
    MyRealm myRealm() {
        return new MyRealm();
    }

//    @Bean
//    DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(myRealm());
//        return manager;
//    }

    @Bean
    public SessionManager sessionManager() {
        return new DefaultWebSessionManager();
    }

    // 配置 SecurityManager, 并设置 SessionManager
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        manager.setSessionManager(sessionManager()); // 设置 SessionManager
        return manager;
    }

    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/doLogin", "anon");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }
}

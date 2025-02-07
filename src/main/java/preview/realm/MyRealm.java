package preview.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import preview.pojo.User;
import java.util.UUID;

public class MyRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = ((UsernamePasswordToken)token).getUsername();

        if(!"jinshihua".equals(username)){
            throw new UnknownAccountException("用户名在系统中不存在！");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword("123456");
        String id = UUID.randomUUID().toString();
        user.setId(id);
        return new SimpleAuthenticationInfo(user,"123456",getName());
    }
    Realm ream;
}

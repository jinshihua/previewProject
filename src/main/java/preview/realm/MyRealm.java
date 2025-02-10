package preview.realm;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import preview.pojo.User;
import preview.token.JWTToken;
import preview.util.JWTUtils;

import java.util.UUID;

public class MyRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken || token instanceof JWTToken;
        // 确保支持 UsernamePasswordToken
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String username = ((UsernamePasswordToken) token).getUsername();
        if (token instanceof UsernamePasswordToken) {
            // 处理 UsernamePasswordToken
            String username = ((UsernamePasswordToken) token).getUsername();
            if (!"jinshihua".equals(username)) {
                throw new UnknownAccountException("用户名在系统中不存在！");
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword("123456");
            String id = UUID.randomUUID().toString();
            user.setId(id);
            return new SimpleAuthenticationInfo(user, "123456", getName());
        }else if(token instanceof JWTToken){
// 处理 JWTToken
            String jwtToken = (String) ((JWTToken) token).getPrincipal();
            if (!JWTUtils.verify(jwtToken)) {
                throw new IncorrectCredentialsException("Token 验证失败"); // token 无效
            }
            DecodedJWT decodedJWT = JWTUtils.decode(jwtToken);
            String username = decodedJWT.getClaim("username").asString();
            String userId = decodedJWT.getClaim("userId").asString();
            User user = new User();
            user.setUsername(username);
            user.setId(userId); // 设置用户 ID
            return new SimpleAuthenticationInfo(user, jwtToken, getName()); // 使用 token 作为凭据
        }else{
            return null;
        }
    }
}

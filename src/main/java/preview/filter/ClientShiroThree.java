package preview.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import preview.pojo.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientShiroThree extends BasicHttpAuthenticationFilter {
    private String SECRET = "!@#SDA$!@#";

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response1) throws Exception {
        HttpServletResponse response = (HttpServletResponse) response1;
        response.sendRedirect("/login.html");
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie1 : cookies) {
                if ("token".equals(cookie1.getName())) {
                    token = cookie1.getValue();
                }
            }
        }
        if (null == token || "".equals(token)) {
            System.out.println("-------token为空");
            return false;
        }
        try {
            //验证 token
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWT.require(algorithm).build().verify(token);
            DecodedJWT jwt = null;
            try {
                jwt = JWT.decode(token);
            } catch (JWTVerificationException e) {
                e.printStackTrace();
                System.out.println("-------token有问题");
                return false;
            }
            String username = jwt.getClaim("username").asString();
            User user = new User();
            user.setUsername(username);
            System.out.println("-------token正确");
            //验证通过重新进行授权
            Subject subject = SecurityUtils.getSubject();
            // 使用获取到的用户名创建一个UsernamePasswordToken
            UsernamePasswordToken token1 = new UsernamePasswordToken(user.getUsername(), "");
            subject.login(token1);
        } catch (JWTVerificationException | AuthenticationException e) {
            e.printStackTrace();
            System.out.println("-------token有问题");
            return false;
        }
        return true;
    }
}

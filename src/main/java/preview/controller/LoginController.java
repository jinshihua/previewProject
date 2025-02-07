package preview.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import preview.pojo.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;


@RestController
@RequestMapping("/test")
public class LoginController {
    private String SECRET = "!@#SDA$!@#";
    @PostMapping("/login")
    public String login(String username,String password){
        try {
            //1.登录
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
            //2.封装请求数据到token
            Calendar instance = Calendar.getInstance(); // 获取当前的日历
            instance.add(Calendar.DATE, 7); // 设置日历为当前的 7 天后
            Date date = instance.getTime(); // 将日历转化为 Date
            JWTCreator.Builder builder = JWT.create();
            String token = builder.withClaim("userId", user.getId()) // payload
                    .withClaim("username", username)
                    .withExpiresAt(date) // 设置过期时间
                    .sign(Algorithm.HMAC256(SECRET));// signature
            System.out.println(token);
            //3.设置 Cookie
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "登录成功！";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "登录失败,请重新登录！";
        }
    }
}

package preview.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import preview.pojo.User;
import preview.util.JWTUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class LoginController {
    //    private String SECRET = "!@#SDA$!@#";
//    @PostMapping("/login")
//    public String login(String username,String password){
//        try {
//            //1.登录
//            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(usernamePasswordToken);
//            User user = (User) subject.getPrincipal();
//            //2.封装请求数据到token
//            Calendar instance = Calendar.getInstance(); // 获取当前的日历
//            instance.add(Calendar.DATE, 7); // 设置日历为当前的 7 天后
//            Date date = instance.getTime(); // 将日历转化为 Date
//            JWTCreator.Builder builder = JWT.create();
//            String token = builder.withClaim("userId", user.getId()) // payload
//                    .withClaim("username", username)
//                    .withExpiresAt(date) // 设置过期时间
//                    .sign(Algorithm.HMAC256(SECRET));// signature
//            System.out.println(token);
//            //3.设置 Cookie
//            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//            Cookie cookie = new Cookie("token", token);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//            return "登录成功！";
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//            return "登录失败,请重新登录！";
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password) {
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
            // 生成 JWT
            String token = JWTUtils.generateToken(username, String.valueOf(user.getId()));
            // 获取HttpServletResponse
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            // 创建 Cookie
            Cookie cookie = new Cookie("token", token);
            // 设置 Cookie 的属性
            cookie.setPath("/");  // Cookie 的有效路径
            cookie.setHttpOnly(true); // 避免客户端脚本访问 Cookie (安全)
            //cookie.setSecure(true); // 如果你使用 HTTPS，设置为 true
            // 添加 Cookie 到 Response
            response.addCookie(cookie);
            // 返回 JSON 响应 (可选)
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "登录成功:"+token); // 可以返回登录成功的消息，也可以不返回
            return ResponseEntity.ok(responseBody); // 返回 200 OK
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("登录失败,请重新登录！");
        }
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "未登录！";
    }
}

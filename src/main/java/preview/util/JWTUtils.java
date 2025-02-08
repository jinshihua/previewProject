package preview.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtils {

    private static final String SECRET = "!@#SDA$!@#"; // 替换成你自己的密钥

    public static String generateToken(String username, String userId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days expiration

        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withIssuedAt(now)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }
}
package crm.myhrcrmproject.service.auth;

import crm.myhrcrmproject.service.validation.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
    этим методом мы создаем екземпляр класса
    cоздаем и заполняем 2 поля: алгорит и верификация
    в момент создания эекз класса у нас храниться переменная (кусочек токена)
    и переменная, которая храниться о алгоритме шифрования

 */
@Service
public class JwtTokenProvider {
    @Value("${jwt.lifetime}")
    private long jwtLifeTime;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // метод создает новый токен
    public String createToken(String userName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // метод, проверяющий валидацию токена
    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            // Invalid JWT signature
            throw new InvalidJwtException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            // Invalid JWT token
            throw new InvalidJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            // Expired JWT token
            throw new InvalidJwtException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            // Unsupported JWT token
            throw new InvalidJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            // JWT claims string is empty
            throw new InvalidJwtException("JWT claims string is empty");
        }
    }
}

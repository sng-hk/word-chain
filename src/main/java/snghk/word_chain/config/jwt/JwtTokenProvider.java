package snghk.word_chain.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long validityInMilliseconds = 3600000; // 1 hour

    // JWT 토큰 생성
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username); // 토큰에 사용자 이름 담기
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 사용자 이름 가져오기
    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // 토큰의 유효성을 검증
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 Claims 정보 파싱
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
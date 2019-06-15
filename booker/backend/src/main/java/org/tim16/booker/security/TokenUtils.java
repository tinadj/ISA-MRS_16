package org.tim16.booker.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.tim16.booker.model.users.User;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUtils {

    @Value("backend")
    public String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("300")
    private int EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";

    @Autowired
    TimeProvider timeProvider;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    // Functions for generating new JWT token

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setAudience(generateAudience())
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private String generateAudience() {

        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        long expiresIn = EXPIRES_IN;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }

    // Functions for refreshing JWT token

    public String refreshToken(String token) {
        String refreshedToken = null;
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims != null) {
            claims.setIssuedAt(timeProvider.now());
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
        }
        return refreshedToken;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getIssuedAtDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
    }

    // Functions for validating JWT token data


    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (username != null && username.equals(userDetails.getUsername())
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(timeProvider.now());
    }

    private Boolean ignoreTokenExpiration(String token) {
        return true;
    }

    // Functions for getting data from token

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims != null)
            username = claims.getSubject();
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt = null;
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims != null)
            issueAt = claims.getIssuedAt();
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience = null;
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims != null)
            audience = claims.getAudience();
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims != null)
            expiration = claims.getExpiration();
        return expiration;
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    // Functions for getting JWT token out of HTTP request

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }
}

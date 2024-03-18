package com.example.employee.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpServletRequest request;
    private static final String JWT_SECRET = "abc-xyz";
    private final long JWT_EXPIRATION = 900000L;
    private static final String SECRET_KEY="opaqueKey";

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(SECRET_KEY.getBytes());
        keyBytes = Arrays.copyOf(keyBytes, 16); // Adjust to 16 bytes for AES-128, or use 24 or 32 for AES-192 or AES-256
        return new SecretKeySpec(keyBytes, "AES");
    }
    @Override
    public HttpHeaders login(LoginRequest loginRequest) throws Exception {
        //validate request
        if (!Objects.nonNull(loginRequest)){
            throw new UserException("ER001","Request body must be not null");
        }
        if (loginRequest.getUserName() == null || loginRequest.getUserName().trim().isEmpty()){
            throw new UserException("ER002","Username is blank");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()){
            throw new UserException("ER003","Password is blank");
        }
        //handle
        User user = userRepository.findUserByUserName(loginRequest.getUserName());
        if (!Objects.nonNull(user)){
            throw new UserException("ER004","UserName or password are wrong. please check again!");
        }
        RegisterRequest registerRequest = decodeToken(user.getPassword());
        if (!registerRequest.getPassword().equals(loginRequest.getPassword())){
            throw new UserException("ER005","UserName or password are wrong. please check again!");
        }
        String opaque = hashJWTToOpaque(user.getPassword());
        user.setJwt(opaque);
        userRepository.save(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("opaque",opaque);
        return responseHeaders;
    }

    @Override
    public String register(RegisterRequest registerRequest) throws Exception{
        //validate request
        if (!Objects.nonNull(registerRequest)){
            throw new UserException("ER001","Request body must be not null");
        }
        if (registerRequest.getUserName() == null || registerRequest.getUserName().trim().isEmpty()){
            throw new UserException("ER002","Username is blank");
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()){
            throw new UserException("ER003","Password is blank");
        }

        //handle
        Date now = new Date();
        User user = new User();
        user.setUserName(registerRequest.getUserName());

        Date date = new Date(now.getTime() + JWT_EXPIRATION);
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        user.setExpiredTime(localDateTime);
        String jwt = generateToken(registerRequest);
        user.setJwt(jwt);
        user.setPassword(jwt);
        userRepository.save(user);
        return "Username: " + registerRequest.getUserName();
    }
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    private String generateToken(RegisterRequest registerRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String subject = objectMapper.writeValueAsString(registerRequest);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public static String hashJWTToOpaque(String jwt) throws Exception {
        //SecretKeySpec secretKey = new SecretKeySpec(generateAESKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateAESKey());
        byte[] encryptedBytes = cipher.doFinal(jwt.getBytes());

        // Hash the encrypted bytes
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(encryptedBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    }
    public static RegisterRequest decodeToken(String jwtToken) {
        try {
            // Parse the JWT token and extract its claims
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Extract the subject from the claims (which contains JSON string of RegisterRequest)
            String subject = claims.getSubject();

            // Deserialize the JSON string back into a RegisterRequest object
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterRequest registerRequest = objectMapper.readValue(subject, RegisterRequest.class);

            return registerRequest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


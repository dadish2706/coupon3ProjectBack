package com.Coupons.Controllers;

import com.Coupons.Entities.MySession;
import com.Coupons.LOGINMANAGER.ClientType;
import com.Coupons.LOGINMANAGER.LoginManager;
import com.Coupons.LOGINMANAGER.LoginRequest;
import com.Coupons.Services.ClientService;
import com.Coupons.Services.CompanyService;
import com.Coupons.Services.CustomerService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.time.Instant;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    LoginManager loginManager;
    @Autowired
    HashMap<String, MySession> sessions;

    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        ClientService clientService = loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getClientType());
        String token = createToken(clientService, loginRequest.getClientType());
        sessions.put(token, new MySession(clientService, Calendar.getInstance()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok()
                .headers(headers)
                .body(token);
    }

    /**
     * creating token with cases for Admin ,Company and costumer
     * with start time for the token and expired time after 20 minutes
     *
     * @param clientService
     * @param clientType
     * @return
     */
    private String createToken(ClientService clientService, ClientType clientType) {
        Instant now = Instant.now();
        Date expirationTime = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20));
        switch (clientType) {
            case Admin -> {
                String token = JWT.create()
                        .withClaim("name", "admin")
                        .withClaim("clientType", clientType.toString())
                        .withClaim("email", "admin@admin.com")
                        .withIssuedAt(now)
                        .withExpiresAt(expirationTime)
                        .sign(Algorithm.HMAC256("tamara"));
                return token;
            }
            case Company -> {
                CompanyService companyService = ((CompanyService) clientService);
                String token = JWT.create()
                        .withClaim("name", ((CompanyService) clientService).getCompanyDetails().getName())
                        .withClaim("email", ((CompanyService) clientService).getCompanyDetails().getEmail())
                        .withClaim("clientType", clientType.toString())
                        .withIssuedAt(now)
                        .withExpiresAt(expirationTime)
                        .sign(Algorithm.HMAC256("tamara"));
                return token;
            }
            case Customer -> {

                CustomerService customerService = ((CustomerService) clientService);
                String token = JWT.create()
                        .withClaim("id", customerService.getCustomerDetails().getId())
                        .withClaim("name", customerService.getCustomerDetails().getFirstName())
                        .withClaim("email", ((CustomerService) clientService).getCustomerDetails().getFirstName() + " " + ((CustomerService) clientService).getCustomerDetails().getLastName())
                        .withClaim("clientType", clientType.toString())
                        .withIssuedAt(now)
                        .withExpiresAt(expirationTime)
                        .sign(Algorithm.HMAC256("tamara"));
                return token;
            }
            default -> throw new IllegalArgumentException("Invalid client type");
        }

    }


    /**
     * this for log out from the system
     *
     * @param token
     * @return
     */
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        sessions.remove(token); // Remove the session associated with the provided token

        // Optionally, you can also invalidate the token on the client-side
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer"); // Set an empty token or remove the header
System.out.println("Logout successful");
        return ResponseEntity.ok()
                .body("Logout successful");

    }
}

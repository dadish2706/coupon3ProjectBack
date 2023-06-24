package com.Coupons.LOGINMANAGER;

import org.springframework.stereotype.Component;

@Component
public class LoginRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private ClientType clientType;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password, String name, String lastName, ClientType clientType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.clientType = clientType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}

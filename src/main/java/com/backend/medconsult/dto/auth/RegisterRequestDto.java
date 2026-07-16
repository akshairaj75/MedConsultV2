package com.backend.medconsult.dto.auth;

import com.backend.medconsult.enums.usersAndPatients.UserRole;
import com.backend.medconsult.enums.usersAndPatients.Gender;

public class RegisterRequestDto {
    private String email;
    private String phone;
    private String password;
    private String fullName;
    private UserRole role;
    private Gender gender;
    private String preferredLang;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getPreferredLang() {
        return preferredLang;
    }
    public void setPreferredLang(String preferredLang) {
        this.preferredLang = preferredLang;
    }
}

package com.backend.medconsult.dto.auth;

import com.backend.medconsult.enums.usersAndPatients.UserRole;

import java.security.PublicKey;

import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.usersAndPatients.Gender;



public class UserResponseDto {

private String fullName;
private String email;
private String phone;
private String avatarUrl;
private String preferredLang;
private Gender gender;
private UserRole role;
public String getFullName() {
    return fullName;
}
public void setFullName(String fullName) {
    this.fullName = fullName;
}
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
public String getAvatarUrl() {
    return avatarUrl;
}
public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
}
public String getPreferredLang() {
    return preferredLang;
}
public void setPreferredLang(String preferredLang) {
    this.preferredLang = preferredLang;
}
public Gender getGender() {
    return gender;
}
public void setGender(Gender gender) {
    this.gender = gender;
}
public UserRole getRole() {
    return role;
}
public void setRole(UserRole role) {
    this.role = role;
}

public static UserResponseDto fromEntity(User user){
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setFullName(user.getFullName());
    userResponseDto.setEmail(user.getEmail());
    userResponseDto.setPhone(user.getPhone());
    userResponseDto.setAvatarUrl(user.getAvatarUrl());
    userResponseDto.setPreferredLang(user.getPreferredLang());
    userResponseDto.setGender(user.getGender());
    userResponseDto.setRole(user.getRole());
    return userResponseDto;
}

}

package com.project.dto;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class UserDTO {
	private String userId;
	private String passwd;
	private String name;
	private String phoneNumber;
	private String eMail;
	private String gender;
	private String membershipNumber; // 추가된 회원번호 // 랜덤입니다.
	private String carNumber;
	private int userType;

	
	public UserDTO() {
		super();
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMembershipNumber() {
		return membershipNumber;
	}


	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}


	public String getCarNumber() {
		return carNumber;
	}


	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}


	public int getUserType() {
		return userType;
	}


	public void setUserType(int userType) {
		this.userType = userType;
	}


	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", passwd=" + passwd + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", eMail=" + eMail + ", gender=" + gender + ", membershipNumber=" + membershipNumber + ", carNumber="
				+ carNumber + ", userType=" + userType + "]";
	}



}

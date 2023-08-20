package com.project.dto;

import org.apache.ibatis.type.Alias;

@Alias("addparking")
public class AddParkingDTO {
	private String parkNum;
	private String address;
	private String parkSize;
	private String price;
	private String parkDescription;
	private String userNum;
	private int pno;
	private int fno;

	public AddParkingDTO() {
	}
//유저 정보
	public AddParkingDTO(String address, String parkSize, String price, String parkDescription) {
		this.address = address;
		this.parkSize = parkSize;
		this.price = price;
		this.parkDescription = parkDescription;
	}
	
	
//파일 추가
	public AddParkingDTO(String parkNum, int pno, int fno) {
		this.parkNum = parkNum;
		this.pno = pno;
		this.fno = fno;
	}

	public AddParkingDTO(String parkNum, String address, String parkSize, String price, String parkDescription,
			String userNum, int pno, int fno) {
		this.parkNum = parkNum;
		this.address = address;
		this.parkSize = parkSize;
		this.price = price;
		this.parkDescription = parkDescription;
		this.userNum = userNum;
		this.pno = pno;
		this.fno = fno;
	}

	
	public String getParkNum() {
		return parkNum;
	}

	public void setParkNum(String parkNum) {
		this.parkNum = parkNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParkSize() {
		return parkSize;
	}

	public void setParkSize(String parkSize) {
		this.parkSize = parkSize;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getParkDescription() {
		return parkDescription;
	}

	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public int getpno() {
		return pno;
	}

	public void setpno(int pno) {
		this.pno = pno;
	}

	public int getfno() {
		return fno;
	}

	public void setfno(int fno) {
		this.fno = fno;
	}

	@Override
	public String toString() {
		return "AddParkingDTO [parkNum=" + parkNum + ", address=" + address + ", parkSize=" + parkSize + ", price="
				+ price + ", parkDescription=" + parkDescription + ", userNum=" + userNum + ", pno=" + pno
				+ ", fno=" + fno + "]";
	}
	
	
	


	

}

package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.UserDTO;

@Mapper
public interface UserMapper {

	UserDTO login(Map<String, Object> map);

	UserDTO mdLoginApp(Map<String, Object> map);
	
	int insertUser(UserDTO dto);

	int UserCheckId(String id);
	
	String getMembershipNumber(String userId);

	void updateCarNumber(String userId, String carNumber);
	
	List<UserDTO> viewAlluser();
	
	List<UserDTO> searchUser(Map<String, Object> map);
	
	String checkPasswd(String nowPass);
	
	int updatePasswd(String newPass);
	
	String searchId(String insertId);
	
	
	

	UserDTO getUserByUserId(String userId);

	UserDTO getUserByUserCar(String carNum);

	String userNum(String userNum);
	
	

}

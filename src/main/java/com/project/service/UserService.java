package com.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.dto.UserDTO;
import com.project.mapper.UserMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	private static UserMapper mapper;

	public UserService(UserMapper mapper) {
		this.mapper = mapper;
	}

	public UserDTO login(String userId, String passwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("passwd", passwd);

		return mapper.login(map);
	}
	
	public UserDTO mdLoginApp(String userId, String passwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("passwd", passwd);

		return mapper.login(map);
	}

	public int insertUser(UserDTO dto) {
		return mapper.insertUser(dto);
	}

	public int UserCheckId(String id, String type) {
		if (type.equals("user")) {
			return mapper.UserCheckId(id);

		}
		return 0;
	}

	public List<UserDTO> searchUser(String kind, String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kind", kind);
		map.put("search", search);
		return mapper.searchUser(map);
	}

	public List<UserDTO> viewAlluser() {
		return mapper.viewAlluser();
	}

	public boolean checkPasswd(String nowPass) {
		String dto = mapper.checkPasswd(nowPass);
			return dto != null;
		}

	public int updatePasswd(String newPass) {
		return mapper.updatePasswd(newPass);
	}

	public boolean checkId(String insertId) {
		String dto = mapper.searchId(insertId);
		return dto != null;
	}
	
	public String getMembershipNumber(HttpSession session) {
	    UserDTO dto = (UserDTO) session.getAttribute("user");
	    if (dto != null) {
	        return mapper.getMembershipNumber(dto.getUserId());
	    }
	    return null;
	}

	public void updateCarNumber(String userId, String carNumber) {
	        mapper.updateCarNumber(userId, carNumber);
	    }

	

	public String userNum(String userNum) {
		return mapper.userNum(userNum);
	}

}
package com.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.dto.AddParkingDTO;
import com.project.dto.UserDTO;
import com.project.mapper.UseparkingMapper;

@Service
public class UseParkingService {
	
	private UseparkingMapper mapper;

	
	public UseParkingService(UseparkingMapper mapper) {
		this.mapper = mapper;
	}


	public List<AddParkingDTO> shareParking(String userNum) {
		return mapper.shareParking(userNum);
	}


	public List<Map<String, Object>> ticketParking(String userNum) {
		return mapper.ticketParking(userNum);
	}


	


}


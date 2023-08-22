package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.AddParkingDTO;
import com.project.dto.UserDTO;

@Mapper
public interface UseparkingMapper {

	List<AddParkingDTO> shareParking(String userNum);

	UserDTO ticketParking(String userNum);

	UserDTO selectcarNo(String userNum);

	

	

}
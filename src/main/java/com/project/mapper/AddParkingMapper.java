package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.AddParkingDTO;

@Mapper
public interface AddParkingMapper {

	int insertparkNum(AddParkingDTO dto);//parkNum객체(주차장정보)

	String insertFile(Map<String, Object> map);


	void insertFileUpload(Map<String, Object> map);

	void insertPicUpload(Map<String, Object> map);
	
}

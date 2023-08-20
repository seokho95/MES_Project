package com.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.dto.AddParkingDTO;
import com.project.mapper.AddParkingMapper;

@Service
public class AddParkingService {
	private AddParkingMapper mapper;

	public AddParkingService(AddParkingMapper mapper) {
		this.mapper = mapper;
	}

	public int insertparkNum(AddParkingDTO dto) {// 주차장정보
		return mapper.insertparkNum(dto);
	}

	public void insertFileUpload(String filepath, int parkNum) {// 서류파일경로&주차장번호
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filepath", filepath);
		map.put("parkNum", parkNum);
		mapper.insertFileUpload(map);
	}

	public void insertPicUpload(String picFilePath, int parkNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("picFilePath", picFilePath);
		map.put("parkNum", parkNum);
		mapper.insertPicUpload(map);
	}

}

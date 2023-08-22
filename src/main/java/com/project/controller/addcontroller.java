package com.project.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.AddParkingDTO;
import com.project.dto.UserDTO;
import com.project.service.AddParkingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class addcontroller {

	private AddParkingService addParkingService;

	public addcontroller(AddParkingService addParkingService) {
		this.addParkingService = addParkingService;
	}

	@RequestMapping("/map")
	public String map() {
		return "add_parking/map1";
	}

	@RequestMapping(value = "/map/action", method = RequestMethod.POST)
	public String AddParkingRequest(AddParkingDTO dto,
			@RequestParam(value = "addFile", required = true) MultipartFile addfileload,
			@RequestParam(value = "picFile", required = true) MultipartFile picfileload, 
			HttpServletResponse response, HttpSession session, 
			HttpServletRequest request) throws JSONException {
		// 데이터 오는지 확인
		System.out.println(dto);
		System.out.println(addfileload.getOriginalFilename());
		System.out.println(picfileload.getOriginalFilename());
//		dto.setUserNum("admin");
		// userNum받기
		String userNum = ((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber();
		dto.setUserNum(userNum);

		// 게시판 글쓰기 참고 시퀀스 받아오는 코드 (주차장번호)
		int parkNum = addParkingService.insertparkNum(dto);

		// 업로드 경로 설정
		File root = new File("c:\\fileupload\\");
		if (!root.exists()) {
			// root가 존재하지 않을경우
			root.mkdirs();
			// root디렉토리 생성
		}
		// 업로드시간 형식설정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		String date = sdf.format(Calendar.getInstance().getTime());

		String addFileName = addfileload.getOriginalFilename();
		String picFileName = picfileload.getOriginalFilename();
		System.out.println(addFileName);
		System.out.println(picFileName);

		// 파일 경로
		String addFilePath = root + "\\" + date + "_" + addFileName.substring(addFileName.indexOf("."));
		String picFilePath = root + "\\" + date + "_" + picFileName.substring(picFileName.indexOf("."));
		System.out.println(addFilePath);
		System.out.println(picFilePath);
		System.out.println(addFileName.substring(addFileName.indexOf(".")));
		if (addfileload.getSize() != 0) {
			File addFile = new File(addFilePath);
			addParkingService.insertFileUpload(addFilePath, parkNum);

			try {
				// The actual file upload part
				addfileload.transferTo(addFile);
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}

		if (picfileload.getSize() != 0) {
			File picfile = new File(picFilePath);
			addParkingService.insertPicUpload(picFilePath, parkNum);

			try {
				// The actual file upload part
				picfileload.transferTo(picfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "add_parking/succes";
	}

	@RequestMapping("/success")
	public String success() {
		return "add_parking/success";
	}

	@RequestMapping("/cancle")
	public String cancel() {
		return "add_parking/cancle";
	}
}

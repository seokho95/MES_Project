package com.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.dto.UserDTO;
import com.project.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@RequestMapping("/kakao")
	public String kakaoMap() {
		return "kakaoMap";
	}

	@RequestMapping("/useparking")
	public String useparking() {
		return "useparking";
	}
	
	@RequestMapping("/pay")
	public String pay() {
		return "pay";
	}

	@RequestMapping("/header")
	public String headerTest() {
		return "headfoot/header";
	}
	@RequestMapping("/footer")
	public String footerTest() {
		return "headfoot/footer";
	}
	
}


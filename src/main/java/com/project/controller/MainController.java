package com.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
	@RequestMapping("/mainmap")
	public String kakaoMap() {
		return "mainmap/mainmap";
	}

	@RequestMapping("/header")
	public String headerTest() {
		return "headfoot/header";
	}
	@RequestMapping("/footer")
	public String footerTest() {
		return "headfoot/footer";
	}
	@RequestMapping("/main")
	public String Main() {
		return "login_html/main";
	}
	
}


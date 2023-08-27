package com.project.controller;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dto.UserDTO;
import com.project.service.KakaoPay;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PayController {
	private KakaoPay kakaopay;
	private UserDTO UserDTO;

	public PayController(KakaoPay kakaopay) {
		super();
		this.kakaopay = kakaopay;
	}

	@RequestMapping("/payMain")
	public String pay_main(Model model,HttpServletRequest request) {
		
		String carNum = ((UserDTO)request.getSession().getAttribute("user")).getCarNumber();
		UserDTO car = kakaopay.getUserByUserCar(carNum);
		model.addAttribute("carNum",carNum);
		return "pay/pay_main";
	}
	
	// 단건 결제 요청
	@RequestMapping("/kakaoPay")
	public String pay(HttpServletRequest request) {
		return "redirect:" + kakaopay.kakaoPayReady(request);
	}

	@GetMapping("/kakaoPaySuccess")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model,HttpServletRequest request) {
		System.out.println("kakaoPaySuccess get............................................");
		System.out.println("kakaoPaySuccess pg_token : " + pg_token);
		model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token, request));	
		
		String carNum = ((UserDTO)request.getSession().getAttribute("user")).getCarNumber();
		UserDTO car = kakaopay.getUserByUserCar(carNum);
		model.addAttribute("carNum",carNum);
		
		return "pay/pay_result";
	}

	@GetMapping("/kakaoPayCancel")
	public String kakaoPayCancel(HttpServletRequest request, Model model) {
		System.out.println("kakaoPayCancel get............................................");
		System.out.println("kakaoPayCancel : ");
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String string = (String) params.nextElement();
			System.out.println(string);
		}
		return "pay/pay_cancel";
	}
	
	

}

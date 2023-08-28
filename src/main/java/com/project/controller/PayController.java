package com.project.controller;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.dto.UserDTO;
import com.project.service.KakaoPay;
import com.project.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PayController {
	private KakaoPay kakaopay;
	private UserDTO UserDTO;
	private UserService userService;

	
	

	

	public PayController(KakaoPay kakaopay, UserService userService) {
		super();
		this.kakaopay = kakaopay;
		this.userService = userService;
	}
	
	@ResponseBody
	@RequestMapping("/payMain")
	public String pay_main(Model model,HttpServletRequest request) {
		
//		String carNum = ((UserDTO)request.getSession().getAttribute("user")).getCarNumber();
//		UserDTO car = kakaopay.getUserByUserCar(carNum);
		String message ="";
		String userNum = ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
		String carNum = userService.userNum(userNum);
		model.addAttribute("carNum",carNum);
		if(carNum == null) {
			message = "<script>alert('마이페이지로 이동합니다. 차량번호를 등록해주세요');location.href='/myPage'</script>";
			return message;
		}else {
			message = "<script> location.href='/payMainA'</script>";
			return message;
		}	
	}
	@RequestMapping("/payMainA")
	public String payMainA(Model model,HttpServletRequest request) {
		String userNum = ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
		String carNum = userService.userNum(userNum);
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
		
		String userNum = ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
		String carNum = userService.userNum(userNum);
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

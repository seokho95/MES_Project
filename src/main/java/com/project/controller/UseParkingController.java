package com.project.controller;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.dto.AddParkingDTO;
import com.project.dto.UserDTO;
import com.project.service.UseParkingService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class UseParkingController {
	
	private  UseParkingService useparkingService;
	
	public UseParkingController(UseParkingService useparkingService) {
		this.useparkingService = useparkingService;
	}
	
	
	@RequestMapping("/useparking")
	public ModelAndView detail( ModelAndView view, HttpServletRequest request) {
	  
		String userNum = ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
			System.out.println(userNum);
			
			List<AddParkingDTO> parking_list = useparkingService.shareParking(userNum);
			System.out.println(parking_list.toString());
			
			List<Map<String, Object>> usePaking = useparkingService.ticketParking(userNum);
			System.out.println(useparkingService.ticketParking(userNum).toString());
			
			view.addObject("ticket", usePaking);
			
			view.addObject("parking_list", parking_list);
			
	        view.setViewName("useparking/useparking");
	    
	    return view;
	}
	
	
	
}
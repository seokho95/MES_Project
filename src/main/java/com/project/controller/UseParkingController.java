package com.project.controller;


import java.util.List;

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
			
			List<AddParkingDTO> parking_list = useparkingService.shareParking(userNum);
			
			UserDTO user_info = useparkingService.ticketParking(userNum);
			System.out.println(useparkingService.ticketParking(userNum).toString());
			
			
			
	        view.addObject("parking_list", parking_list);
	        System.out.println(parking_list.toString());
	        
	        view.addObject("user_info", user_info);
	        System.out.println(user_info.toString());
	        
	        view.setViewName("useparking/useparking");
	    
	    return view;
	}
	
	
	
}
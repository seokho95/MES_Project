package com.project.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.project.dto.UserDTO;
import com.project.mapper.UserMapper;
import com.project.vo.KakaoPayApprovalVO;
import com.project.vo.KakaoPayReadyVO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class KakaoPay {
	private UserMapper userMapper;
	
	 public KakaoPay(UserMapper userMapper) {
	        this.userMapper = userMapper;
	    }
	
	private static final String HOST = "https://kapi.kakao.com";
	
	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;
	
	public String kakaoPayReady(HttpServletRequest request) {

		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "179ce8e54dfed176e68267f067200634");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		
		String userId = ((UserDTO)request.getSession().getAttribute("user")).getUserId();
		UserDTO user = userMapper.getUserByUserId(userId);
		
//		String carNum = ((UserDTO)request.getSession().getAttribute("user")).getCarNumber();
//		UserDTO car = userMapper.getUserByUserCar(carNum);
		
		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "A1001");
		params.add("partner_user_id",userId);
//		params.add("carNum", carNum);
		params.add("item_name", "코리아it아카데미 신촌점");
		params.add("quantity", "1");
		params.add("total_amount", "3000");
		params.add("tax_free_amount", "300");
		params.add("approval_url", "http://localhost:9999/kakaoPaySuccess");
		params.add("cancel_url", "http://localhost:9999/kakaoPayCancel");
		params.add("fail_url", "http://localhost:9999/kakaoPaySuccessFail");
		
		

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
					KakaoPayReadyVO.class);

			System.out.println(kakaoPayReadyVO);

			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return "/kakaopay";

	}
	
	 public KakaoPayApprovalVO kakaoPayInfo(String pg_token,HttpServletRequest request) {
		 
	        System.out.println("KakaoPayInfoVO............................................");
	        System.out.println("-----------------------------");
	        
	        RestTemplate restTemplate = new RestTemplate();
	 
	        // 서버로 요청할 Header
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "KakaoAK " + "179ce8e54dfed176e68267f067200634");
	        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
	        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
	        
	        String userId = ((UserDTO)request.getSession().getAttribute("user")).getUserId();
			UserDTO user = userMapper.getUserByUserId(userId);
			
//			String carNum = ((UserDTO)request.getSession().getAttribute("user")).getCarNumber();
//			UserDTO car = userMapper.getUserByUserCar(carNum);
			
	        // 서버로 요청할 Body
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("cid", "TC0ONETIME");
	        params.add("tid", kakaoPayReadyVO.getTid());
	        params.add("partner_order_id", "A1001");
	        params.add("partner_user_id", userId);
//	        params.add("carNum", carNum);
	        params.add("pg_token", pg_token);
	        params.add("total_amount", "3000");
	        
	        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
	        
	        try {
	            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
	            System.out.println(kakaoPayApprovalVO);
	          
	            return kakaoPayApprovalVO;
	        
	        } catch (RestClientException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (URISyntaxException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        return null;
	    }

	public UserDTO getUserByUserCar(String carNum) {
		return userMapper.getUserByUserCar(carNum);
	}
}

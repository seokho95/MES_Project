package com.project.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.dto.UserDTO;
import com.project.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
	private UserService userService;

	// ↓↓ 카카오 로그인을 위한 URI와 KEY ↓↓
	private final String REST_API_KEY = "80462c955b13f6a6451b008befedcd2e";
	private final String REDIRECT_URI = "http://localhost:9999/login/kakao";

	public LoginController(UserService userService) {
		this.userService = userService;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////카카오 로그인 controller///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////	
	@RequestMapping("/")
	public ModelAndView kakaoLoginView(ModelAndView view) {
		String apiURL = "https://kauth.kakao.com/oauth/authorize?response_type=code&" + "client_id=" + REST_API_KEY
				+ "&redirect_uri=" + REDIRECT_URI;

		view.addObject("apiURL", apiURL);
		view.setViewName("login_html/login");

		return view;
	}

	@RequestMapping("/login/kakao")
	public ModelAndView kakaoCallBack(HttpSession session, ModelAndView view, String code)
			throws UnsupportedEncodingException, JSONException {

		String apiURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code" + "&client_id="
				+ REST_API_KEY + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;

		String res = requestKaKaoServer(apiURL, null);

		if (res != null && !res.equals("")) {
			JSONObject json = new JSONObject(res);
			session.setAttribute("user", res);
			session.setAttribute("accessToken", json.getString("access_token"));
			session.setAttribute("refreshToken", json.getString("refresh_token"));
		} else {
			view.addObject("res", "로그인 실패");
		}
		view.setViewName("redirect:/main");

		return view;
	}

	@RequestMapping("/kakao/profile")
	public ModelAndView getProfile(ModelAndView view, HttpSession session) {
		String token = (String) session.getAttribute("accessToken"); // 카카오 로그인 접근 토큰;
		String header = "Bearer " + token; // Bearer 다음에 공백 추가

		String apiURL = "https://kapi.kakao.com/v2/user/me";

		String result = requestKaKaoServer(apiURL, header);

		view.addObject("userInfo", result);
		view.setViewName("login_html/main.html");
		return view;
	}

	@RequestMapping("/kakao/delete")
	public ModelAndView deleteTokken(HttpSession session, ModelAndView view) throws JSONException {
		String token = (String) session.getAttribute("accessToken"); // 카카오 로그인 접근 토큰;
		String apiURL = "https://kapi.kakao.com/v1/user/unlink";
		String header = "Bearer " + token; // Bearer 다음에 공백 추가

		String result = requestKaKaoServer(apiURL, header);
		System.out.println(result);
		session.invalidate();
		view.setViewName("redirect:/kakao/login");
		return view;
	}

	@RequestMapping("/kakao/logout")
	public String logout(HttpSession session) {
		String apiURL = "https://kapi.kakao.com/v1/user/logout";
		String token = (String) session.getAttribute("accessToken"); // 카카오 로그인 접근 토큰;
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		String result = requestKaKaoServer(apiURL, header);
		session.invalidate();
		return "redirect:/";
	}

	private String requestKaKaoServer(String apiURL, String header) {
		StringBuilder res = new StringBuilder();
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			if (header != null && !header.equals("")) {
				con.setRequestProperty("Authorization", header);
			}

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println(res.toString());
			}
		} catch (Exception e) {
			// Exception 로깅
		}
		return res.toString();
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////APP 로그인 controller////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////		
	
	@RequestMapping("/mdLogin_app")
	public String mdLogin() {
		return "login_html/mdLogin_app";
	}	
	
	@RequestMapping("app/loginInput")
	public String mdLoginApp(String userId, String passwd, HttpSession session) {

	    UserDTO dto = userService.mdLoginApp(userId, passwd);
	    if (dto != null) {
	        session.setAttribute("user", dto);

	        if ("admin".equals(dto.getUserId())) { // 관리자인 경우
	            return "login_html/manager_page.html"; // 관리자 전용 페이지로 이동
	        } else {
	            return "redirect:/main"; // 일반 사용자 페이지로 이동
	        }
	    }
	    return "redirect:/";
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////프로젝트 로그인 controller//////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	/* 카카오 로그인으로 인해 사용x
	 * @RequestMapping("/logout") 
	 * public String logout(HttpSession session) {
	 * session.invalidate(); // 세션을 무효화하여 로그아웃 처리 
	 * return "redirect:/"; // 로그아웃 후 메인페이지로 리다이렉트 
	 * }
	 */	
	
	
	
	@RequestMapping("/loginInput")
	public String login(String userId, String passwd, HttpSession session) {

	    UserDTO dto = userService.login(userId, passwd);
	    if (dto != null) {
	        session.setAttribute("user", dto);

	        if ("admin".equals(dto.getUserId())) { // 관리자인 경우
	            return "login_html/manager_page.html"; // 관리자 전용 페이지로 이동
	        } else {
	            return "redirect:/main"; // 일반 사용자 페이지로 이동
	        }
	    }
	    return "redirect:/";
	}

	@RequestMapping("/membership")
	public String membership() {
		return "login_html/membership";
	}

	@RequestMapping("/join")
	public String joinView() {
		return "login_html/join";
	}

	@RequestMapping("/myPage")
	public String myPage(HttpSession session, Model model) {
	    UserDTO dto = (UserDTO) session.getAttribute("user");
	    System.out.println(dto);
	    if (dto != null) {
	        System.out.println(dto);

	        String membershipNumber = dto.getMembershipNumber();
	        String carNumber = dto.getCarNumber();
	        String eMail = dto.geteMail();
	        model.addAttribute("membershipNumber", membershipNumber);
	        model.addAttribute("carNumber", carNumber);
	        model.addAttribute("eMail", eMail);
	        return "login_html/myPage";
	    } else {
	        model.addAttribute("membershipNumber", "null");
	        model.addAttribute("carNumber", "null");
	        model.addAttribute("eMail", "null");
	        return "login_html/myPage";
	    }
	}
	@RequestMapping("/updateCarNumber")
	public ModelAndView updateCarNumber(
	        @RequestParam("carNumber") String carNumber,
	        HttpSession session) {
	    UserDTO userDTO = (UserDTO) session.getAttribute("user");
	    String userId = userDTO.getUserId();

	    if (userId != null) {
	        // 차량번호 업데이트
	        userService.updateCarNumber(userId, carNumber);
	        return new ModelAndView("redirect:/myPage"); // 마이페이지로 리다이렉트
	    } else {
	        // 로그인되지 않았을 경우 처리
	        return new ModelAndView("redirect:/login"); // 로그인 페이지로 리다이렉트
	    }
	}

	@RequestMapping("/user/join/action")
	public String join(UserDTO dto) {
		MembershipService membershipService = new MembershipService();
		membershipService.signup(dto);
		System.out.println(dto.toString());
		int result = userService.insertUser(dto);
		return "login_html/join_Complete";
	}


	@RequestMapping("/manager_page")
	public ModelAndView userList(ModelAndView view) {
		List<UserDTO> list = userService.viewAlluser();
		System.out.println(list);
		view.addObject("list", list);
		view.setViewName("login_html/manager_page");
		return view;
	}

	@RequestMapping("/user/search")
	public ResponseEntity<String> searchUser(String kind, String search) {
		List<UserDTO> dto = userService.searchUser(kind, search);
		System.out.println(dto);
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	@RequestMapping("/join_Complete")
	public String Join_Complete() {
		return "redirect:/";
	}

	@RequestMapping("/idsearch")
	public String idsearch() {
		return "login_html/idsearch";
	}

	@RequestMapping("/idsearch_result")
	public String Idsearch_result() {
		return "login_html/idsearch_result";
	}

	@RequestMapping("/searchPasswd")
	public String SearchPasswd() {
		return "login_html/searchPasswd";
	}
	@RequestMapping("/comfirm/id")
	@ResponseBody
	public int checkId(@RequestParam("insertId") String insertId) throws Exception{
		boolean userIdValid = userService.checkId(insertId);
		System.out.println(userIdValid);
		if(!userIdValid) {
			return 0; // 현재 아이디가 일치하지 않음
		}
		return 1; //아이디 조회 성공
		 
	}

	@RequestMapping("/searchPasswd_Result")
	public String SearchPasswd_Result() {
		return "login_html/searchPasswd_Result";
	}

	@RequestMapping("/update/passwd")
	@ResponseBody
	public int checkPasswd(@RequestParam("nowPass") String nowPass,
			@RequestParam("newPass") String newPass) throws Exception{
		boolean userPassValid = userService.checkPasswd(nowPass);
		if(!userPassValid) {
			return 0; // 현재 비밀번호가 일치하지 않음
		}
		userService.updatePasswd(newPass);
		
		return 1; //비밀번호 변경 성공
		 
	}

	// 아이디 중복 검사
	@RequestMapping("join/checkid")
	@ResponseBody
	public int checkid(@RequestParam("id") String id, @RequestParam("type") String type) {
		return userService.UserCheckId(id, type);
	}

	// 회원번호 랜덤으로 생성하게 해주는 클래스 입니다. 보기 쉽게 하려고 controller에 넣었습니다.
	public class MembershipService {
		private static final int MEMBERSHIP_NUMBER_LENGTH = 8; // 회원번호 길이입니다.
		private static final String NUMBERS = "0123456789"; // 사용될 회원번호 숫자, 숫자로만 생성되는 회원번호 입니다.
		private Set<String> generatedMembershipNumbers = new HashSet<>();

		// 기존에 사용된 회원번호 목록을 설정
		public void setExistingMembershipNumbers(Set<String> membershipNumber) {
			generatedMembershipNumbers.addAll(membershipNumber);
		}

		public void signup(UserDTO dto) {
			// 회원 정보 검증 및 처리

			String membershipNumber = UniqueMembershipNumber();
			dto.setMembershipNumber(membershipNumber);

			// 회원 가입 로직 수행 및 응답 DTO 생성
		}

		private String UniqueMembershipNumber() {
			Random random = new Random();
			StringBuilder membershipNumber = new StringBuilder();

			while (true) {
				for (int i = 0; i < MEMBERSHIP_NUMBER_LENGTH; i++) {
					int index = random.nextInt(NUMBERS.length());
					char digit = NUMBERS.charAt(index);
					membershipNumber.append(digit);
				}

				String generatedNumber = membershipNumber.toString();
				if (!generatedMembershipNumbers.contains(generatedNumber)) {
					generatedMembershipNumbers.add(generatedNumber);
					return generatedNumber;
				}

				membershipNumber.setLength(0); // 생성할 때 마다 회원번호 초기화
			}
		}

	}

}

package com.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.dto.BoardDTO;
import com.project.dto.CommentDTO;
import com.project.dto.UserDTO;
import com.project.service.BoardService;
import com.project.service.UserService;
import com.project.vo.PageVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {

	private BoardService boardService;
	UserService userService;

	public BoardController(BoardService boardService, UserService userService) {
		this.boardService = boardService;
		this.userService = userService;
	}


	@RequestMapping("/board")
	public ModelAndView postList(@RequestParam(name = "pageNo", defaultValue = "1") int currPage, ModelAndView view) {
		List<BoardDTO> list = boardService.selectAllPost(currPage);
		List<BoardDTO> list2 = boardService.selectAllAnnounce();

		System.out.println(list.toString());
		view.addObject("list", list);
		view.addObject("list2", list2);
		int count = boardService.getCount();
		System.out.println(currPage);
		PageVO vo = new PageVO(count, currPage, 10, 5);
		System.out.println(vo.toString());
		view.addObject("page", vo);
		view.setViewName("board/qna_board_main");
		return view;
	}

	@RequestMapping("/board/post/{pno}")
	public ModelAndView detail(@PathVariable("pno") int pno, ModelAndView view) {
		boardService.addReadCount(pno);
		BoardDTO dto = boardService.selectPost(pno);
		List<CommentDTO> list = boardService.selectAllComment(pno);
		view.addObject("comment", list);
		view.addObject("board", dto);
		view.setViewName("board/post");
		return view;

	}

	@RequestMapping("/board/post/comment")
	public String insertComment(HttpServletRequest request, HttpSession session) {

		if (request.getSession().getAttribute("user") == null)
			return "redirect:/";
		int pno = Integer.parseInt(request.getParameter("pno"));
		String comment = request.getParameter("comment");
		String userNum = ((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber();
		CommentDTO dto = new CommentDTO(pno, comment, userNum);
		int result = boardService.insertBoardComment(dto);
		return "redirect:/board/post/" + pno;
	}

	@RequestMapping("/board/post/write")
	public String callInsertPost(HttpSession session) {
		return "board/post_write";
	}

	@RequestMapping("/board/post/writePost")
	public String InsertPost(HttpServletRequest request, HttpSession session) {
		String post_title = request.getParameter("title");
		String post_content = request.getParameter("content");
		String user_num = ((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber();
		// private int post_num; <- sequence
		// private String post_date; <- date
		// private int read_count; <- 0
		int post_type = 0;
		if (((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber().equals("admin")) {
			post_type = 1;
		}
		BoardDTO dto = new BoardDTO(post_title, post_content, user_num, 0, post_type);
		int result = boardService.insertPost(dto);
		return "redirect:/board";
	}

	@RequestMapping("/board/post/update/{pno}")
	public String UpdatePost(@PathVariable("pno") int pno, HttpServletRequest request, HttpSession session) {
		String post_title = request.getParameter("title");
		String post_content = request.getParameter("content");

		BoardDTO dto = new BoardDTO(pno, post_title, post_content);
		int result = boardService.updatePost(dto);
		return "redirect:/board";

	}

	@RequestMapping("/board/post/updateView/{pno}")
	public ModelAndView UpdatePostView(@PathVariable("pno") int pno, ModelAndView view) {
		boardService.addReadCount(pno);
		BoardDTO dto = boardService.selectPost(pno);
		view.addObject("board", dto);
		view.setViewName("board/post_update");
		return view;

	}

	@RequestMapping("/board/post/delete/{pno}")
	public String deletePost(@PathVariable("pno") int pno, HttpServletRequest request) {
		System.out.println(pno);
		int result = boardService.deletePost(pno);

		return "redirect:/board";
	}

	@RequestMapping("comment/delete/{cno}/{pno}")
	public String deleteComment(@PathVariable("cno") int cno, @PathVariable("pno") int pno,
			HttpServletRequest request) {
		System.out.println(cno + " " + pno);
		int result = boardService.deleteComment(cno);

		return "redirect:/board/post/" + pno;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////// 앱//////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping("/app")
	public String appLogin(String userId, String passwd, HttpSession session) {

		UserDTO dto = userService.login("admin", "123456");
		session.setAttribute("user", dto);
			
		return "redirect:/app/board";
	}

	@RequestMapping("/app/board")
	public ModelAndView appPostList(@RequestParam(name = "pageNo", defaultValue = "1") int currPage,
			ModelAndView view) {
		List<BoardDTO> list = boardService.selectAllPost(currPage);
		List<BoardDTO> list2 = boardService.selectAllAnnounce();

		System.out.println(list.toString());
		view.addObject("list", list);
		view.addObject("list2", list2);
		int count = boardService.getCount();
		System.out.println(currPage);
		PageVO vo = new PageVO(count, currPage, 10, 5);
		System.out.println(vo.toString());
		view.addObject("page", vo);
		view.setViewName("board_app/qna_board_main_app");
		return view;
	}

	@RequestMapping("/app/post/{pno}")
	public ModelAndView appDetail(@PathVariable("pno") int pno, ModelAndView view) {
		boardService.addReadCount(pno);
		BoardDTO dto = boardService.selectPost(pno);
		List<CommentDTO> list = boardService.selectAllComment(pno);
		view.addObject("comment", list);
		view.addObject("board", dto);
		view.setViewName("board_app/post_app");
		return view;

	}
	
	@RequestMapping("/app/post/app/comment")
	public String insertCommentApp(HttpServletRequest request, HttpSession session) {

		if (request.getSession().getAttribute("user") == null)
			return "redirect:/";
		int pno = Integer.parseInt(request.getParameter("pno"));
		String comment = request.getParameter("comment");
		String userNum = ((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber();
		CommentDTO dto = new CommentDTO(pno, comment, userNum);
		int result = boardService.insertBoardComment(dto);
		return "redirect:/app/post/" + pno;
	}
	
	
	@RequestMapping("/app/post/updateView/{pno}")
	public ModelAndView AppUpdatePostView(@PathVariable("pno") int pno, ModelAndView view) {
		boardService.addReadCount(pno);
		BoardDTO dto = boardService.selectPost(pno);
		view.addObject("board", dto);
		view.setViewName("board_app/post_update_app");
		return view;

	}

	@RequestMapping("/app/post/delete/{pno}")
	public String appDeletePost(@PathVariable("pno") int pno, HttpServletRequest request) {
		System.out.println(pno);
		int result = boardService.deletePost(pno);

		return "redirect:/app/board";
	}

	@RequestMapping("/app/comment/delete/{cno}/{pno}")
	public String appDeleteComment(@PathVariable("cno") int cno, @PathVariable("pno") int pno,
			HttpServletRequest request) {
		System.out.println(cno + " " + pno);
		int result = boardService.deleteComment(cno);

		return "redirect:/app/post/" + pno;
	}
	
	@RequestMapping("/app/post/write")
	public String callAppInsertPost(HttpSession session) {
		return "board_app/post_write_app";
	}

	@RequestMapping("/app/post/writePost")
	public String appInsertPost(HttpServletRequest request, HttpSession session) {
		String post_title = request.getParameter("title");
		String post_content = request.getParameter("content");
		String user_num = ((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber();
		// private int post_num; <- sequence
		// private String post_date; <- date
		// private int read_count; <- 0
		int post_type = 0;
		if (((UserDTO) request.getSession().getAttribute("user")).getMembershipNumber().equals("admin")) {
			post_type = 1;
		}
		BoardDTO dto = new BoardDTO(post_title, post_content, user_num, 0, post_type);
		int result = boardService.insertPost(dto);
		return "redirect:/app";
	}
	
	@RequestMapping("/app/post/update/{pno}")
	public String appUpdatePost(@PathVariable("pno") int pno, HttpServletRequest request, HttpSession session) {
		String post_title = request.getParameter("title");
		String post_content = request.getParameter("content");

		BoardDTO dto = new BoardDTO(pno, post_title, post_content);
		int result = boardService.updatePost(dto);
		return "redirect:/app";

	}
}

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
import com.project.vo.PageVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {

	private BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping("/board")
	public ModelAndView postList(@RequestParam(name = "pageNo", defaultValue = "1") int currPage, ModelAndView view) {
		List<BoardDTO> list = boardService.selectAllPost(currPage);
		List<BoardDTO> list2 = boardService.selectAllAnnounce();

		System.out.println(list.toString());
		view.addObject("list", list);
		view.addObject("list2", list2);
		int count = boardService.getCount();
		PageVO vo = new PageVO(count, currPage, 10, 5);
		view.addObject("page",vo);
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
		
		if(request.getSession().getAttribute("user") == null) return "redirect:/";
		int pno = Integer.parseInt(request.getParameter("pno"));
		String comment = request.getParameter("comment");
		String userNum = ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
		CommentDTO dto = new CommentDTO(pno, comment, userNum);
		int result = boardService.insertBoardComment(dto);
		return "redirect:/board/post/"+pno;
	}
	
	@RequestMapping("/board/post/write")
	public String callInsertPost(HttpSession session ) {
		return "board/post_write";
	}
	@RequestMapping("/board/post/writePost")
	public String InsertPost(HttpServletRequest request, HttpSession session ) {
		String post_title = request.getParameter("title");
		String post_content = request.getParameter("content");
		String user_num= ((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber();
		//private int post_num; <- sequence
		//private String post_date; <- date
		//private int read_count; <- 0
		int post_type = 0;
		if(((UserDTO)request.getSession().getAttribute("user")).getMembershipNumber().equals("admin")) {
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
	
	

}

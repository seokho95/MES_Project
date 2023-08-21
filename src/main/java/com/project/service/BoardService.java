package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.BoardDTO;
import com.project.dto.CommentDTO;
import com.project.mapper.BoardMapper;

@Service
public class BoardService {
	private BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}


	public List<BoardDTO> selectAllPost(int currPage) {
		return boardMapper.selectAllPost(currPage);
	}


	public List<BoardDTO> selectAllAnnounce() {
		return boardMapper.selectAllAnnounce();
	}


	public BoardDTO selectPost(int pno) {
		return boardMapper.selectPost(pno);
	}


	public int addReadCount(int pno) {
		return boardMapper.addReadCount(pno);
	}


	public List<CommentDTO> selectAllComment(int pno) {
		return boardMapper.selectAllComment(pno);
	}


	public int insertBoardComment(CommentDTO dto) {
		return boardMapper.insertBoardComment(dto);
		
	}


	public int insertPost(BoardDTO dto) {
		return boardMapper.insertPost(dto);
	}


	public int getCount() {
		return boardMapper.getCount();
	}


	public int updatePost(BoardDTO dto) {
		return boardMapper.updatePost(dto);
	}


	public int deletePost(int pno) {
		return boardMapper.deletePost(pno);
	}


	public int deleteComment(int cno) {
		return boardMapper.deleteComment(cno);
	}

}

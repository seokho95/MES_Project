package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.CommentDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectAllPost(int currPage);

	List<BoardDTO> selectAllAnnounce();

	BoardDTO selectPost(int pno);

	int addReadCount(int pno);

	List<CommentDTO> selectAllComment(int pno);

	int insertBoardComment(CommentDTO dto);

	int insertPost(BoardDTO dto);

	int getCount();

	int updatePost(BoardDTO dto);

	int deletePost(int pno);

	int deleteComment(int cno);
	
}

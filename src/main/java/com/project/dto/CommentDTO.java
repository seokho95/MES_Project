package com.project.dto;

import org.apache.ibatis.type.Alias;

@Alias("comment")
public class CommentDTO {
	private int post_num;
	private int comment_num;
	private String comment_content;
	private String user_num;
	private String user_name;
	
	
	public CommentDTO(int post_num, String comment_content, String user_num) {
		super();
		this.post_num = post_num;
		this.comment_content = comment_content;
		this.user_num = user_num;
	}
	public CommentDTO(int post_num, int comment_num, String comment_content, String user_name) {
		this.post_num = post_num;
		this.comment_num = comment_num;
		this.comment_content = comment_content;
		this.user_name = user_name;
	}
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	@Override
	public String toString() {
		return "CommentDTO [post_num=" + post_num + ", comment_num=" + comment_num + ", comment_content="
				+ comment_content + ", user_name=" + user_name + "]";
	}
	
	
	
	
}

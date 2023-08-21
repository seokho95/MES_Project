package com.project.dto;

import org.apache.ibatis.type.Alias;

@Alias("board")
public class BoardDTO {
	private int post_num;
	private String post_title;
	private String post_date;
	private String post_content;
	private String user_name;
	private int read_count;
	private int post_type;
	private String user_num;
	
	public BoardDTO() {}
	

	public BoardDTO(int post_num, String post_title, String post_date, String post_content, String user_name,
			int read_count, int post_type) {
		this.post_num = post_num;
		this.post_title = post_title;
		this.post_date = post_date;
		this.post_content = post_content;
		this.user_name = user_name;
		this.read_count = read_count;
		this.post_type = post_type;
	}




	public BoardDTO(int post_num, String post_title, String post_content) {
		super();
		this.post_num = post_num;
		this.post_title = post_title;
		this.post_content = post_content;
	}


	public BoardDTO(String post_title, String post_content, String user_num, int read_count, int post_type) {
		super();
		this.post_title = post_title;
		this.post_content = post_content;
		this.user_num= user_num;
		this.read_count = read_count;
		this.post_type = post_type;
	}

	public int getPostNum() {
		return post_num;
	}

	public void setPostNum(int postNum) {
		this.post_num = postNum;
	}

	public String getPostTitle() {
		return post_title;
	}

	public void setPostTitle(String postTitle) {
		this.post_title = postTitle;
	}

	public String getPostDate() {
		return post_date;
	}
	public String getPostDateShort() {
		return post_date.substring(2,10);
	}

	public void setPostDate(String postDate) {
		this.post_date = postDate;
	}

	public String getPostContent() {
		return post_content;
	}

	public void setPostContent(String postContent) {
		this.post_content = postContent;
	}

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String userName) {
		this.user_name = userName;
	}


	public int getReadCount() {
		return read_count;
	}

	public void setReadCount(int readCount) {
		this.read_count = readCount;
	}

	public int getPostType() {
		return post_type;
	}

	public void setPostType(int postType) {
		this.post_type = postType;
	}
	
	
	
	public String getUser_num() {
		return user_num;
	}


	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}


	@Override
	public String toString() {
		return "BoardDTO [post_num=" + post_num + ", post_title=" + post_title + ", post_date=" + post_date
				+ ", post_content=" + post_content + ", user_name=" + user_name + ", read_count=" + read_count
				+ ", post_type=" + post_type + ", user_num=" + user_num + "]";
	}


	
	
	
	
	
}

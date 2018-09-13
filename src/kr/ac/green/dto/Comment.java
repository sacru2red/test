package kr.ac.green.dto;

import java.sql.Date;

public class Comment {
	private int player_code;
	private String comment;
	private Date edit_time;
	
	public Comment() {
		
	}
	
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public Comment(int player_code, String comment, Date edit_time) {
		super();
		this.player_code = player_code;
		this.comment = comment;
		this.edit_time = edit_time;
	}

	@Override
	public String toString() {
		return "Comment [player_code=" + player_code + ", comment=" + comment
				+ ", edit_time=" + edit_time + "]";
	}

	public int getPlayer_code() {
		return player_code;
	}
	public void setPlayer_code(int player_code) {
		this.player_code = player_code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

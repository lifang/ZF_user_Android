package com.example.zf_android.entity;

public class GoodCommentEntity {
	//            "content": "jh",
//    "id": 13,
//    "name": "admin12",
//    "score": 5,
//    "created_at": "2015-02-16 15:28:19"
	private String content;
	private int id;
	private String name;
	private int score;
	private String created_at;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
}

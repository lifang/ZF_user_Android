package com.example.zf_android.entity;
/***
 * 
*    
* ����ƣ�MessageEntity   
* ��������   ������Ϣʵ��
* �����ˣ� ljp 
* ����ʱ�䣺2015-3-3 ����2:21:53   
* @version    
*
 */
public class MessageEntity {
	//"content":"������������","id":"2","title":"���Ǳ���","create_at":"2015-02-12 19:42:52"
	private String content;
	private String id;
	private String title;
	private String create_at;
	private Boolean ischeck;
	private Boolean status;
	
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getIscheck() {
		return ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreate_at() {
		return create_at;
	}
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}
	
}

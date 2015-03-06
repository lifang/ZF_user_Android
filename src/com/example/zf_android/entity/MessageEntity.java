package com.example.zf_android.entity;
/***
 * 
*    
* 类名称：MessageEntity   
* 类描述：   个人消息实体
* 创建人： ljp 
* 创建时间：2015-3-3 下午2:21:53   
* @version    
*
 */
public class MessageEntity {
	//"content":"我是内容详情","id":"2","title":"还是标题","create_at":"2015-02-12 19:42:52"
	private String content;
	private String id;
	private String title;
	private String create_at;
	private Boolean ischeck;
	
	
	
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

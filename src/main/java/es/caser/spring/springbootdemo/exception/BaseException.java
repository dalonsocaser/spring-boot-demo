package es.caser.spring.springbootdemo.exception;

public class BaseException extends RuntimeException{	
	private static final long serialVersionUID = -1164943843737783593L;
	String code;
	String desc;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public BaseException(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	
}

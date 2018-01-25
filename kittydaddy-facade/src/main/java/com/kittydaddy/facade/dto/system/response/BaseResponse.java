package com.kittydaddy.facade.dto.system.response;
import java.util.Collection;

/**
 * @author kitty daddy
 *  基本返回dto
 */
public class BaseResponse {
	//返回码
	private Integer code;
	
	//返回信息
	private String msg;
	
	//返回总数量
	private Long count;
	
	//返回总数据
	private Collection<?> data;
	
	
	public BaseResponse(Integer code, String msg, Long count, Collection<?> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	/**
	 * 返回成功
	 * @param msg
	 * @param data
	 * @return
	 */
	public static BaseResponse getSuccessResp(String msg,Long count,Collection<?> data){
		BaseResponse resp = new BaseResponse(0, msg, count, data);
		return resp;
	}
	
	/**
	 * 返回异常
	 * @param msg
	 * @param data
	 * @return
	 */
	public static BaseResponse getFailureResp(String msg,Long count,Collection<?> data){
		BaseResponse resp = new BaseResponse(-200, msg, count, data);
		return resp;
	}
	
	/**
	 * 返回异常
	 * @param msg
	 * @param data
	 * @return
	 */
	public static BaseResponse getFailureResp(String msg,Long count){
		BaseResponse resp = new BaseResponse(-200, msg, count, null);
		return resp;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Collection<?> getData() {
		return data;
	}

	public void setData(Collection<?> data) {
		this.data = data;
	}
}

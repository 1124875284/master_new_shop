package com.hzq.enumss;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Title: ResponseCode</p>
 * <p>Description: 响应码</p>
 * <p>Author: BeautifulSoup</p>
 * <p>Time: 2019年2月12日 下午3:09:08</p>
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
	SUCCESS(10001,"请求成功"),
	ERROR(10000,"请求失败"),
	ILLEGAL_ARGUMENTS(1,"请求参数不合法")
	;
	
	private final Integer code;
	private final String desc;
	
}

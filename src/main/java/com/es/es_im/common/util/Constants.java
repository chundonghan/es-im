package com.es.es_im.common.util;

public abstract class Constants {
	public final static String PASSWORD_SALT = "es";
	public final static String DEFAULT_AVATAR = "img/default-avatar.jpg";

	public static final String PHONE_EMPTY_RET_CODE = "请输入手机号";
	public static final String PHONE_ERROR_RET_CODE = "手机号输入不正确";
	public static final String PHONE_SUCC_RET_CODE = "此手机号可以使用";
	public static final String PHONE_EXIST_RET_CODE = "此手机号已被注册";
	public static final String TOO_MUCH_FREQUENTLY_OPER_RET_CODE = "操作过于频繁，请稍后再试";
	public static final String TOO_MANY_OPER_RET_CODE = "操作次数过多，请明天再试";
	public static final String PHONE_SMS_SUCC_RET_CODE = "发送成功";

	public static final String ACCOUNT_EXIST_RET_CODE = "此账号已存在";
	public static final String ACCOUNT_ERROR_RET_CODE = "以字母开头,长度在6-18之间,只能包含字符、数字和下划线.";
	public static final String ACCOUNT_SUCC_RET_CODE = "此账号可以使用";

	public static final String PASSWORD_ERROR_RET_CODE = "以字母开头,长度在6-18之间,只能包含字符、数字和下划线.";
	public static final String PASSWORD_EQUALS_ACCOUNT_RET_CODE = "账号和密码不能相同";
	public static final String PASSWORD_SUCC_RET_CODE = "此密码可以使用";
	
	public static final String RESET_PASSWORD_NOT_EQUALS_RET_CODE = "新密码与确认密码不相同";
	
	
	/* redis key */
	public static final String ONLINE_USERS = "online_users";
	// 将每个在线私聊窗口管理于 “聊天市场”中
	public static final String CHAT_MARKET = "chat_market";
	// 私聊链接
	public static final String CHAT_LINK = "chat_link";
	
	

}

package util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString();  // 得到随机字符串
	}
	
}

package info.twiceyuan.weather.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将输入流转换为字符串
 * */
public class Stream2String {
	
	public static String get(InputStream is) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int rd;
		byte[] buffer = new byte[1024];
		
		try {
			while((rd = is.read(buffer)) != -1 ) {
                out.write(buffer, 0, rd);
			}
			String temp = out.toString();
			
			return temp;
		} catch (IOException e) {
			System.out.println("字符串转换失败！");
			e.printStackTrace();
		}
		return null;
	}

}

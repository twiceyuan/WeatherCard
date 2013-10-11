package info.twiceyuan.weather.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 从链接中获取输入流
 * 输入：URL
 * 输出：InputStream流
 **/
public class StreamGetter {
	
	public static InputStream get(String url) {

        // 获取HttpClient类
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		HttpContext localContext = new BasicHttpContext(); 
		
		try {

			HttpResponse response = httpClient.execute(httpGet,localContext);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				httpGet.abort();
			} else {
				HttpEntity httpEntity = response.getEntity(); // 获取返回数据
				String string = EntityUtils.toString(httpEntity,"UTF-8").trim();
				
				InputStream is = new ByteArrayInputStream(string.getBytes("UTF-8"));
				
				return is;
			}
		} catch (Exception e) {

			System.out.println("输入流获取失败: " + e.toString());
		}
		return null;
	}
}

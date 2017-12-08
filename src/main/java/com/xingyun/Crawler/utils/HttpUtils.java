package com.xingyun.Crawler.utils;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

public class HttpUtils {
	/**
	 * 发送 get请求,同步请求
	 */
	public static String get(String url) {
		if (TextUtils.isEmpty(url)) {
			return "";
		}
		if (!isUrl(url)) {
			return "";
		}
		System.out.println("get!!!"+url);
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    try {
	        HttpGet httpget = new HttpGet(url);
	        CloseableHttpResponse response = httpclient.execute(httpget);
	        try {
	            // 获取响应实体  
	            HttpEntity entity = response.getEntity();
	            // 打印响应状态  
	            StatusLine line = response.getStatusLine();
	            if (line.getStatusCode()==200&&entity != null) {
					return EntityUtils.toString(entity);
				}
	        } finally {
	            response.close();
	        }
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        // 关闭连接,释放资源  
	        try {
	            httpclient.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return "";
	}
	
	public static boolean isUrl(String url) {
		Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");  
		return pattern.matcher(url).matches();
	}
	
}

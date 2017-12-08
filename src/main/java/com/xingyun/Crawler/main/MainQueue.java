package com.xingyun.Crawler.main;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.util.TextUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xingyun.Crawler.bean.UrlBean;
import com.xingyun.Crawler.db.UrlBeanDao;
import com.xingyun.Crawler.utils.HttpUtils;

public class MainQueue {
	private String mainUrl = "http://3g.d1xz.net/test/";
	private Queue<UrlBean> queue = new LinkedList<UrlBean>();
	private UrlBeanDao urlBeanDao = new UrlBeanDao();
	
	public void go() {
		queue.add(new UrlBean(mainUrl));
		
		while (!queue.isEmpty()) {
			UrlBean bean = queue.poll();
			String url = bean.getUrl();
			if (isExit(url)) {
				System.out.println("该url已经读取过了");
				continue;
			}
			String html = HttpUtils.get(url);
			saveUrl(bean);
			addQueue(html);
		}
		
	}
	
	private boolean isExit(String url) {
		return urlBeanDao.isExit(url);
	}
	
	private void addQueue(String html){
		if (TextUtils.isEmpty(html)) {
			return;
		}
		Document doc = Jsoup.parse(html);
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
		  String linkHref = link.attr("href");
		  String linkText = link.text();
		  if (!HttpUtils.isUrl(linkHref)) {
			continue;
		  }
		  System.out.println(linkHref+" ---  "+linkText);
		  UrlBean bean = new UrlBean(linkHref);
		  bean.setText(linkText);
		  bean.setSource("crawler");
		  
		  queue.add(bean);
		}
	}
	
	private void saveUrl(UrlBean url) {
		url.setCreateTime(new Date());
		urlBeanDao.saveUrlBean(url);
	}
	
}

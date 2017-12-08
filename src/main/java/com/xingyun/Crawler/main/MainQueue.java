package com.xingyun.Crawler.main;

import java.util.Date;
import java.util.Stack;

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

	// 利用栈的结构可以深度遍历，而队列结构是广度遍历
	private Stack<UrlBean> stack = new Stack<UrlBean>();
	private UrlBeanDao urlBeanDao = new UrlBeanDao();

	public void go() {
		stack.push(new UrlBean(mainUrl));

		while (!stack.isEmpty()) {
 			UrlBean bean = stack.pop();
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

	private void addQueue(String html) {
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
			System.out.println(linkHref + " ---  " + linkText);
			UrlBean bean = new UrlBean(linkHref);
			bean.setText(linkText);
			bean.setSource("crawler");

			if (!isStackExit(bean)) {
				stack.push(bean);
			}
		}
	}

	private void saveUrl(UrlBean url) {
		url.setCreateTime(new Date());
		urlBeanDao.saveUrlBean(url);
	}

	private boolean isStackExit(UrlBean bean) {
		for (int i = 0; i < stack.size(); i++) {
			if (stack.get(i).getUrl().equals(bean.getUrl())) {
				return true;
			}
		}
		return false;
	}

}

package com.xingyun.Crawler.db;

import com.xingyun.Crawler.bean.UrlBean;

public class UrlBeanDao extends BaseDao{
	
	public boolean saveUrlBean(UrlBean bean) {
		return super.save(bean);
	}
	
	public boolean isExit(String url) {
		String hql = "from UrlBean where url='"+url+"'";
		int count = getList(UrlBean.class, hql).size();
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

}

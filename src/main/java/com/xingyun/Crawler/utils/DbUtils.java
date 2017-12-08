package com.xingyun.Crawler.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbUtils {

	private static DbUtils dbUtils;
	private SessionFactory factory;

	private DbUtils() {
		Configuration cfg = new Configuration().configure();
		factory = cfg.buildSessionFactory();
	}

	public synchronized static DbUtils getInstance() {
		if (dbUtils == null) {
			return new DbUtils();
		}
		return dbUtils;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	

}

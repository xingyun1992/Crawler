package com.xingyun.Crawler.db;

import java.io.Serializable;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.xingyun.Crawler.utils.DbUtils;

public class BaseDao {
	
	protected SessionFactory factory;
	
	{
		factory = DbUtils.getInstance().getFactory();
	}
	
	protected boolean save(Object object) {
		Session session = null;
		try {
			session = factory.openSession();
			// 开启事务
			session.beginTransaction();
			session.save(object);
			// 提交事务
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事务
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					// 关闭session
					session.close();
				}
			}
		}
		return false;
	}
	
	protected boolean update(Object object) {
		Session session = null;
		try {
			session = factory.openSession();
			// 开启事务
			session.beginTransaction();
			session.update(object);
			// 提交事务
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事务
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					// 关闭session
					session.close();
				}
			}
		}
		return false;
	}
	
	protected boolean delete(Object object) {
		Session session = null;
		try {
			session = factory.openSession();
			// 开启事务
			session.beginTransaction();
			session.delete(object);
			// 提交事务
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事务
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					// 关闭session
					session.close();
				}
			}
		}
		return false;
	}
	
	
	protected <T> T get(Class<T> clszz,Serializable id) {
		Session session = null;
		try {
			session = factory.openSession();
			return (T) session.get(clszz, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return null;
	}
	
	protected <T> List<T> getList(Class<T> clszz,String hql) {
		Session session = null;
		try {
			session = factory.openSession();
			Query query = session.createQuery(hql);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return null;
	}
	
	
	
}

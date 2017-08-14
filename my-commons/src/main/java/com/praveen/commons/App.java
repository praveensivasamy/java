package com.praveen.commons;

import java.util.List;

import com.praveen.commons.hibernate.BaseInterceptor;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		HibernateProvider sourceProvider = HibernateProvider.instance("hibernate.cfg.xml", new BaseInterceptor());
		JpaDao dao = JpaDao.instance(sourceProvider);
		try {
			System.out.println(dao.toString());
			@SuppressWarnings("rawtypes") List l = dao.query("from TUser ");
			System.out.println(l);

		} finally {
			HibernateProvider.tearDownAll();
		}

		/*try {
		Connection conn = DBConnectionProvider.instance().getConnection();
		System.out.println("Connection :" + conn.isClosed());
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}*/
	}
}

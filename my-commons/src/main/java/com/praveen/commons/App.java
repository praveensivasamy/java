package com.praveen.commons;

import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
	System.out.println("Hello World!");

	HibernateProvider sourceProvider = HibernateProvider.instance("hibernate.cfg.xml", null);

	JpaDao dao = JpaDao.instance(sourceProvider);
	try {

	    Object user = dao.query("from TUser").size();
	    System.out.println(user.toString());

	} finally {

	}

    }
}

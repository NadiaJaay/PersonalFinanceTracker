package com.PersonalFinance.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.PersonalFinance.dao.UserDao;

@WebListener
public class AppStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application is starting...");
        try {
            UserDao.createAdminIfNotExists(); // Initialize the admin account
            System.out.println("Admin user initialized.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize admin user.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is shutting down...");
    }
}

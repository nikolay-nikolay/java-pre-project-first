package jm.task.core.jdbc;



import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.getAllUsers();
        userDaoHibernate.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Иван", "Иванов", (byte) 25);
        System.out.println("User с именем – Иван добавлен в базу данных");

        userService.saveUser("Петр", "Петров", (byte) 30);
        System.out.println("User с именем – Петр добавлен в базу данных");

        userService.saveUser("Сергей", "Сергеев", (byte) 35);
        System.out.println("User с именем – Сергей добавлен в базу данных");

        userService.saveUser("Анна", "Аннова", (byte) 28);
        System.out.println("User с именем – Анна добавлен в базу данных");

        List<User> users = userDaoHibernate.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userDaoHibernate.cleanUsersTable();

        userDaoHibernate.dropUsersTable();
    }
}

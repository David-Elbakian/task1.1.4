package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final User user1 = new User("David", "Elbakian", (byte) 38);
    private static final User user2 = new User("Jim", "Carey", (byte) 55);
    private static final User user3 = new User("John", "Wick", (byte) 50);
    private static final User user4 = new User("Nikki", "Minnaj", (byte) 26);

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();


        List<User> users = userDao.getAllUsers();
        users.forEach(user -> System.out.println(user.getName() + " " + user.getLastName()));

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
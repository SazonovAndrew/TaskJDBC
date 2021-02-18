
package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {


        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Frodo", "Baggins", (byte) 50);
        userService.saveUser("Samwise", "Gamgee", (byte) 38);
        userService.saveUser("Meriadoc", "Brandybuck", (byte) 36);
        userService.saveUser("Peregrin", "Took", (byte) 28);

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE users " +
                    "(id INTEGER not NULL auto_increment primary key, " +
                    "name VARCHAR(255) not null , " +
                    "lastName VARCHAR(255) not null , " +
                    "age INTEGER)");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE users");
            System.out.println("Таблица удалена");
        }catch (SQLException e){
            System.out.println("Ошибка при удалении таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = util.getConnection()
                .prepareStatement("INSERT INTO users(name, lastName, age) values(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.execute();
            System.out.printf("User с именем - %s добавлен в базу%n", name);
        }catch (SQLException e){
            System.out.println("Ошибка при добавлении строки");
        }

    }
    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = util.getConnection()
                .prepareStatement("DELETE FROM users WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            System.out.println("Таблица удалена");

            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        }catch (SQLException e){
            System.out.println("Ошибка при получении списка");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("Таблица очищена");
        }catch (SQLException e){
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}

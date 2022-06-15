package dao.imlp;

import entities.User;
import dao.interfaces.UserDaoInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserDaoInterface {
    private Connection connection;
    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";
    private final static String PASSWORD_COLUMN = "password";
    private Statement statement;

    public UserDao(final Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);

        return new User(id, name, password);
    }

    @Override
    public List<User> findAll() {
        String queryString = "SELECT * FROM users";
        List<User> userList = new ArrayList();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                User user = getUser(resultSet);
                userList.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public User findById(long id){
        String queryString = "SELECT * FROM users WHERE users.id=" + id;
        User user = new User();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            user = getUser(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    };

    @Override
    public long save(User user) {
        String queryString = "INSERT INTO users (name, password) VALUES ('";
        queryString += user.getName()+"', '"+user.getPassword()+"') RETURNING id" ;

        long id = 0;

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    };

    @Override
    public void update(long id, User user) {
        String queryString = "UPDATE users SET name = '"+user.getName()
                +"', password = '"+user.getPassword()+"'"
                +" WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };


    public void delete(long id) {
        String queryString = "DELETE FROM users WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };
}

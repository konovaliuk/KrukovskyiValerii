package dao;

import dao.imlp.ReportDao;
import dao.imlp.TournamentDao;
import dao.imlp.UserDao;
import dao.interfaces.ReportDaoInterface;
import dao.interfaces.TournamentDaoInterface;
import dao.interfaces.UserDaoInterface;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
    public static UserDaoInterface createUserDao (Connection connection) throws SQLException {
        return new UserDao(connection);
    }
    public static TournamentDaoInterface createTournamentDao (Connection connection) throws SQLException {
        return new TournamentDao(connection);
    }
    public static ReportDaoInterface createReportDao (Connection connection) throws  SQLException {
        return new ReportDao(connection);
    }
}

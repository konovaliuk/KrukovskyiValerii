import connection.ConnectionPool;
import dao.DaoFactory;
//import dao.IPackDao;
//import dao.IQuestionsDao;
import dao.interfaces.ReportDaoInterface;
import dao.interfaces.TournamentDaoInterface;
import dao.interfaces.UserDaoInterface;
//import entities.Pack;
import entities.Report;
import entities.Tournament;
import entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitProject {
    public static void main(String args[]) {

        testUserDao();

        testTournamentDao();

        testReportsDao();

    }

    private static void testUserDao(){
        try {
            printIndent("User");
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection1 = connectionPool.getConnection();

            UserDaoInterface userDao = DaoFactory.createUserDao(connection1);

            System.out.println("\t*Finding user by id 1*");
            System.out.println(userDao.findById(1).toString());

            System.out.println("\n\t*Inserting new user*");
            User user1 = new User("TempUsername", "TempPassword");
            long new_id = userDao.save(user1);
            printAll(userDao.findAll());

            System.out.println("\n\t*Updating pack*");
            user1.setPassword("TempPasswordUpdated");
            userDao.update(new_id , user1);
            printAll(userDao.findAll());

            System.out.println("\n\t*Deleting user*");
            userDao.delete(new_id);
            printAll(userDao.findAll());

            connectionPool.releaseConnection(connection1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private static void testTournamentDao(){
        try {
            printIndent("Tournament");
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection1 = connectionPool.getConnection();
            TournamentDaoInterface tournamentDao = DaoFactory.createTournamentDao(connection1);

            System.out.println("\t*Finding tournament by id 1*");
            System.out.println(tournamentDao.findById(1).toString());

            System.out.println("\n\t*Inserting new tournament*");
            Tournament tournament1 = new Tournament("NewTestTournament", "Common_description");
            long new_id = tournamentDao.save(tournament1);
            printAll(tournamentDao.findAll());

            System.out.println("\n\t*Updating tournament*");
            tournament1.setDescription("New_description");
            tournamentDao.update(new_id , tournament1);
            printAll(tournamentDao.findAll());

            System.out.println("\n\t*Deleting tournament*");
            tournamentDao.delete(new_id);
            printAll(tournamentDao.findAll());

            connectionPool.releaseConnection(connection1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private static void testReportsDao(){
        try {
            printIndent("Report");
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection1 = connectionPool.getConnection();
            ReportDaoInterface reportDao = DaoFactory.createReportDao(connection1);

            System.out.println("\t*Finding report by id 1*");
            System.out.println(reportDao.findById(1).toString());

            System.out.println("\n\t*Inserting new report*");
            Report report1 = new Report(2, 2, "cheating!", "checking");
            long new_id = reportDao.save(report1);
            printAll(reportDao.findAll());

            System.out.println("\n\t*Updating report*");
            report1.setDecision("BANNED");
            reportDao.update(new_id , report1);
            printAll(reportDao.findAll());

            System.out.println("\n\t*Deleting report*");
            reportDao.delete(new_id);
            printAll(reportDao.findAll());

            connectionPool.releaseConnection(connection1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private static void testConnectionPool(){
        printIndent("Connection Pool");
        try {
            List<Connection> connectionList = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();

            System.out.print("Initial amount of coonections in pool: ");
            System.out.println(connectionPool.amountOfFreeConnections());

            System.out.println("  *Getting 100 connections from the pool*");
            for (int i = 0; i < 100; i++)
                connectionList.add(connectionPool.getConnection());

            System.out.print("Amount of free connections in pool: ");
            System.out.println(connectionPool.amountOfFreeConnections());

            System.out.println("  *Free all 100 connections from the pool*");
            for (int i = 99; i > -1; i--) {
                connectionPool.releaseConnection(connectionList.get(i));
                connectionList.remove(i);
            }

            System.out.print("Amount of free connections in pool: ");
            System.out.println(connectionPool.amountOfFreeConnections());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("______________________________________");
    }

    private static void printIndent(String tableName) {
        System.out.println("\n______________________________________\n\t\t---" + tableName + "---\n");
        return;
    }

    private static void printAll(List questionList){
        questionList.forEach((question) -> {
            System.out.println(question);
        });
        return;
    }
}

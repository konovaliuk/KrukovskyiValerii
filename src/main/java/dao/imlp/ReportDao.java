package dao.imlp;

import entities.Report;
import dao.interfaces.ReportDaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao implements ReportDaoInterface {
    private Connection connection;
    private final static String ID_COLUMN = "id";
    private final static String VIOLATOR_ID_COLUMN = "violator_id";
    private final static String TOURNAMENT_ID_COLUMN = "tournament_id";
    private final static String DESCRIPTION_COLUMN = "description";
    private final static String DECISION_COLUMN = "decision";
    private Statement statement;

    public ReportDao(final Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
    }

    public Report getReport(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        long violatorId = resultSet.getLong(VIOLATOR_ID_COLUMN);
        long tournamentId = resultSet.getLong(TOURNAMENT_ID_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        String decision = resultSet.getString(DECISION_COLUMN);

        return new Report(id, violatorId, tournamentId, description, decision);
    }

    @Override
    public List<Report> findAll() {
        String queryString = "SELECT * FROM reports";
        List<Report> reportList = new ArrayList<Report>();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                Report report = getReport(resultSet);
                reportList.add(report);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportList;
    }

    @Override
    public Report findById(long id){
        String queryString = "SELECT * FROM reports WHERE reports.id=" + id;

        Report report = new Report();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            report = getReport(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    };

    @Override
    public long save(Report report) {
        String queryString = "INSERT INTO reports (violator_id, tournament_id, description, decision) VALUES ('"
                +report.getViolatorId()+"', '"
                +report.getTournamentId()+"', '"
                +report.getDescription()+"', '"
                +report.getDecision()+"') RETURNING id" ;
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
    public void update(long id, Report report) {
        String queryString = "UPDATE reports SET violator_id = '"+report.getViolatorId()
                +"', tournament_id = '"+report.getTournamentId()
                +"', description = '"+report.getDescription()
                +"', decision = '"+report.getDecision()
                +"' WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };

    public void delete(long id) {
        String queryString = "DELETE FROM reports WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };
}

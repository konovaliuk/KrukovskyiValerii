package dao.imlp;

import entities.Tournament;
import dao.interfaces.TournamentDaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDao implements TournamentDaoInterface {
    private Connection connection;
    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";
    private final static String DESCRIPTION_COLUMN = "description";
    private Statement statement;

    public TournamentDao(final Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
    }

    private Tournament getTournament(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);

        return new Tournament(id, name, description);
    }

    @Override
    public List<Tournament> findAll() {
        String queryString = "SELECT * FROM tournaments";
        List<Tournament> tournamentList = new ArrayList<Tournament>();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                Tournament tournament = getTournament(resultSet);
                tournamentList.add(tournament);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tournamentList;
    }

    @Override
    public Tournament findById(long id){
        String queryString = "SELECT * FROM tournaments WHERE tournaments.id=" + id;
        Tournament tournament = new Tournament();

        try {
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            tournament = getTournament(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tournament;
    };

    @Override
    public long save(Tournament tournament) {
        String queryString = "INSERT INTO tournaments (name, description) VALUES ('"
                +tournament.getName()+"', '"
                +tournament.getDescription()
                +"') RETURNING id" ;
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
    public void update(long id, Tournament tournament) {
        String queryString = "UPDATE tournaments SET name = '"+tournament.getName()
                +"', description = '"+tournament.getDescription()+"' WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };

    public void delete(long id) {
        String queryString = "DELETE FROM tournaments WHERE id="+id;

        try {
            statement.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
    };
}

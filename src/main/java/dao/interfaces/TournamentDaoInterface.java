package dao.interfaces;

import entities.Tournament;

import java.util.List;

public interface TournamentDaoInterface {
    List<Tournament> findAll();
    Tournament findById(long id);
    long save(Tournament tournament);
    void update(long id, Tournament tournament);
    void delete(long id);
}

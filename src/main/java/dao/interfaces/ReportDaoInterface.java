package dao.interfaces;

import entities.Report;

import java.util.List;

public interface ReportDaoInterface {
    List<Report> findAll();
    Report findById(long id);
    long save(Report report);
    void update(long id, Report report);
    void delete(long id);
}

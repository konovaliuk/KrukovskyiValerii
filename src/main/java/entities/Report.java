package entities;

import java.util.Objects;

public class Report {
    private long id;
    private long violatorId;
    private long tournamentId;
    private String description;
    private String decision;

    public Report() {
    }

    public Report(long id, long violatorId, long tournamentId, String description, String decision) {
        this.id = id;
        this.violatorId = violatorId;
        this.tournamentId = tournamentId;
        this.description = description;
        this.decision = decision;
    }

    public Report(long violatorId, long tournamentId, String description, String decision) {
        this.violatorId = violatorId;
        this.tournamentId = tournamentId;
        this.description = description;
        this.decision = decision;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getViolatorId() {
        return violatorId;
    }

    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && violatorId == report.violatorId && tournamentId == report.tournamentId && Objects.equals(description, report.description) && Objects.equals(decision, report.decision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, violatorId, tournamentId, description, decision);
    }

    @Override
    public String toString() {
        return Long.toString(id)+"\t"+violatorId+"\t"+tournamentId+"\t"+description+"\t"+decision;
    }
}

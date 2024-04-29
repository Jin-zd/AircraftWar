package edu.hitsz.DAO;

public class Record {
    private final String user;
    private final int score;
    private final String date;

    Record(String user, int score, String date) {
        this.user = user;
        this.score = score;
        this.date = date;
    }

    @Override
    public String toString() {
        return user + "," + score + "," + date;
    }

    public String getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}

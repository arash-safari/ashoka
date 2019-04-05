package com.game.senjad.sgame.results_view;

public class AllResultEntity {
    private String name;
    private int rank;
    private int score;

    public AllResultEntity(String name, int rank, int score) {
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

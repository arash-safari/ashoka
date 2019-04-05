package com.game.senjad.sgame;

public class MyScoreRankEntity {
    private String gameName;
    private int myRank,myScore,bestScore;

    public MyScoreRankEntity(String gameName, int myRank, int myScore, int bestScore) {
        this.gameName = gameName;
        this.myRank = myRank;
        this.myScore = myScore;
        this.bestScore = bestScore;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setMyRank(int myRank) {
        this.myRank = myRank;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public String getGameName() {
        return gameName;
    }

    public int getMyRank() {
        return myRank;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getBestScore() {
        return bestScore;
    }

}

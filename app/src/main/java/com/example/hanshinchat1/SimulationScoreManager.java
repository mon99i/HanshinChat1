package com.example.hanshinchat1;

public class SimulationScoreManager {
    private static int totalScore = 0;

    public static void addToScore(int score) {
        totalScore += score;
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public static void resetScore() {
        totalScore = 0;
    }
}

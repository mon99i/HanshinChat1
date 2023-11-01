package com.example.hanshinchat1;

import java.util.HashMap;
import java.util.Map;

public class SimulationScoreManager {
    private static int totalScore = 0;
    private static Map<Class<?>, Integer> questionScores = new HashMap<>();
    public static void addToScore(int score) {
        totalScore += score;
    }
    public static void subtractFromScore(int score) {
        totalScore -= score;
    }
    public static int getTotalScore() {
        return totalScore;
    }
    public static int getQuestionScore(Class<?> simulationClass) {
        return questionScores.getOrDefault(simulationClass, 0);
    }
    public static void setQuestionScore(Class<?> simulationClass, int score) {
        questionScores.put(simulationClass, score);
    }
    public static int resetScore() {
        return totalScore = 0;
    }
}

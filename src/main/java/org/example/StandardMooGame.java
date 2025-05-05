package org.example;
import java.util.Random;

public class StandardMooGame implements Game {
    private final Random random = new Random();
    private static final String DELIMITER = ",";

    @Override
    public String makeGoal() {
        StringBuilder goal = new StringBuilder();
        while (goal.length() < 4) {
            int digit = random.nextInt(10);
            String digitStr = String.valueOf(digit);
            if (!goal.toString().contains(digitStr)) {
                goal.append(digitStr);
            }
        }
        return goal.toString();
    }

    @Override
    public String generateFeedback(String goal, String guess) {
        if (goal.length() != guess.length()) {
            return "Invalid guess, try again!";
        }
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            char gChar = guess.charAt(i);
            if (gChar == goal.charAt(i)) {
                bulls++;
            } else if (goal.indexOf(gChar) >= 0) {
                cows++;
            }
        }

        return "B".repeat(bulls) + DELIMITER + "C".repeat(cows);
    }
}

package org.example;
import java.util.Random;

public class StandardMooGame implements Game {
    private final Random random = new Random();

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
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (goal.charAt(i) == guess.charAt(j)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }

        return "B".repeat(bulls) + "," + "C".repeat(cows);
    }
}

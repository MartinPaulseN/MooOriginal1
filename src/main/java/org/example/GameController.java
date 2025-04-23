package org.example;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameController {
    private final UserInterface ui;
    private final Database db;
    private final Game game;

    public GameController(UserInterface ui, Database db, Game game) {
        this.ui = ui;
        this.db = db;
        this.game = game;
    }

    public void run() throws SQLException {
        ui.showMessage(GameConstants.USERNAME_PROMPT);
        String name = ui.prompt("");
        int playerId = db.getPlayerIdByName(name);

        if (playerId < 0) {
            ui.showMessage("User not in database-exiting.");
            ui.exit();
            return;
        }

        boolean play = true;
        while (play) {
            String goal = game.makeGoal();
            ui.clear();
            ui.showMessage(GameConstants.NEW_GAME_PROMPT);
            String guess = ui.prompt("");
            int guesses = 1;
            String feedback = game.generateFeedback(goal, guess);
            ui.showMessage(guess + "\n" + feedback + "\n");

            while (!feedback.equals("BBBB,")) {
                guess = ui.prompt("");
                feedback = game.generateFeedback(goal, guess);
                guesses++;
                ui.showMessage(guess + ":\n" + feedback + "\n");
            }

            db.saveResult(playerId, guesses);
            showTopTen();
            play = ui.askYesNo("Correct, it took " + guesses + " guesses\nContinue?");
        }

        ui.exit();
    }

    private void showTopTen() throws SQLException {
        ui.showMessage(GameConstants.TOP_TEN_HEADER);
        AtomicInteger pos = new AtomicInteger(1);
        db.getTopTenPlayers().stream()
                .limit(10)
                .forEach(p -> ui.showMessage(
                        String.format("%3d %-10s%5.2f%n",
                                pos.getAndIncrement(),
                                p.name,
                                p.average)));
    }
}

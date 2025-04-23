package org.example;
import java.sql.SQLException;
import java.util.List;

public class GameController {
    private final UserInterface ui;
    private final Database db;
    private final Game game;

    public GameController(UserInterface ui, Database db, Game game) {
        this.ui = ui;
        this.db = db;
        this.game = game;
    }

    public void run() throws SQLException, InterruptedException {
        ui.showMessage("Enter your user name:\n");
        String name = ui.prompt("");
        int playerId = db.getPlayerIdByName(name);

        if (playerId < 0) {
            ui.showMessage("User not in database, please register with admin");
            Thread.sleep(5000);
            ui.exit();
            return;
        }

        boolean play = true;
        while (play) {
            String goal = game.makeGoal();
            ui.clear();
            ui.showMessage("New game:\n");
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
        List<Database.PlayerAverage> topList = db.getTopTenPlayers();
        ui.showMessage("Top Ten List\n     Player     Average\n");
        int pos = 1;
        for (DatabaseManager.PlayerAverage p : topList) {
            ui.showMessage(String.format("%3d %-10s%5.2f%n", pos++, p.name, p.average));
            if (pos > 10) break;
        }
    }
}

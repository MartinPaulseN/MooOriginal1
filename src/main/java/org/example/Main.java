package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            UserInterface ui = new SimpleWindowUI("Moo");
            Database db = new DatabaseManager(
                    "jdbc:mysql://localhost/Moo", "root", "MartinPaulsen03");
            Game game = new StandardMooGame();

            GameController controller = new GameController(ui, db, game);
            controller.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
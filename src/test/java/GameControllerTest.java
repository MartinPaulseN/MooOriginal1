import org.example.Database;
import org.example.Game;
import org.example.GameController;
import org.example.UserInterface;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    public void testSingleRoundCorrectGuess() throws Exception {
        MockUI ui = new MockUI(List.of("testuser", "1234", "no"));
        MockDB db = new MockDB();
        MockGame game = new MockGame("1234");

        GameController controller = new GameController(ui, db, game);
        controller.run();

        assertTrue(db.savedResults.containsKey("testuser"));
        assertEquals(1, db.savedResults.get("testuser"));
    }

    static class MockUI implements UserInterface {
        Queue<String> input;
        List<String> output = new ArrayList<>();
        public MockUI(List<String> input) { this.input = new LinkedList<>(input); }

        public void showMessage(String message) { output.add(message); }
        public String prompt(String message) { return input.poll(); }
        public boolean askYesNo(String message) { return input.poll().equalsIgnoreCase("yes"); }
        public void clear() {}
        public void exit() {}

    }

    static class MockDB implements Database {
        Map<String, Integer> savedResults = new HashMap<>();
        public int getPlayerIdByName(String name) { return name.equals("testuser") ? 1 : -1; }
        public void saveResult(int playerId, int guesses) { savedResults.put("testuser", guesses); }
        public List<PlayerAverage> getTopTenPlayers() { return List.of(); }

    }

    static class MockGame implements Game {
        String goal;
        public MockGame(String goal) { this.goal = goal; }
        public String makeGoal() { return goal; }
        public String generateFeedback(String goal, String guess) {
            return goal.equals(guess) ? "BBBB," : ",CCCC";
        }
    }
}

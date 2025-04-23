import org.example.StandardMooGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardMooGameTest {

    @Test
    public void testGenerateFeedback_AllBulls() {
        StandardMooGame game = new StandardMooGame();
        String feedback = game.generateFeedback("1234", "1234");
        assertEquals("BBBB,", feedback);
    }

    @Test
    public void testGenerateFeedback_AllCows() {
        StandardMooGame game = new StandardMooGame();
        String feedback = game.generateFeedback("1234", "4321");
        assertEquals(",CCCC", feedback);
    }

    @Test
    public void testGenerateFeedback_Mixed() {
        StandardMooGame game = new StandardMooGame();
        String feedback = game.generateFeedback("1234", "1243");
        assertEquals("BB,CC", feedback);
    }
}

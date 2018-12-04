
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;




public class BoardTest {

    private Board board;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        board = new Board;
    }


    @Test
    public void testConstructor() {
        assertEquals("At first Player's kazan should have 0 balls", 0, getpKazan().return_num());
        assertEquals("At first Opponents's kazan should have 0 balls", 0, getoKazan().return_num());

        int i = 0;
        for(Hole hole : allTheHoles){
            i++;
            assertEquals("Hole " + i + "out of 18 should have 9 balls", 9, hole.getNum());
        }
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown(){
        board = null;
    }

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    setUp();

    @Test
    public void testMoveBallsAtTheBegining() {
        //check if the balls are moved correctly (we don't have any tuz yet)
        //but the balls may be captured to kazan from the last hole
        int index = 3;
        board.moveTheBalls(index);
        assertEquals("Fist hole (Player - 4) should have only one ball", 1, board.getAllTheHoles().get(index).getNum());
        assertEquals("Next hole (Player - 5) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 6) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 7) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 8) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 9) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 1) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 2) should have only 10 balls now", 10, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 3) should have only 0 balls now as they are captured to kazan", 0, board.getAllTheHoles().get(++index).getNum());
        assertEquals("Player's kazan should have 10 balls now", 10, board.getpKazan().return_num());
}

    }



    @Test
    public void testTryMarkAsTuzPlayer(){
        int index1 = 10;
        board.getAllTheHoles().get(index1).changeNum(3);
        tryMarkAsTuz(index1, true);
        assertEquals("The hole should become a player's tuz.", true, board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 11;
        tryMarkAsTuz(index2, true);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, board.getAllTheHoles().get(index2).checkTuz());
    }



    @Test
    public void testTryMarkAsTuzOpponent(){
        int index1 = 2;
        board.getAllTheHoles().get(index1).changeNum(3);
        tryMarkAsTuz(index1, false);
        assertEquals("The hole should become an opponent's tuz.", true, board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 4;
        tryMarkAsTuz(index2, false);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, board.getAllTheHoles().get(index2).checkTuz());
    }



    @Test
    public void testTryCaptureBalls(){
        //can't capture the balls if the number of balls is odd - when constructed every hole has 9 balls
        int index1 = 11;
        tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the opponent but has an odd number of balls.", 9, board.getAllTheHoles().get(index1).getNum());

        int index2 = 2;
        tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the player but has an odd number of balls.", 9, board.getAllTheHoles().get(index2).getNum());

        //can't capture the balls if the number of balls if the hole belongs to the person who made the move (even when the number of balls is evem)
        index1 = 3;
        board.getAllTheHoles().get(index1).changeNum(10);
        tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the player.", 10, board.getAllTheHoles().get(index1).getNum());

        index2 = 12;
        board.getAllTheHoles().get(index2).changeNum(10);
        tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the opponent.", 10, board.getAllTheHoles().get(index2).getNum());

    }


    @Test
    public void testCaptureBallsFromTuzPlayer(){
        captureBallsFromTuz();
        assertEquals("No balls should be captured to Player's kazan as he does not have a tuz yet.", 0, board.getpKazan().return_num());

        int index1 = 10;
        board.getAllTheHoles().get(index1).changeNum(3);
        tryMarkAsTuz(index1, true);

        board.getAllTheHoles().get(index1).changeNum(10);
        captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Player's kazan should now have 10 balls.", 10, board.getpKazan().return_num());
    }

    @Test
    public void testCaptureBallsFromTuzOpponent(){
        captureBallsFromTuz();
        assertEquals("No balls should be captured to Opponent's kazan as he does not have a tuz yet.", 0, board.getoKazan().return_num());

        int index1 = 4;
        board.getAllTheHoles().get(index1).changeNum(3);
        tryMarkAsTuz(index1, false);

        board.getAllTheHoles().get(index1).changeNum(11);
        captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Opponent's kazan should now have 11 balls.", 11, board.getoKazan().return_num());
    }



}

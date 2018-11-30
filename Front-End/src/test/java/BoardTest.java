
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class BoardTest{

    private Board board;

    @Before
    public void setUp(){
        board = new Board;
    }
    @After
    public void tearDown(){
        board = null;
    }
    @Test
    publi void
}


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
    public void setUp(){
        board = new Board;
    }

    @Test
    public void testMoveBalls() {
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
    public void setUp(){
        board = new Board;
    }


    @Test
    public void testTryMarkAsTuz(){
        tryMarkAsTuz(int index, boolean isPlayerTemp)
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
    public void setUp(){
        board = new Board;
    }

    @Test
    public void testTryCaptureBalls(){

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
    public void setUp(){
        board = new Board;
    }

    @Test
    public void testCaptureBallsFromTuz(){

    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown(){
        board = null;
    }



}

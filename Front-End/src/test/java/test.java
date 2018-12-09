

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class test {

    private playTheGame game;
    private Board board;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        game = new playTheGame();
        board = game.getBoard();
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown(){
        game = null;
        board = null;
    }

    @Test
    public void testBoardHole() {
        assertEquals("Index should be 1(getIndex)", 1, board.getAllTheHoles().get(1).getIndex());
        Hole hole = board.getAllTheHoles().get(1);
        hole.incrementBalls();
        assertEquals("Number of balls should be 10(increase)", 10, hole.getNum());
        hole.changeNum(5);
        assertEquals("Number of balls should be 10(changeNum)", 5, hole.getNum());
    }
    @Test
    public void testBoardConstructor() {
        assertEquals("At first Player's kazan should have 0 balls", 0, board.getpKazan().return_num());
        assertEquals("At first Opponents's kazan should have 0 balls", 0, board.getoKazan().return_num());

        int i = 0;
        for(Hole hole : board.getAllTheHoles()){
            i++;
            assertEquals("Hole " + i + "out of 18 should have 9 balls", 9, hole.getNum());
        }
    }

    @Test
    public void testBoardMoveBallsAtTheBegining() {
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

    @Test
    public void testBoardTryMarkAsTuzPlayer(){
        int index1 = 10;
        board.getAllTheHoles().get(index1).changeNum(3);
        board.tryMarkAsTuz(index1, true);
        assertEquals("The hole should become a player's tuz.", true, board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 11;
        board.tryMarkAsTuz(index2, true);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, board.getAllTheHoles().get(index2).checkTuz());
    }



    @Test
    public void testBoardTryMarkAsTuzOpponent(){
        int index1 = 2;
        board.getAllTheHoles().get(index1).changeNum(3);
        board.tryMarkAsTuz(index1, false);
        assertEquals("The hole should become an opponent's tuz.", true, board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 4;
        board.tryMarkAsTuz(index2, false);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, board.getAllTheHoles().get(index2).checkTuz());
    }



    @Test
    public void testBoardTryCaptureBalls(){
        //can't capture the balls if the number of balls is odd - when constructed every hole has 9 balls
        int index1 = 11;
        board.tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the opponent but has an odd number of balls.", 9, board.getAllTheHoles().get(index1).getNum());

        int index2 = 2;
        board.tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the player but has an odd number of balls.", 9, board.getAllTheHoles().get(index2).getNum());

        //can't capture the balls if the number of balls if the hole belongs to the person who made the move (even when the number of balls is evem)
        index1 = 3;
        board.getAllTheHoles().get(index1).changeNum(10);
        board.tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the player.", 10, board.getAllTheHoles().get(index1).getNum());

        index2 = 12;
        board.getAllTheHoles().get(index2).changeNum(10);
        board.tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the opponent.", 10, board.getAllTheHoles().get(index2).getNum());

    }


    @Test
    public void testBoardCaptureBallsFromTuzPlayer(){
        board.captureBallsFromTuz();
        assertEquals("No balls should be captured to Player's kazan as he does not have a tuz yet.", 0, board.getpKazan().return_num());

        int index1 = 10;
        board.getAllTheHoles().get(index1).changeNum(3);
        board.tryMarkAsTuz(index1, true);

        board.getAllTheHoles().get(index1).changeNum(10);
        board.captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Player's kazan should now have 10 balls.", 10, board.getpKazan().return_num());
    }

    @Test
    public void testBoardCaptureBallsFromTuzOpponent(){
        board.captureBallsFromTuz();
        assertEquals("No balls should be captured to Opponent's kazan as he does not have a tuz yet.", 0, board.getoKazan().return_num());

        int index1 = 4;
        board.getAllTheHoles().get(index1).changeNum(3);
        board.tryMarkAsTuz(index1, false);

        board.getAllTheHoles().get(index1).changeNum(11);
        board.captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Opponent's kazan should now have 11 balls.", 11, board.getoKazan().return_num());
    }

    @Test
    public void testGenerateTheBestIndex(){
        for(int i = 0; i <=17; i++){
            if (i == 6){
                board.getAllTheHoles().get(i).changeNum(3);
            }else if (i == 16){
                board.getAllTheHoles().get(i).changeNum(9);
            }else if (i == 17){
                board.getAllTheHoles().get(i).changeNum(10);
            }
            else{
                board.getAllTheHoles().get(i).changeNum(0);
            }
        }

        int bestIndex = game.generateTheBestIndex();
        assertEquals("The generated index should be 16 as the move will end up with capturing the most balls.", 16, bestIndex);
    }



}

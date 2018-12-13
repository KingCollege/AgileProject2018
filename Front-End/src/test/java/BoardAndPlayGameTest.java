

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
public class BoardAndPlayGameTest {
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
      PlayTheGame.board = new Board();
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown(){
      PlayTheGame.board = null;
    }

    /**
     * Tests hole's methodes - incerement and changeNum.
     */
    @Test
    public void testBoardHole() {
        assertEquals("Index should be 1(getIndex)", 1, PlayTheGame.board.getAllTheHoles().get(1).getIndex());
        Hole hole = PlayTheGame.board.getAllTheHoles().get(1);
        hole.incrementBalls();
        assertEquals("Number of balls should be 10(increase)", 10, hole.getNum());
        hole.changeNum(5);
        assertEquals("Number of balls should be 10(changeNum)", 5, hole.getNum());
    }

    /**
     * Tests Board's constructor.
     * The kazan's should be set to 0.
     * Every hole should have 9 balls.
     */
    @Test
    public void testBoardConstructor() {
        assertEquals("At first Player's kazan should have 0 balls", 0, PlayTheGame.board.getpKazan().return_num());
        assertEquals("At first Opponents's kazan should have 0 balls", 0, PlayTheGame.board.getoKazan().return_num());

        int i = 0;
        for(Hole hole : PlayTheGame.board.getAllTheHoles()){
            i++;
            assertEquals("Hole " + i + "out of 18 should have 9 balls", 9, hole.getNum());
        }
    }

    /**
     * Tests Board's method to move the balls constructor.
     * The balls from one hole are moved to appropriate holes.
     * If the last one ends up in Opponent's hole and there is an even number of balls - it should be captured.
     */
    @Test
    public void testBoardMoveBallsAtTheBegining() {
        //check if the balls are moved correctly (we don't have any tuz yet)
        //but the balls may be captured to kazan from the last hole
        int index = 3;
        PlayTheGame.board.moveTheBalls(index);
        assertEquals("First hole (Player - 4) should have only one ball", 1, PlayTheGame.board.getAllTheHoles().get(index).getNum());
        assertEquals("Next hole (Player - 5) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 6) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 7) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 8) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Player - 9) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 1) should have only 1 balls because the computer will choose this as the best hole to move", 1, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 2) should have only 10 balls now", 11, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 3) should have only 1 ball now as they are captured to kazan but computer also makes a move on hole 1"
          , 1, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Player's kazan should have 10 balls now", 10, PlayTheGame.board.getpKazan().return_num());
    }

    /**
     * Tests Board's tryMarkAsTuz() method.
     * Can the Player mark this hole as tuz?
     */
    @Test
    public void testBoardTryMarkAsTuzPlayer(){
        int index1 = 10;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, true);
        assertEquals("The hole should become a player's tuz.", true, PlayTheGame.board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 11;
        PlayTheGame.board.tryMarkAsTuz(index2, true);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }


    /**
     * Tests Board's tryMarkAsTuz() method.
     * Can the Opponent mark this hole as tuz?
     */
    @Test
    public void testBoardTryMarkAsTuzOpponent(){
        int index1 = 2;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, false);
        assertEquals("The hole should become an opponent's tuz.", true, PlayTheGame.board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 4;
        PlayTheGame.board.tryMarkAsTuz(index2, false);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", true, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }

    /**
     * Tests Board's tryMarkAsTuz() method.
     * Opponent and Player cannot mark mirroring holes as tuz!
     */
    @Test
    public void testBoardTryMarkAsTuzMirrorIndex(){
        int index1 = 10;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, true);
        assertEquals("The hole should become a player's tuz.", true, PlayTheGame.board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 1;
        PlayTheGame.PlayTheGame.board.tryMarkAsTuz(index2, false);
        assertEquals("The opponent should not be able to mark this hole as tuz because the player has a tuz with the same index on the other side.", false, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }

    /**
     * Tests Board's tryMarkAsTuz() method.
     * If Player has a tuz, he/she cannot get another one.
     */
    @Test
    public void testBoardTryMarkAsTuzAgain(){
        int index1 = 10;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, true);

        int index2 = 15;
        PlayTheGame.board.getAllTheHoles().get(index2).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index2, true);

        assertEquals("The Player should no be able to capture this az tuz because he already has one.", false, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }

    /**
     * Tests Board's tryCaptureBalls() method.
     * A Player can capture balls is his last ball ends up in Opponent's hole
     * with even number of balls or his tuz.
     */
    @Test
    public void testBoardTryCaptureBalls(){
        //can't capture the balls if the number of balls is odd - when constructed every hole has 9 balls
        int index1 = 11;
        PlayTheGame.board.tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the opponent but has an odd number of balls.", 9, PlayTheGame.board.getAllTheHoles().get(index1).getNum());

        int index2 = 2;
        PlayTheGame.board.tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the player but has an odd number of balls.", 9, PlayTheGame.board.getAllTheHoles().get(index2).getNum());

        //can't capture the balls if the number of balls if the hole belongs to the person who made the move (even when the number of balls is evem)
        index1 = 3;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(10);
        PlayTheGame.board.tryCaptureBalls(index1, true);
        assertEquals("The player shouldn't capture the balls - the hole belongs to the player.", 10, PlayTheGame.board.getAllTheHoles().get(index1).getNum());

        index2 = 12;
        PlayTheGame.board.getAllTheHoles().get(index2).changeNum(10);
        PlayTheGame.board.tryCaptureBalls(index2, false);
        assertEquals("The opponent shouldn't capture the balls - the hole belongs to the opponent.", 10, PlayTheGame.board.getAllTheHoles().get(index2).getNum());

    }

    /**
     * Tests Board's captureBallsFromTuz() method.
     * If during anyone's move balls end up in tuz they should be captured to appropriate kazans.
     */
    @Test
    public void testBoardCaptureBallsFromTuzPlayer(){
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("No balls should be captured to Player's kazan as he does not have a tuz yet.", 0, PlayTheGame.board.getpKazan().return_num());

        int index1 = 10;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, true);

        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(10);
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, PlayTheGame.board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Player's kazan should now have 13 balls.", 13, PlayTheGame.board.getpKazan().return_num());
    }

    /**
     * Tests Board's captureBallsFromTuz() method.
     * If during anyone's move balls end up in tuz they should be captured to appropriate kazans.
     */
    @Test
    public void testBoardCaptureBallsFromTuzOpponent(){
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("No balls should be captured to Opponent's kazan as he does not have a tuz yet.", 0, PlayTheGame.board.getoKazan().return_num());

        int index1 = 4;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, false);

        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(11);
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, PlayTheGame.board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Opponent's kazan should now have 14 balls.", 14, PlayTheGame.board.getoKazan().return_num());
    }

    /**
     * Tests playTheGames's generateTheBestIndex() method.
     * Instead of picking a random index, Computed chooses the one that makes the most favourable move.
     * As a result - the biggest number of balls will be captured.
     */
    @Test
    public void testGenerateTheBestIndex(){
        for(int i = 0; i <=17; i++){
            if (i == 6){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(3);
            }else if (i == 16){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(9);
            }else if (i == 17){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(10);
            }
            else{
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(0);
            }
        }

        int bestIndex = game.generateTheBestIndex();
        assertEquals("The generated index should be 16 as the move will end up with capturing the most balls.", 16, bestIndex);
    }

    /**
     * Tests playTheGames's generateTheBestIndex() method.
     * Instead of picking a random index, Computed chooses the one that makes the most favourable move.
     * As a result - the biggest number of balls will be captured.
     * If the Computer doesn't have a tuz yet though, then the index will be picked that will
     * result in capturing a tuz.
     */
    @Test
    public void testGenerateTheBestIndexTuz(){
        for(int i = 0; i <=17; i++){
            if (i == 2){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(2);
            }else if (i == 3){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(3);
            }else if (i == 14){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(7);
            }else if (i == 15){
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(7);
            }
            else{
                PlayTheGame.board.getAllTheHoles().get(i).changeNum(1);
            }
        }

        int bestIndex = game.generateTheBestIndex();
        assertEquals("The generated index should be 14 as then move will end up with capturing a tuz.", 14, bestIndex);
    }

}

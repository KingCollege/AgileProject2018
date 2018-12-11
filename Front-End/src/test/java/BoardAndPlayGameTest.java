import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/***
 * The class that tests functionalities in PlayTheGame class and Board class.
 * @author Mandu Shi, Tao Lin, Marta Krawczyk and Adam Able
 * @version    2018.11.28
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

    @Test
    public void testBoardHole() {
        assertEquals("Index should be 1(getIndex)", 1, PlayTheGame.board.getAllTheHoles().get(1).getIndex());
        Hole hole = PlayTheGame.board.getAllTheHoles().get(1);
        hole.incrementBalls();
        assertEquals("Number of balls should be 10(increase)", 10, hole.getNum());
        hole.changeNum(5);
        assertEquals("Number of balls should be 10(changeNum)", 5, hole.getNum());
    }

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

    @Test
    public void testBoardMoveBallsAtTheBeginning() {
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
        assertEquals("Next hole (Opponent - 1) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 2) should have only 10 balls now", 10, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Next hole (Opponent - 3) should have only 0 balls now as they are captured to kazan", 0, PlayTheGame.board.getAllTheHoles().get(++index).getNum());
        assertEquals("Player's kazan should have 10 balls now", 10, PlayTheGame.board.getpKazan().return_num());
    }

    @Test
    public void testBoardTryMarkAsTuzPlayer(){
        int index1 = 10;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, true);
        assertEquals("The hole should become a player's tuz.", true, PlayTheGame.board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 11;
        PlayTheGame.board.tryMarkAsTuz(index2, true);
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", false, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }



    @Test
    public void testBoardTryMarkAsTuzOpponent(){
        int index1 = 2;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        PlayTheGame.board.tryMarkAsTuz(index1, false);
        assertEquals("The hole should become an opponent's tuz.", true, PlayTheGame.board.getAllTheHoles().get(index1).checkTuz());

        int index2 = 4;
        PlayTheGame.board.tryMarkAsTuz(index2, false);
        //You marked here as true, but checkTuz returns false when its not a tuz
        assertEquals("The hole shouldn't become a tuz - there are 9 balls (not 3).", false, PlayTheGame.board.getAllTheHoles().get(index2).checkTuz());
    }



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

    @Test
    public void testBoardCaptureBallsFromTuzOpponent(){
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("No balls should be captured to Opponent's kazan as he does not have a tuz yet.", 0, PlayTheGame.board.getoKazan().return_num());

        int index1 = 4;
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(3);
        //When tryMarkAsTuz is called, the 3 initial balls are captured
        PlayTheGame.board.tryMarkAsTuz(index1, false);
        //Therefore when we change the same hole to 11 and capture, the overall value should be 14
        PlayTheGame.board.getAllTheHoles().get(index1).changeNum(11);
        PlayTheGame.board.captureBallsFromTuz();
        assertEquals("The hole that is a tuz should have 0 balls.", 0, PlayTheGame.board.getAllTheHoles().get(index1).getNum());
        assertEquals("The Opponent's kazan should now have 14 balls.", 14, PlayTheGame.board.getoKazan().return_num());
    }

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

        int bestIndex = PlayTheGame.generateTheBestIndex();
        assertEquals("The generated index should be 16 as the move will end up with capturing the most balls.", 16, bestIndex);
    }



}

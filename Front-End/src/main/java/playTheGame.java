import java.util.Random;
/**
 * Write a description of class playTheGame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class playTheGame
{
    public static Board board = new Board();
    private static boolean pTurn = true;

    public static boolean getPlayerTurn(){
        return pTurn;
    }

    public static void setPlayerTurn(boolean turn){
        pTurn = turn;
    }

    public static String computerPlay(){
        Random r = new Random();
        //computer holes start after 9
        int chooseHole = r.nextInt(9) + 9;
        while(!pTurn){
            if(!board.moveTheBalls(chooseHole)){
                chooseHole = r.nextInt(9) + 9;
                continue;
            }
            if(pTurn)
                return "Log: " + (chooseHole - 8 );
        }
        return "Went wrong!";
    }

    public static void main(String[] args) {
        new ToguzKorgoolGUI();
    }
}

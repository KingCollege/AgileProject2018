import java.util.Random;
import java.util.*;
/**
 * The class the links front-end gui and back-end functionalities together
 * @author Mandu Shi, Tao Lin, Marta Krawczyk and Adam Able
 * @version    2018.11.28
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

    public Board getBoard() { return board; }
    
    public  static String computerPlay(){
        int bestIndex = generateTheBestIndex();
        Random r = new Random();
        int chooseHole;
        if (bestIndex == 0){
            chooseHole = r.nextInt(9) + 9;
        }else{
            chooseHole = bestIndex;
        }
        //computer holes start after 9

        while(!pTurn){
            if(!board.moveTheBalls(chooseHole)){
                chooseHole = r.nextInt(9) + 9;
                continue;
            }
            if(pTurn)
                return "Log: " + (chooseHole - 8);
        }
        return "Went wrong!";
    }

    public static void main(String[] args) {
        new ToguzKorgoolGUI();
    }

    //enhancing Computer's decision making
    public static int generateTheBestIndex(){
        List<Hole> listOfHoles = board.getAllTheHoles();
        List<Integer> possibleMoves = new ArrayList<>();

        for(int i = 9; i <= 17; i++){
            Hole hole = listOfHoles.get(i);
            //the last ball has to end up in Player's hole
            int relativeNumberOfBalls = hole.getNum()%19 + 1;
            if((19-i>=relativeNumberOfBalls) && (relativeNumberOfBalls>10-i)){
                int targetIndex = i+hole.getNum();
                targetIndex = targetIndex%18;
                //if the computer doesn't have the tuz yet it could be a hole with 3 balls so that its can be marked az tuz
                if(!board.getoHasTuz() && listOfHoles.get(targetIndex).getNum()+1 == 3){
                    //with that move the computer will gain a tuz
                    return i;
                }
                //the hole cannot be tuz and it should have event number of balls at the end
                else if((listOfHoles.get(targetIndex).getNum()+1)%2 == 0 || !listOfHoles.get(targetIndex).checkTuz()){
                    possibleMoves.add(i);
                }
            }
        }
        //we'll pick the hole with the biggest number of balls to capture
        //we have to make sure the array list is not empty first
        if(!possibleMoves.isEmpty()){
            int indexToPick;
            int maxBalls = 0;
            for(Integer index : possibleMoves){
                Hole hole = listOfHoles.get(index);
                //the last ball has to end up in Player's hole
                //what if there is so many balls that the target hole will have extra 2 or 3 balls?
                int toIncrement =  hole.getNum()/18 + 1;
                int targetIndex = index + hole.getNum();
                targetIndex = targetIndex%18;

                if((listOfHoles.get(targetIndex).getNum()+toIncrement) > maxBalls){
                    indexToPick = (int) index;
                    maxBalls = listOfHoles.get(targetIndex).getNum()+toIncrement;
                }
            }
            return indexToPick;
        }else{
            return 0;
        }

    }
}

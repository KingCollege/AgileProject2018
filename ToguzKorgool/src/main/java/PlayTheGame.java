/**
 * The class the links front-end gui and back-end functionalities together
 * @author Mandu Shi, Tao Lin, Marta Krawczyk and Adam Able
 * @version    2018.11.28
 */
import java.util.Random;
import java.util.*;
public class PlayTheGame
{
    public static Board board = new Board();
    private static boolean pTurn = true;

    public static boolean getPlayerTurn(){
        return pTurn;
    }

    public static void setPlayerTurn(boolean turn){
        pTurn = turn;
    }

    public  static String computerPlay(){
        int bestIndex = generateTheBestIndex();
        Random r = new Random();
        int chooseHole;
        if (bestIndex == 0){
            chooseHole = r.nextInt(9) + 9;
        }else{
            chooseHole = bestIndex;
        }

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
        new StartPageGUI();
    }

    /**
    The computer will look for a possible tuz capture, then it looks for all successful
    captures and puts then in a list, compare and find the largest capture.
    */
    public static int generateTheBestIndex(){
        List<Hole> listOfHoles = board.getAllTheHoles();
        List<Integer> possibleMoves = new ArrayList<>();

        for(int i = 9; i <= 17; i++){
            Hole hole = listOfHoles.get(i);
            //the last ball has to end up in Player's hole
            if(hole.getNum() == 0)
              continue;
            int finalKorgoolPosition = (hole.getNum()+i -1)%18;
            if(finalKorgoolPosition <= 8){
                //if the computer doesn't have the tuz yet it could be a hole with 3 balls so that it can be marked az tuz
                if(!board.getoHasTuz() && listOfHoles.get(finalKorgoolPosition+1).getNum() == 3){
                    //with that move the computer will gain a tuz
                    return i;
                }
                //the hole cannot be tuz and it should have even number of balls at the end
                else if((listOfHoles.get(finalKorgoolPosition+1).getNum())%2 == 0 || !listOfHoles.get(finalKorgoolPosition+1).checkTuz()){
                    possibleMoves.add(i);
                }
            }
        }

        if(!possibleMoves.isEmpty()){
            int indexToPick =0;
            int maxBalls = 0;
            for(Integer index : possibleMoves){
                Hole hole = listOfHoles.get(index);
                int i = (int)index;
                int finalKorgoolPosition = (hole.getNum()+i -1)%18;
                int potentialCaptures = listOfHoles.get(finalKorgoolPosition).getNum();
                if((hole.getNum()+potentialCaptures) > maxBalls){
                    indexToPick = (int) index;
                    maxBalls = hole.getNum()+potentialCaptures;
                }
            }
            return indexToPick;
        }else{
            return 0;
        }

    }
}

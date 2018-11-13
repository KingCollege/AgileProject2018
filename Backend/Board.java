package Backend;

import java.util.*;

public class Board {
    List<Hole> player = new ArrayList<Hole>();
    kazan Pkazan;
    List<Hole> opponent = new ArrayList<Hole>();
    kazan Okazan;
    List<Hole> allTheHoles = new ArrayList<Hole>();
    public Board(){
        for (int i=0;i<9;i++){
            //init the board,contains holes and kazans for both sides.
            //mark that the index starts from 0.
            Hole initP = new Hole(9,true,i);
            Hole initO = new Hole(9,false,i);
            player.add(initP);
            opponent.add(initO);
        }
        Pkazan = new kazan(0,true);
        Okazan = new kazan(0,false);
        allTheHoles.addAll(player);
        allTheHoles.addAll(opponent);
    }


    public void moveTheBalls(Hole hole){
        int numberOfBalls = hole.getNum();
        boolean isPlayer = hole.getSide();
        int allTheHolesSize = allTheHoles.size();
        int indexOfHole;
        if (isPlayer){
            indexOfHole = hole.getIndex();
        }else{
            indexOfHole = hole.getIndex() + 8;
        }

        //the same hole is cleared and only 1 ball is left there
        hole.changeNum(1);
        numberOfBalls--;

        //we have less balls now and we start putting them to the following holes
        for(int i = 1; i <= numberOfBalls; i++) {
            indexOfHole++;
            if(indexOfHole%allTheHolesSize == 0){
                indexOfHole = 0;
            }
            allTheHoles.get(indexOfHole).incrementBalls();
        }

        //was the last ball put in a hole belonging to the opponent?
        int ballsInLastHole = allTheHoles.get(indexOfHole).getNum();
        if (allTheHoles.get(indexOfHole).getSide() != isPlayer){
            if(ballsInLastHole%2 == 0){
                //the hole belongs to opponent & is even - all the balls are captured
                allTheHoles.get(indexOfHole).changeNum(0);
                //to which kazan they should be placed?
                if(isPlayer){
                    Pkazan.add_balls(ballsInLastHole);
                }else{
                    Okazan.add_balls(ballsInLastHole);
                }
            }
            //if there were 3 balls in the hole & the index of the hole is not 9 (actually 8) - it becomes a kazan
            if(ballsInLastHole == 3 && allTheHoles.get(indexOfHole).getIndex() != 8){
                allTheHoles.get(indexOfHole).markAsTuz();
            }
        }




    }
    public void playTheGame(){

    }
}

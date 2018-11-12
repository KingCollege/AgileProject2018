package Backend;

import java.util.*;

public class Board {
    List<Hole> player = new ArrayList<Hole>();
    kazan Pkazan;
    List<Hole> opponent = new ArrayList<Hole>();
    kazan Okazan;
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
    }
    public void moveTheBall(){

    }
    public void playTheGame(){

    }
}

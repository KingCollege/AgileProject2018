package Backend;
import java.util.*;

public class Board {


    private List<Hole> player = new ArrayList<Hole>();
    private kazan pKazan;
    private List<Hole> opponent = new ArrayList<Hole>();
    private kazan oKazan;
    private List<Hole> allTheHoles = new ArrayList<Hole>();
    private boolean pHasTuz;
    private boolean oHasTuz;
    private int pTuzIndex;
    private int oTuzIndex;

    public Board(){
        for (int i=0;i<9;i++){
            //init the board,contains holes and kazans for both sides.
            //mark that the index starts from 0.
            Hole initP = new Hole(9,true,i);
            Hole initO = new Hole(9,false,i);
            player.add(initP);
            opponent.add(initO);
        }
        pKazan = new kazan(0,true);
        oKazan = new kazan(0,false);
        allTheHoles.addAll(player);
        allTheHoles.addAll(opponent);
        pHasTuz = false;
        oHasTuz = false;
    }


    public void moveTheBalls(int index){
        int numberOfBalls = allTheHoles.get(index).getNum();
        boolean isPlayer = allTheHoles.get(index).getSide();
        int allTheHolesSize = allTheHoles.size();
        int indexOfHole = index;


        //the same hole is cleared and only 1 ball is left there
        allTheHoles.get(index).changeNum(1);
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
            //the hole belongs to opponent & is even - all the balls are captured (BUT balls can't be taken from player's tuz)
            if(ballsInLastHole%2 == 0 && !allTheHoles.get(indexOfHole).checkTuz()){
                allTheHoles.get(indexOfHole).changeNum(0);
                //to which kazan they should be placed?
                if(isPlayer){
                    pKazan.add_balls(ballsInLastHole);
                }else{
                    oKazan.add_balls(ballsInLastHole);
                }
            }

            //TUZ part:
            //we can only mark it as a tuz is a player doesn't have one yet
            boolean tryMarkAsTuz = false;
            if (isPlayer && !pHasTuz){
                //& the opponent's tuz index is different than indexOfHole (if he has one)
                if (oHasTuz){
                    if(oTuzIndex != allTheHoles.get(indexOfHole).getIndex()){
                        tryMarkAsTuz = true;
                    }
                }else{
                    tryMarkAsTuz = true;
                }
            }
            if (!isPlayer && !oHasTuz){
                //& the opponent's tuz index is different than indexOfHole (if he has one)
                if (pHasTuz){
                    if(pTuzIndex != allTheHoles.get(indexOfHole).getIndex()){
                        tryMarkAsTuz = true;
                    }
                }else{
                    tryMarkAsTuz = true;
                }
            }
            //if there were 3 balls in the hole & the index of the hole is not 9 (actually 8) - it becomes a tuz
            if(ballsInLastHole == 3 && allTheHoles.get(indexOfHole).getIndex() != 8 && tryMarkAsTuz){
                    allTheHoles.get(indexOfHole).markAsTuz(); //this hole is yours now
                    //all the balls are transfered to the players kazan
                    if(isPlayer){
                        pKazan.add_balls(3);
                        pHasTuz = true;
                        pTuzIndex = allTheHoles.get(indexOfHole).getIndex();

                    }else{
                        oKazan.add_balls(3);
                        oHasTuz = true;
                        oTuzIndex = allTheHoles.get(indexOfHole).getIndex();
                    }
            }
        }

    }
    public List<Hole> getPlayer() {
        return player;
    }

    public void setPlayer(List<Hole> player) {
        this.player = player;
    }

    public kazan getpKazan() {
        return pKazan;
    }

    public void setpKazan(kazan pKazan) {
        this.pKazan = pKazan;
    }

    public List<Hole> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<Hole> opponent) {
        this.opponent = opponent;
    }

    public kazan getoKazan() {
        return oKazan;
    }

    public void setoKazan(kazan oKazan) {
        this.oKazan = oKazan;
    }

    public List<Hole> getAllTheHoles() {
        return allTheHoles;
    }

    public void setAllTheHoles(List<Hole> allTheHoles) {
        this.allTheHoles = allTheHoles;
    }
}

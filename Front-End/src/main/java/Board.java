
import java.util.*;
public class Board {
    private List<Hole> player = new ArrayList<Hole>();
    private Kazan pKazan;
    private List<Hole> opponent = new ArrayList<Hole>();
    private Kazan oKazan;
    private List<Hole> allTheHoles = new ArrayList<Hole>();
    private boolean pHasTuz;
    private boolean oHasTuz;
    private int pTuzIndex;
    private int oTuzIndex;
    private String log;

    public Board(){
        for (int i=0;i<9;i++){
            //init the board,contains holes and Kazans for both sides.
            //mark that the index starts from 0.
            Hole initP = new Hole(9,true,i);
            Hole initO = new Hole(9,false,i);
            player.add(initP);
            opponent.add(initO);
        }
        pKazan = new Kazan(0,true);
        oKazan = new Kazan(0,false);
        allTheHoles.addAll(player);
        allTheHoles.addAll(opponent);
    }

    public Board(List<Hole> pHoles, List<Hole> oHoles, Kazan p, Kazan o){
      player = pHoles;
      opponent = oHoles;
      pKazan = p;
      oKazan = o;
      allTheHoles.addAll(player);
      allTheHoles.addAll(opponent);
    }

    public int getBallsFromHole(int index) {
        return allTheHoles.get(index).getNum();
    }

    public boolean moveTheBalls(int index){
        int numberOfBalls = allTheHoles.get(index).getNum();
        boolean isPlayer = allTheHoles.get(index).getSide();
        int allTheHolesSize = allTheHoles.size();
        int indexOfHole = index;

        //No balls
        if(allTheHoles.get(index).checkTuz() || allTheHoles.get(index).getNum() < 1)
            return false;
        //the same hole is cleared and only 1 ball is left there
        //if the hole has one korgool, it is moved to the next hole leaving the hole emptied
        if( allTheHoles.get(index).getNum() == 1)
            allTheHoles.get(index).changeNum(0);
        else{
            allTheHoles.get(index).changeNum(1);
            numberOfBalls--;
        }

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
                //to which Kazan they should be placed?
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
                    allTheHoles.get(indexOfHole).markAsTuz();
                    //all the balls are transfered to the opposite side's Kazan
                    if(isPlayer){
                        pKazan.add_balls(3);
                        pHasTuz = true;
                        pTuzIndex = allTheHoles.get(indexOfHole).getIndex();
                        System.out.println("Player Tuz Index " + pTuzIndex);
                        allTheHoles.get(indexOfHole).changeNum(0);

                    }else{
                        oKazan.add_balls(3);
                        oHasTuz = true;
                        oTuzIndex = allTheHoles.get(indexOfHole).getIndex();
                        System.out.println("Computer Tuz Index " + oTuzIndex);
                        allTheHoles.get(indexOfHole).changeNum(0);
                    }
            }
        }

// legalmoves.(i => counttours(dim,  i::path))
        //capture all the balls from tuz to correct Kazan's
        if (pHasTuz){
            if (allTheHoles.get(pTuzIndex).getNum()>0){
                int balls = allTheHoles.get(pTuzIndex).getNum();
                allTheHoles.get(pTuzIndex).changeNum(0);
                pKazan.add_balls(balls);
            }
        }

        if (oHasTuz){
            if (allTheHoles.get(oTuzIndex).getNum()>0){
                int balls = allTheHoles.get(oTuzIndex).getNum();
                allTheHoles.get(oTuzIndex).changeNum(0);
                oKazan.add_balls(balls);
            }
        }

        //need a check to switch turn with computer
        if(playTheGame.getPlayerTurn()){
            playTheGame.setPlayerTurn(false);
            log = playTheGame.computerPlay();
        }
        else{
             playTheGame.setPlayerTurn(true);
        }
        return true;
    }

    // 1 for player, -1 for opponent, 0 for draw, 2 for no wins
    public int checkwin(){
        int draw = ( (pKazan.return_num() == 81 || oKazan.return_num() == 81) &&
              (oKazan.return_num() == pKazan.return_num()) )? 0 : -1;
        if(draw < 0 ){
            if(oKazan.check_win())
              return -1;
            if(pKazan.check_win())
              return 1;
            return -2;
        }
        else
          return 0;
    }

    public String getLog(){
        return log;
    }

    public List<Hole> getPlayer() {
        return player;
    }

    public void setPlayer(List<Hole> player) {
        this.player = player;
    }

    public Kazan getpKazan() {
        return pKazan;
    }

    public void setpKazan(Kazan pKazan) {
        this.pKazan = pKazan;
    }

    public List<Hole> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<Hole> opponent) {
        this.opponent = opponent;
    }

    public Kazan getoKazan() {
        return oKazan;
    }

    public void setoKazan(Kazan oKazan) {
        this.oKazan = oKazan;
    }

    public List<Hole> getAllTheHoles() {
        return allTheHoles;
    }

    public void setAllTheHoles(List<Hole> allTheHoles) {
        this.allTheHoles = allTheHoles;
    }
}

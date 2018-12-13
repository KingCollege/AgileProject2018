
import java.util.*;


/**
 * A class that creates a Board and enables the 2 players to
 * keep playing until one of them wins.
 *
 * @author Marta Krawczyk, Tao Lin, Mandu Shi and Adam Able
 * @version    2018.11.28
 */
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
    private String log;

    /**
     * Create a Board containing 2 sets of 9 holes and 2 kazans.
     */
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
    }

    /**
     * The player picks a hole and all the balls from that hole are moved.
     * @param index The index of the hole.
     * @return boolean
     */
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

        //try capturing the balls & try marking as tuz
        tryCaptureBalls(indexOfHole, isPlayer);


        //capture all the balls from tuz to correct kazan's
        captureBallsFromTuz();

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

    /**
     * Capture all the balls from tuz to correct kazan's.
     */
    public void captureBallsFromTuz(){
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
    }

    /**
     * Check where the last ball was placed and whether the balls can be captured from that hole.
     * Also check if this hole can be marked as tuz.
     * @param index The index of the hole.
     * @param isPlayerTemp Whose move is this one
     */
    public void tryCaptureBalls(int index, boolean isPlayerTemp){
        int indexOfHole = index;
        boolean isPlayer = isPlayerTemp;

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
            tryMarkAsTuz(indexOfHole, isPlayer);
//
        }

    }

    /**
     * Check is that hole can be marked as tuz by the current gamer - Player or Computer.
     * If they don't have a tuz yet and opponen't tuz does not have the same relative index
     * PLUS if there are currently 3 balls in the hole and it is not the hole no 9
     * THEN that hole can be marked as tuz. (of course it has to be on the opponent's side
     * but that is checked when the method is called in tryCaptureBalls().
     * @param index The index of the hole.
     * @param isPlayerTemp Whose move is this one
     */
    public void tryMarkAsTuz(int index, boolean isPlayerTemp){
        int indexOfHole = index;
        boolean isPlayer = isPlayerTemp;
        int ballsInLastHole = allTheHoles.get(indexOfHole).getNum();

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
            //this hole is yours now
            allTheHoles.get(indexOfHole).markAsTuz();
            //all the balls are transfered to the players kazan
            if(isPlayer){
                pKazan.add_balls(3);
                pHasTuz = true;
                pTuzIndex = allTheHoles.get(indexOfHole).getIndex();
                allTheHoles.get(indexOfHole).changeNum(0);

            }else{
                oKazan.add_balls(3);
                oHasTuz = true;
                oTuzIndex = allTheHoles.get(indexOfHole).getIndex();
                allTheHoles.get(indexOfHole).changeNum(0);
            }
        }

    }

    /**
     * Get the number of balls in the hole.
     * @param index The index of the hole.
     * @return The number of balls
     */
    public int getBallsFromHole(int index) {
        return allTheHoles.get(index).getNum();
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

    public boolean getoHasTuz(){
        return oHasTuz;
    }
}
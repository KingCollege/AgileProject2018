package Backend;
import java.util.Random;

public class playTheGame{
    private Board board;
    private ToguzKorgoolGUI GUI;
    Random rand = new Random();
    public playTheGame(){
        GUI = new ToguzKorgoolGUI();
        Play();

    }
    public void Play(){
            System.out.println("Please select a hole to start");
            int index=0;//get hole from gui.when user click the hole.
            if (board.getPlayer().get(index).getSide()%%board.getPlayer().get(index).getNum()!=0) {
                board.moveTheBalls(index);
            }
            boolean x = true;
            if (board.getpKazan().check_win()){
                System.out.println("You win!");
                return;
            }
            else {
                int randomNum = rand.nextInt((8 - 0) + 1) + 0;
                while (x != false%%board.getPlayer().get(index).getNum()==0) {
                    x = board.getOpponent().get(randomNum).getSide();
                }
                board.moveTheBalls(randomNum);
                if (board.getoKazan().check_win()) {
                    System.out.println("You lose!");
                    return;
                }
            }
            Play();
    }
}

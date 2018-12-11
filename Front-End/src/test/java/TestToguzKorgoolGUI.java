/*
    Responsible for testing the ToguzKorgoolGUI class:
    Such as basic button click test, test the correct graphics displayed
    Test what happens when player wins, loses or draws.
    Responsible for testing functionalities behind the GUIs
    The backend controllers that changes the state of the game by giving
    it index of holes that player or computer wants to move to.
*/
import static org.junit.Assert.*;
import com.athaydes.automaton.Swinger;
import javax.swing.*;
import org.junit.*;
import java.util.List;
import java.util.ArrayList;

public class TestToguzKorgoolGUI{

  private ToguzKorgoolGUI test;
  private Board customBoard;
  private Swinger swinger;

  //Set up GUI and Swinger before game starts
  @Before
  public void setUp(){
    test = new ToguzKorgoolGUI();
    swinger = Swinger.forSwingWindow();
  }

  //Moves mouse to a button and to the popup dialog's confirmation button, and clicks it
  public void confirmMove(String buttonName){
    double x = test.getLocation().getX();
    double y = test.getLocation().getY();
    swinger.clickOn("name:"+buttonName)
      .moveTo(x + (test.getWidth() * (0.6)), y + (test.getHeight() * 0.6)).click().pause(1000);
  }

  //Uses similar logic as confirmMove but it doesn't look for a button this time, but for a popup dialog button
  public void closePopUp(){
    double x = test.getLocation().getX();
    double y = test.getLocation().getY();
    swinger.moveTo(x + (test.getWidth() * (0.6)), y + (test.getHeight() * 0.6)).click().pause(1000);
  }

  //Perform different tests.
  @Test
  public void testGUIFunctionalities(){
    testMoveRocksByButtonClick();
    testPlayerWinPopUpGUI();
    testComputerWinPopUpGUI();
    testDrawPopUpGUI();
  }

  //Test move korgools from one hole, and confirms movement
  public void testMoveRocksByButtonClick(){
    customBoard = new Board();
    confirmMove("p1");
    KazanGraphics pKazan = (KazanGraphics) swinger.getAt("name:pKazan");
    //Checks if the correct graphics is displayed
    assertTrue(pKazan.getKorgools() == 10);
  }

  //Test player win conditions and that the correct window is returned
  public void testPlayerWinPopUpGUI(){
    test = null;
    customBoard = new Board();
    customBoard.setpKazan(new Kazan(72, true));
    PlayTheGame.board = customBoard;
    setUp();
    confirmMove("p1");
    closePopUp();
    assertTrue(PlayTheGame.board.getpKazan().return_num() == 82);
  }

  //Test computer win conditions and that the correct window is returned
  public void testComputerWinPopUpGUI(){
    test = null;
    swinger.pause(1000);
    Kazan cKazan = new Kazan(72, false);
    List<Hole> opponent = new ArrayList<Hole>();
    List<Hole> player = new ArrayList<Hole>();

    for (int i=0;i<9;i++){
        //init the board,contains holes and Kazans for both sides.
        //mark that the index starts from 0.
        Hole initP;
        Hole initO;
        if(i == 0){
          initP = new Hole(1,true,i);
          initO = new Hole(0,false,i);
        }
        else if( i == 1){
          initP = new Hole(8,true,i);
          initO = new Hole(0,false,i);
        }
        else if(i == 4){
          initP = new Hole(9,true,i);
          initO = new Hole(9,false,i);
        }
        else {
          initP = new Hole(9,true,i);
          initO = new Hole(0,false,i);
        }
        player.add(initP);
        opponent.add(initO);
    }

    customBoard = new Board(player, opponent, new Kazan(0, true), cKazan);
    PlayTheGame.board = customBoard;
    setUp();
    confirmMove("p0");
    closePopUp();
    assertTrue(PlayTheGame.board.getoKazan().return_num() == 82);
  }

  //Test draw conditions and that the correct window is returned
  public void testDrawPopUpGUI(){
    test = null;
    swinger.pause(1000);
    Kazan cKazan = new Kazan(71, false);
    Kazan pKazan = new Kazan(71, true);
    List<Hole> opponent = new ArrayList<Hole>();
    List<Hole> player = new ArrayList<Hole>();

    for (int i=0;i<9;i++){
        //init the board,contains holes and Kazans for both sides.
        //mark that the index starts from 0.
        Hole initP;
        Hole initO;
        if(i == 1){
          initP = new Hole(9,true,i);
          initO = new Hole(9,false,i);
        }
        else if( i == 0){
          initP = new Hole(9,true,i);
          initO = new Hole(9,false,i);
        }
        else if(i == 1){
          initP = new Hole(9,true,i);
          initO = new Hole(9,false,i);
        }
        else {
          initP = new Hole(0,true,i);
          initO = new Hole(0,false,i);
        }
        player.add(initP);
        opponent.add(initO);
    }

    customBoard = new Board(player, opponent,pKazan, cKazan);
    PlayTheGame.board = customBoard;
    setUp();
    confirmMove("p1");
    swinger.pause(2000);
    closePopUp();
    assertTrue(PlayTheGame.board.getoKazan().return_num() == 81 && PlayTheGame.board.getpKazan().return_num() == 81);
  }

  @After
  public void tearDown(){
    swinger = null;
  }

}

/*
   This class generates the main GUI window for playing Toguz Korgool
   Player has the option to move any holes they want.
   Displays the number of korgools captured.
   Author: Mandu, Marta, Admin, Tao
   Version: 11.2018
*/
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;

public class ToguzKorgoolGUI extends JFrame implements ActionListener{
    private static int MAX_ROCKS = 9;
    private static int MAX_HOLES = 9;

    private JButton[] playerHoles;
    private JButton[] computerHoles;
    private JLabel[] pLabel;
    private JLabel[] cLabel;
    private GridLayout gl;
    private JPanel south;
    private JPanel north;
    private JPanel center;
    private JTextArea computerLog;
    private String logText;

    private KazanGraphics pKazan;
    private KazanGraphics cKazan;
    private JOptionPane confirmMoveDiaglog;

    public ToguzKorgoolGUI() {
        super("Toguz Korgool");
        UIManager.put("Button.disabledText", Color.white);
        setUpGUIAndShow();
    }

    // Initialise all GUI components
    private void setUpGUIAndShow(){
      setLayout(new BorderLayout());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      confirmMoveDiaglog = new JOptionPane();
      confirmMoveDiaglog.setName("confirm");

      gl = new GridLayout(2,9);
      south = new JPanel(gl);
      north = new JPanel(gl);
      center = new JPanel(new GridLayout(2,1));
      playerHoles = new JButton[9];
      computerHoles = new JButton[9];
      pLabel = new JLabel[9];
      cLabel = new JLabel[9];

      setBackground(new Color(222,184,135));

      pKazan = new KazanGraphics(playTheGame.board.getpKazan().return_num());
      pKazan.setKorgoolColor(Color.black);
      cKazan = new KazanGraphics(playTheGame.board.getoKazan().return_num());
      cKazan.setKorgoolColor(Color.white);

      cKazan.setBackground(new Color(139,69,19));
      pKazan.setBackground(new Color(139,69,19));
      cKazan.setName("cKazan");
      pKazan.setName("pKazan");

      north.setBackground(Color.black);
      south.setBackground(Color.white);

      pKazan.add(new JLabel("Player Kazan"));
      cKazan.add(new JLabel("Computer Kazan"));

      logText = "Computer:\n";
      computerLog = new JTextArea(logText);
      computerLog.setEnabled(false);
      computerLog.setPreferredSize(new Dimension(100, 400));
      JScrollPane scroll = new JScrollPane(computerLog);
      //Set up GUI
      center.add(cKazan);
      center.add(pKazan);
      add(center, BorderLayout.CENTER);
      add(scroll, BorderLayout.EAST);

      setPreferredSize(new Dimension(820, 400));
      setResizable(false);
      setUpHoles();
      pack();
      setVisible(true);
    }

    //Initialise all components' value
    private void setUpHoles() {
        for(int i=0; i < MAX_HOLES; i++){
            pLabel[i]= new JLabel("Rocks: " + playTheGame.board.getBallsFromHole(i));
            cLabel[i]= new JLabel("Rocks: " + playTheGame.board.getBallsFromHole(i + 9));
            cLabel[i].setForeground(Color.white);
            south.add(pLabel[i]);
            north.add(cLabel[i]);
        }

        //player
        for(int i =0; i < MAX_HOLES; i++) {
            playerHoles[i] = new JButton(""+(i + 1));
            playerHoles[i].addActionListener(this);
            playerHoles[i].setName("p" +i);
            south.add(playerHoles[i]);
        }

        add(south, BorderLayout.SOUTH);
        //computer
        for(int i =0; i < MAX_HOLES; i++) {
            int index = MAX_HOLES - i;
            computerHoles[i] = new JButton(""+index);
            computerHoles[i].setEnabled(false);
            computerHoles[i].setName("c" +i);
            north.add(computerHoles[i]);
        }
        add(north, BorderLayout.NORTH);
        updateGUI();
    }

    //Check with playTheGame listener for changes in board. Mark button as Tuz if the corresponding
    //Hole is becomes a tuz.
    private void checkTuz(){
        List<Hole> tuzHole = playTheGame.board.getAllTheHoles().stream().filter(hole -> hole.checkTuz() == true).collect(Collectors.toList());
        for(Hole tuzs : tuzHole){
            if(!tuzs.getSide()){
                computerHoles[tuzs.getIndex()].setText("TUZ");
            }
            else{
                playerHoles[tuzs.getIndex()].setText("TUZ");
                playerHoles[tuzs.getIndex()].setEnabled(false);
            }
        }
    }

    //Check with board if any side has won or results in draw.
    //Either case create a pop-up dialog confirming that the game has finished
    private boolean checkFinished(){
        int checkStatus = playTheGame.board.checkwin();
        if(checkStatus == 0){
            JOptionPane.showMessageDialog(this, "The GAME was a DRAW");
            return true;
        }
        else if(checkStatus == 1){
          JOptionPane.showMessageDialog(this, "The PLAYER has WON");
          return true;
        }
        else if (checkStatus == -1){
          JOptionPane.showMessageDialog(this, "The OPPONENT has WON");
          return true;
        }

        return false;
    }

    //Whenever player (human) makes a move update the window GUI for any changes
    //This means when player chooses to move korgools in hole 7, all corresponding holes
    //will gain one korgool, and this should be updated.
    private void updateGUI(){
        for(int i =0; i< MAX_HOLES; i++){
            pLabel[i].setText("Rocks: " + playTheGame.board.getBallsFromHole(i));
            if(playTheGame.board.getBallsFromHole(i) == 0)
                playerHoles[i].setEnabled(false);
            else
                playerHoles[i].setEnabled(true);
            int index = MAX_HOLES - i;
            cLabel[i].setText("Rocks: " + playTheGame.board.getBallsFromHole(index -1 + 9));
        }
        checkTuz();

        //A log that shows what the computer has done, to help player keep track of the game.
        if(playTheGame.board.getLog() != null){
          logText += playTheGame.board.getLog() +"\n";
          computerLog.setText(logText);
        }

        pKazan.setKorgools(playTheGame.board.getpKazan().return_num());
        cKazan.setKorgools(playTheGame.board.getoKazan().return_num());
        pKazan.repaint();
        cKazan.repaint();

        south.repaint();
        north.repaint();
        if(checkFinished()){
            setEnabled(false);
        }
    }

    //This method determines what happens when player presses a button.
    //In this game, these buttons are holes which contains korgools.
    //Clicking the button will result in a confirm dialog for confirmation of moving korgools
    //After player moves their korgools and computer finishes their move, the GUI is then updated.
    @Override
    public void actionPerformed(ActionEvent e) {
        int confirmMove = confirmMoveDiaglog.showConfirmDialog(this, "Are you Sure? ");
        if(confirmMove == JOptionPane.YES_OPTION) {
            //From here we pass the 'i' which indicates which hole is being selected
            //Then we perform back-end computation
            int index = Integer.parseInt(e.getActionCommand());
            if(!playTheGame.board.moveTheBalls(index -1)){
                JOptionPane.showMessageDialog(new JFrame("Invalid"), "INVALID MOVE!");
            }
            updateGUI();
        }
    }
}

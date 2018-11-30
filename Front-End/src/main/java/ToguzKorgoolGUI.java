import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;

public class ToguzKorgoolGUI extends JFrame implements ActionListener{
    private static int MAX_ROCKS = 9;
    private static int MAX_HOLES = 9;

    private JButton[] playerHoles;
    private JButton[] computerHoles;
    private JButton pKazan;
    private JButton cKazan;
    private JLabel pTimer;
    private JLabel cTimer;
    private JLabel[] pLabel = new JLabel[9];
    private JLabel[] cLabel = new JLabel[9];
    private GridLayout gl = new GridLayout(2,9);
    private JPanel south = new JPanel(gl);
    private JPanel north = new JPanel(gl);
    private JPanel center = new JPanel(new GridLayout(2,1));
    private JTextArea computerLog;
    private String logText = "Computer:\n";
    //The class constructor can take in a Board Manager like class
    //This class can be used to obtain necessary information or to change information of the game i.e move rocks, claim tuz
    public ToguzKorgoolGUI() {
        super("Toguz Korgool");
        //Initialisation
        UIManager.put("Button.disabledText", Color.BLACK);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerHoles = new JButton[9];
        computerHoles = new JButton[9];
        pKazan = new JButton("Player Kazan");
        cKazan = new JButton("Computer Kazan");
        computerLog = new JTextArea(logText);
        computerLog.setEnabled(false);
        computerLog.setPreferredSize(new Dimension(100, 400));
        JScrollPane scroll = new JScrollPane(computerLog);
        pKazan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame("Status"),
                           e.getActionCommand() + " Current Rocks Claimed: "  + playTheGame.board.getpKazan().return_num()); // obtained data from back-end
            }
        });
        cKazan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame("Status"),
                            e.getActionCommand() + " Current Rocks Claimed: " +playTheGame.board.getoKazan().return_num());
                            // obtained data from back-end
            }
        });

        pTimer = new JLabel("Player Time: ");
        cTimer = new JLabel("Computer Time: ");
        //Set up GUI
        center.add(cKazan);
        center.add(pKazan);
        add(center, BorderLayout.CENTER);
        add(scroll, BorderLayout.EAST);
        setUpHoles();
        //setUpTimers();
        setPreferredSize(new Dimension(820, 400));
        pack();
        setVisible(true);
    }

    private void setUpTimers() {
        GridLayout gl = new GridLayout(2, 1);
        JPanel west = new JPanel(gl);
        west.add(cTimer);
        west.add(pTimer);
        //start timer...
        add(west, BorderLayout.WEST);
    }

    private void setUpHoles() {
        for(int i=0; i < MAX_HOLES; i++){
            pLabel[i]= new JLabel("Rocks: " + playTheGame.board.getBallsFromHole(i));
            cLabel[i]= new JLabel("Rocks: " + playTheGame.board.getBallsFromHole(i + 9));
            south.add(pLabel[i]);
            north.add(cLabel[i]);
        }

        //player
        for(int i =0; i < MAX_HOLES; i++) {
            //MAX_ROCKS should later be replaced with data obtained from back-end HOLE
            playerHoles[i] = new JButton(""+(i + 1));
            playerHoles[i].addActionListener(this);
            south.add(playerHoles[i]);
        }

        add(south, BorderLayout.SOUTH);
        //computer
        for(int i =0; i < MAX_HOLES; i++) {
            int index = MAX_HOLES - i;
            computerHoles[i] = new JButton(""+index);
            computerHoles[i].setEnabled(false);
            north.add(computerHoles[i]);
        }
        add(north, BorderLayout.NORTH);
    }

    private void checkTuz(){
        List<Hole> tuzHole = playTheGame.board.getAllTheHoles().stream().filter(hole -> hole.checkTuz() == true).collect(Collectors.toList());
        for(Hole tuzs : tuzHole){
            if(!tuzs.getSide()){
                computerHoles[tuzs.getIndex()].setText("TUZ");
            }
            else{
                playerHoles[tuzs.getIndex()].setText("TUZ");
            }
        }
    }

    private boolean checkFinished(){
        if(!playTheGame.board.getpKazan().check_win() && !playTheGame.board.getoKazan().check_win()){
            int pKazan = playTheGame.board.getpKazan().return_num();
            int oKazan = playTheGame.board.getoKazan().return_num();
            if(pKazan == 81 && pKazan == oKazan){
                JOptionPane.showMessageDialog(new JFrame("Draw"), "The GAME was a DRAW");
                return true;
            }
            return false;
        }
        else{
            if(playTheGame.board.getpKazan().check_win()){
                JOptionPane.showMessageDialog(new JFrame("Player"), "The PLAYER has WON");
                return true;
            }
            else{
                JOptionPane.showMessageDialog(new JFrame("Computer"), "The OPPONENT has WON");
                return true;
            }
        }
    }

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

        logText += playTheGame.board.getLog() + "\n";
        computerLog.setText(logText);

        if(checkFinished()){
            setEnabled(false);
        }

        south.repaint();
        north.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int confirmMove = JOptionPane.showConfirmDialog(new JFrame("Confirm"), "Are you Sure? ");
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

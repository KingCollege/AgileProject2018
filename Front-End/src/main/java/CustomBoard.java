/***
 * Sets up customisation menu for player to set each holes korgool and kazans captured korgool.
 * The menu will stop you from typing any invalid inputs i.e string characters.
 * @author Mandu Shi, Tao Lin, Marta Krawczyk and Adam Able
 * @version    2018.11.28
 */
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
public class CustomBoard extends JFrame implements KeyListener, ActionListener{

    private JTextField[] playerKorgools;
    private JTextField[] computerKorgools;
    private JLabel[] playerKorgoolsLabel;
    private JLabel[] computerKorgoolsLabel;
    private JTextField pKazan;
    private JTextField cKazan;
    private JButton submit;
    private JButton back;

    private JPanel center;
    private JPanel top;
    private JPanel bot;

    /**
      Initialisation
    */
    public CustomBoard(){
      super("Customise Board");
      setLayout(new BorderLayout());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      center = new JPanel(new GridLayout(2,1));
      top = new JPanel(new GridLayout(2,9));
      bot = new JPanel(new GridLayout(2,9));
      setUpGUI();

    }

    /**
      Initialises all swing components and assign appropriate values (if any)
      Adds ActionListener to submit and back button which will take them
      to the main menu or a new custom game.
    */
    private void setUpGUI(){
      playerKorgools = new JTextField[9];
      computerKorgools = new JTextField[9];

      pKazan = new JTextField();
      cKazan = new JTextField();

      pKazan.setName("pKazan");
      cKazan.setName("cKazan");

      submit = new JButton("Submit");
      submit.setName("submit");
      submit.addActionListener(this);
      back = new JButton("Back");
      back.setName("backToMenu");
      back.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
          new StartPageGUI();
          dispose();
        }
      });
      playerKorgoolsLabel = new JLabel[9];
      computerKorgoolsLabel = new JLabel[9];

      for(int i =0; i<9; i++){
        playerKorgoolsLabel[i] = new JLabel("P-Hole: " + (i + 1));
        computerKorgoolsLabel[i] = new JLabel("C-Hole: " + (9 - i) );
        top.add(computerKorgoolsLabel[i]);
        bot.add(playerKorgoolsLabel[i]);
      }

      for(int i =0; i< 9; i++){
        playerKorgools[i] = new JTextField();
        computerKorgools[i] = new JTextField();
        playerKorgools[i].setName("p"+i);
        computerKorgools[i].setName("c"+i);
        top.add(computerKorgools[i]);
        bot.add(playerKorgools[i]);
      }

      addKeyListeners();

      center.add(cKazan);
      center.add(pKazan);
      add(center, BorderLayout.CENTER);
      add(top, BorderLayout.NORTH);
      add(bot, BorderLayout.SOUTH);
      add(submit, BorderLayout.EAST);
      add(back, BorderLayout.WEST);
      setPreferredSize(new Dimension(820,300));
      pack();
      setResizable(false);
      setVisible(true);
    }

    /**
      This method returns false if at least one field on the menu is empty
      otherwise return true
    */
    private boolean checkIfAllFieldsAreFilled(){
      if(cKazan.getText().length() ==0 || pKazan.getText().length() == 0 )
        return false;

      for(JTextField tf : playerKorgools){
        if(tf.getText().length() == 0)
          return false;
      }

      for(JTextField tf : computerKorgools){
        if(tf.getText().length() == 0)
          return false;
      }

      return true;
    }

    /**
      This method adds a KeyListener to every JTextField, and upon release of pressing a character
      it determines whether if the input can be converted to an int, if it can't then it will
      empty the field.
    */
    private void addKeyListeners(){
      cKazan.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent e){
          try{
            int convert = Integer.valueOf(cKazan.getText());
          }catch(Exception error){cKazan.setText("");}
        }
      });

      pKazan.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent e){
          try{
            int convert = Integer.valueOf(pKazan.getText());
          }catch(Exception error){pKazan.setText("");}
        }
      });

      for(JTextField tf : playerKorgools){
        tf.addKeyListener(new KeyAdapter(){
          @Override
          public void keyReleased(KeyEvent e){
            try{
              int convert = Integer.valueOf(tf.getText());
            }catch(Exception error){tf.setText("");}
          }
        });
      }

      for(JTextField tf : computerKorgools){
        tf.addKeyListener(new KeyAdapter(){
          @Override
          public void keyReleased(KeyEvent e){
            try{
              int convert = Integer.valueOf(tf.getText());
            }catch(Exception error){tf.setText("");}
          }
        });
      }

    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    /**
      Overrides the actionPerformed method, this is used for submit button.
      It first checks if all fields are filled in, then creates appropriate
      objects required for a new board, creates a new game and closes itself.
    */
    @Override
    public void actionPerformed(ActionEvent e){
      if(checkIfAllFieldsAreFilled()){
          Kazan playerKazan = new Kazan(Integer.valueOf(pKazan.getText()), true);
          Kazan computerKazan = new Kazan(Integer.valueOf(cKazan.getText()), false);
          List<Hole> pHoles = new ArrayList<Hole>();
          List<Hole> cHoles = new ArrayList<Hole>();
          for(int i =0; i< 9; i++){
            Hole pHole = new Hole(Integer.valueOf(playerKorgools[i].getText()), true, i);
            Hole cHole = new Hole(Integer.valueOf(computerKorgools[8-i].getText()), false, i);
            pHoles.add(pHole);
            cHoles.add(cHole);
          }
          PlayTheGame.board = new Board(pHoles, cHoles, playerKazan, computerKazan);
          new ToguzKorgoolGUI();
          dispose();
      }
    }

}

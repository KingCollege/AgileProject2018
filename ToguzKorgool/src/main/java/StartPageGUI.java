/**
 * Class to create the welcome page of the Toguz Korgool game.
 * @Author Adam, Tao, Marta, Mandu
 * @version 0.1.1
 */
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
public class StartPageGUI extends JFrame {
    private static int GUI_WIDTH = 820;
    private static int GUI_HEIGHT = 400;
    private JButton playAI;
    private JButton loadGame;
    private JButton customGame;
    private JButton help;
    private JButton quit;
    private JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
    private JPanel centerBottom = new JPanel(new GridLayout(1, 2, 10, 0));

    private static String saveFileName ="SAVE.csv";
    private static String filePath = "src/main/resources/";
    private File savedData;
    /**
     * Constructor for start page
     */
    public StartPageGUI() {
        super("Toguz Korgool");
        UIManager.put("Button.disabledText", Color.BLACK);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        savedData = new File(filePath + saveFileName);

        playAI = new JButton("Play vs AI");
        loadGame = new JButton("Load Game");
        customGame = new JButton("Custom Game");

        playAI.addActionListener(ev -> playAIWindow());

        loadGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
              loadSavedData();
            }
        });

        customGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
              new CustomBoard();
              dispose();
            }
        });


        if(!savedData.exists())
          loadGame.setEnabled(false);
        else
          loadGame.setEnabled(true);

        center.add(playAI);
        center.add(loadGame);
        center.add(customGame);

        help = new JButton("Help");
        quit = new JButton("Quit");

        playAI.setName("newGame");
        loadGame.setName("loadGame");
        customGame.setName("customGame");
        help.setName("help");
        quit.setName("quit");

        help.addActionListener(ev -> helpWindow());
        quit.addActionListener(ev -> closeWindow());

        centerBottom.add(help);
        centerBottom.add(quit);
        center.add(centerBottom);

        add(center, BorderLayout.CENTER);

        addPadding();

        setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        pack();
        setVisible(true);
    }

    /**
     * method used to add padding around menu buttons
     */
    private void addPadding() {
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        left.setPreferredSize(new Dimension(200, GUI_HEIGHT));
        right.setPreferredSize(new Dimension(200, GUI_HEIGHT));
        top.setPreferredSize(new Dimension(GUI_WIDTH, 120));
        bottom.setPreferredSize(new Dimension(GUI_WIDTH, 120));

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadSavedData(){
      List<String> data = new ArrayList<String>();
      try{
        Scanner sc = new Scanner(savedData);
        while(sc.hasNextLine())
          data.add(sc.nextLine());
      }catch(IOException e){
        System.out.println("Read file error.");
      }

      Kazan playerKazan = new Kazan(Integer.valueOf(data.get(0)), true);
      Kazan computerKazan = new Kazan(Integer.valueOf(data.get(2)), false);
      List<Hole> pHoles = new ArrayList<Hole>();
      List<Hole> cHoles = new ArrayList<Hole>();
      String[] csvData = data.get(1).split(",");

      //-1 because of last comma
      for( int i = 0 ; i < csvData.length ; i++){
          int convert = Integer.valueOf(csvData[i]);
          System.out.println(convert);
          Hole p = new Hole(convert, true, i);
          pHoles.add(p);
      }
      csvData = data.get(3).split(",");
      for( int i = 0 ; i < csvData.length ; i++){
          int convert = Integer.valueOf(csvData[i]);
          Hole c = new Hole(convert, false, i);
          cHoles.add(c);
      }

      PlayTheGame.board = new Board(pHoles, cHoles, playerKazan, computerKazan);
      playAIWindow();
    }

    /**
     * launch game vs AI
     */
    private void playAIWindow() {
        new ToguzKorgoolGUI();
        dispose();
    }

    /**
     * close window
     */
    private void closeWindow() {
        if(JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    /**
     * open help window
     */
    private void helpWindow() {
        new WindowHelpGUI();
    }

}

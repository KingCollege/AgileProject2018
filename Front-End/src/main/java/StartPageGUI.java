import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * Class to create the welcome page of the Toguz Korgool game.
 * @Author Adam, Tao, Marta, Mandu
 * @version 0.1.1
 */
public class StartPageGUI extends JFrame {
    private static int GUI_WIDTH = 820;
    private static int GUI_HEIGHT = 400;
    private JButton playAI;
    private JButton playPlayer;
    private JButton help;
    private JButton quit;
    private JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
    private JPanel centerBottom = new JPanel(new GridLayout(1, 2, 10, 0));

    /**
     * Constructor for start page
     */
    public StartPageGUI() {
        super("Toguz Korgool");
        UIManager.put("Button.disabledText", Color.BLACK);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playAI = new JButton("Play vs AI");
        playPlayer = new JButton("Play vs Player");

        playAI.addActionListener(ev -> playAIWindow());
        playPlayer.setEnabled(false);

        center.add(playAI);
        center.add(playPlayer);

        help = new JButton("Help");
        quit = new JButton("Quit");

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

    /**
     * launch game vs AI
     */
    private void playAIWindow() {
        new ToguzKorgoolGUI();
        setVisible(false);
    }

    /**
     * close window
     */
    private void closeWindow() {
        if(JOptionPane.showConfirmDialog(new JFrame(),
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI {
    private JFrame frame;
    private static int WINDOW_WIDTH = 1200;
    private static int WINDOW_HEIGHT = 600;

    public GUI() {
        makeFrame();
    }

    private void setTuz(JButton e) {
        if (e.getBackground() != Color.RED) {
            e.setBackground(Color.RED);
        } else {
            e.setBackground(UIManager.getColor("Button.background"));
        }
    }

    private void makePlayerHoles(JPanel panel) {
        JPanel playerHoles = new JPanel();
        playerHoles.setLayout(new GridLayout(1, 9, 10, 0));

        for (int i = 0; i < 9; ++i) {
            JButton newButton = new JButton("9");
            newButton.addActionListener(ev -> setTuz(newButton));
            playerHoles.add(newButton);
        }

        panel.add(playerHoles);
    }

    private void makeFrame() {
        // initialise the frame
        frame = new JFrame("Toguz Korgool");
        Container contentPane = frame.getContentPane();

        // set main layout
        BorderLayout mainBox = new BorderLayout();
        contentPane.setLayout(mainBox);

        // populate top bar
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 75));
        topPanel.setLayout(new GridLayout(1, 3));

        JLabel emptyPanel = new JLabel();
        JLabel player2Title = new JLabel("Player 2");
        JLabel player2Timer = new JLabel("Time remaining: 420");
        topPanel.add(emptyPanel);
        topPanel.add(player2Title);
        topPanel.add(player2Timer);

        contentPane.add(topPanel, BorderLayout.PAGE_START);


        // populate center area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 10));

        // create the holes for player 2
        makePlayerHoles(centerPanel);

        // create the points bit in the middle
        JPanel pointsArea = new JPanel();
        pointsArea.setLayout(new FlowLayout(FlowLayout.CENTER, 220, 10));

        JButton player2points = new JButton("Player 2 points: 0");
        JButton player1points = new JButton("Player 1 points: 0");

        int PREFERRED_WIDTH = 260;
        int PREFERRED_HEIGHT = 100;

        player2points.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        player1points.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        pointsArea.add(player2points);
        pointsArea.add(player1points);

        centerPanel.add(pointsArea);

        // create the holes for player 1
        makePlayerHoles(centerPanel);

        contentPane.add(centerPanel, BorderLayout.CENTER);


        // populate bottom of screen
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 75));
        bottomPanel.setLayout(new GridLayout(1, 3));

        JLabel emptyPanel2 = new JLabel();
        JLabel player1Title = new JLabel("Player 1");
        JLabel player1Timer = new JLabel("Time remaining: 420");
        bottomPanel.add(emptyPanel2);
        bottomPanel.add(player1Title);
        bottomPanel.add(player1Timer);

        contentPane.add(bottomPanel, BorderLayout.PAGE_END);

        // exit program when 'x' in corner is clicked
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GUI g = new GUI();
    }
}



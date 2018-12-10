import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * Class to display help window and switch between panels
 * @Author Adam, Tao, Marta, Mandu
 * @version 0.1.1
 */
public class WindowHelpGUI extends JFrame {
    private JFrame frame;
    private static int GUI_WIDTH = 820;
    private static int GUI_HEIGHT = 400;
    private int currentPage;

    /**
     * Constructor for help window
     */
    public WindowHelpGUI() {
        makeFrame();
    }

    /**
     * fills out the previous, next, and page number for the help window
     * @param contentPane the content pane to add the buttons to
     */
    private void makePageBottom(Container contentPane) {
        JButton back = new JButton("<");
        JButton forward = new JButton(">");
        JLabel pageNum = new JLabel(currentPage + "/3", SwingConstants.CENTER);

        if (currentPage == 1) {back.setEnabled(false);}
        if (currentPage == 3) {forward.setEnabled(false);}

        forward.addActionListener(ev -> nextPage(contentPane));
        back.addActionListener(ev -> prevPage(contentPane));

        JPanel bottom = new JPanel(new GridLayout(1, 3, 10, 10));

        bottom.add(back);
        bottom.add(pageNum);
        bottom.add(forward);

        contentPane.add(bottom, BorderLayout.SOUTH);


    }

    /**
     * fills out the first slide of the help window
     * @param contentPane the content pane to add the help slide to
     */
    private void makeFirstPage(Container contentPane) {
        contentPane.removeAll();

        JLabel title = new JLabel("<html>" +
                "                   <h1 style='text-align: center;'>How To Play</h1>" +
                "                  </html", SwingConstants.CENTER);



        JLabel tutorialText = new JLabel("<html>" +
                "                   <div>" +
                "                       <h2>Game Board: </h2>" +
                "                       <p> Toguz Korgool employs a board containing 18 holes and two sides, a white/light side and a black/dark side. The player on the white/light side starts the game. Each player side has nine holes, numbered 1-9 from left to right, and one larger rectangular store called the player's kazan. Initially, 162 korgools (small round stones) are distributed equally over the 18 holes in the board: 9 korgools per hole. No stones are placed in the player's kazans.</p<" +
                "                       <br/>" +
                "                       <h2>Move: </h2>" +
                "                       <p>Players make moves by selecting one of the holes on their side of the game that contains korgools. A move consists of taking all the korgools from the selected hole and redistributing or seeding them to other holes in the anticlockwise direction. The first korgool is put in the hole the korgools were taken from. The next korgool goes in the adjacent hole to the right, and so on. Once the player has dropped a korgool in hole 9, the next korgool goes into hole 1 of the other player, and so on.</p>" +
                "                  </html>", SwingConstants.CENTER);


        contentPane.add(title, BorderLayout.NORTH);
        contentPane.add(tutorialText, BorderLayout.CENTER);
        makePageBottom(contentPane);

        contentPane.repaint();
        contentPane.revalidate();
    }

    /**
     * fills out the second slide of the help window
     * @param contentPane the content pane to add the help slide to
     */
    private void makeSecondPage(Container contentPane) {
        contentPane.removeAll();

        JLabel title = new JLabel("<html>" +
                "                   <h1>How To Play</h1>" +
                "                  </html", SwingConstants.CENTER);

        JLabel tutorialText = new JLabel("<html>" +
                "                           <div>" +
                "                               <h2>Moving</h2>" +
                "                               <ul>" +
                "                                   <li>If the last korgool in a move ends up in an opponent's hole, and the number of korgools in that hole is now even, then the player captures all the korgools in that hole. The korgools are moved into the player's kazan.</li>" +
                "                                   <li>If the last korgool in a move ends up in the player's own side, nobody captures these korgools.</li>" +
                "                                   <li>If the last korgool in a move ends up in an opponent's hole, and the number of korgools in that hole is now odd, then nobody captures these korgools.</li>" +
                "                               </ul>" +
                "                           </div>" +
                "                         </html>");

        contentPane.add(title, BorderLayout.NORTH);
        contentPane.add(tutorialText, BorderLayout.CENTER);
        makePageBottom(contentPane);

        contentPane.repaint();
        contentPane.revalidate();
    }

    /**
     * fills out the third slide of the help window
     * @param contentPane the content pane to add the help slide to
     */
    private void makeThirdPage(Container contentPane) {
        contentPane.removeAll();

        JLabel title = new JLabel("<html>" +
                "                   <h1>How To Play</h1>" +
                "                  </html", SwingConstants.CENTER);

        JLabel tutorialText = new JLabel("<html>" +
                "                           <div>" +
                "                               <h2>Tuz</h2>" +
                "                               <p>If the last korgool in a move ends up in an opponent's hole containing two korgools (i.e. the hole contains three korgools at the end of the move), then this hole is marked as a tuz. This means that this hole now belongs to the player who claimed the tuz and all korgools in the tuz are transferred to the owner's kazan. The following restrictions apply:</p>" +
                "                               <ul>" +
                "                                   <li>Each player can claim only one tuz.</li>" +
                "                                   <li>A tuz cannot be moved.</li>" +
                "                                   <li>Hole 9 cannot be claimed as tuz.</li>" +
                "                                   <li>If one player has claimed hole n as tuz, then their opponent can no longer claim hole n on the opposite side as tuz.</li>" +
                "                               </ul>" +
                "                               <h2>How to win</h2>" +
                "                               <p>The game ends when one player has collected 82 or more korgools in their kazan.</p>" +
                "                           </div>" +
                "                         </html>");

        contentPane.add(title, BorderLayout.NORTH);
        contentPane.add(tutorialText, BorderLayout.CENTER);
        makePageBottom(contentPane);

        contentPane.repaint();
        contentPane.revalidate();
    }

    /**
     * decrements page number and sends user to page
     * @param contentPane the content pane to be updated
     */
    private void prevPage(Container contentPane) {
        currentPage--;
        choosePage(contentPane);
    }

    /**
     * increments page number and sends user to page
     * @param contentPane the content pane to be updated
     */
    private void nextPage(Container contentPane) {
        currentPage++;
        choosePage(contentPane);
    }

    /**
     * figures out which page to go to
     * @param contentPane the content page to be updated
     */
    private void choosePage(Container contentPane) {
        switch (currentPage) {
            case 1: makeFirstPage(contentPane);
                break;
            case 2: makeSecondPage(contentPane);
                break;
            case 3: makeThirdPage(contentPane);
        }
    }

    /**
     * initailises the first page when help screen is opened
     */
    private void makeFrame() {
        frame = new JFrame("Toguz Korgool - Help");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        currentPage = 1;

        makeFirstPage(contentPane);

        frame.setLocation(100,100);
        frame.setSize(GUI_WIDTH, GUI_HEIGHT);
        frame.setVisible(true);
    }
}
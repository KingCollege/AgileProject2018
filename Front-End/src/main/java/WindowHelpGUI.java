import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * Class to display help window
 */
public class WindowHelpGUI extends JFrame {

    private static int GUI_WIDTH = 820;
    private static int GUI_HEIGHT = 400;
    private JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));

    public WindowHelpGUI() {
        super("Toguz Korgool");
        UIManager.put("Button.disabledText", Color.BLACK);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        pack();
        setVisible(true);
    }
}
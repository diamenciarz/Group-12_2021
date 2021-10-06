
/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel {
    private static JFrame window = new JFrame();
    private static int[][] state;
    private static int size;

    static JPanel panel = new JPanel();
    static JLabel labelTitle;
    static JLabel trialsLabel;
    static Font font1;
    static Font font2;

    public static UI thisUI;

    /**
     * Constructor for the GUI. Sets everything up
     * 
     * @param x     x position of the GUI
     * @param y     y position of the GUI
     * @param _size size of the GUI
     */
    public UI() {
        // labelTitle = new JLabel("PENTOMINOES", SwingConstants.CENTER);
        font1 = new Font("Calbri", Font.BOLD, 12);
        font2 = new Font("Calibri", Font.BOLD, 30);
        trialsLabel = new JLabel();
        thisUI = this;
    }

    public static void UpdateTrials(int trials) {
        trialsLabel = new JLabel("Number of trials: " + trials);
        trialsLabel.setFont(font1);
        // panel.add(labelTitle);
        SetDisplay();
        panel.add(trialsLabel);

    }

    public static void CreateDisplay(int x, int y, int _size) {
        size = _size;
        thisUI.setPreferredSize(new Dimension(x * size, y * size));

        state = new int[x][y];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    public static void SetDisplay() {
        // labelTitle.setFont(font2);
        // labelTitle.setForeground(Color.RED);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(thisUI);

        window.setLayout(new GridLayout(0, 1));
        window.add(panel, BorderLayout.CENTER);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Pentominoes");
        window.pack();
        window.setVisible(true);

    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the
     * state stored by the UI class.
     */
    public void paintComponent(Graphics g) {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(Color.LIGHT_GRAY);
        localGraphics2D.fill(getVisibleRect());

        // draw lines
        localGraphics2D.setColor(Color.GRAY);
        for (int i = 0; i <= state.length; i++) {
            localGraphics2D.drawLine(i * size, 0, i * size, state[0].length * size);
        }
        for (int i = 0; i <= state[0].length; i++) {
            localGraphics2D.drawLine(0, i * size, state.length * size, i * size);
        }

        // draw blocks
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(i * size + 1, j * size + 1, size - 1, size - 1));
            }
        }
    }

    /**
     * Decodes the ID of a pentomino into a color
     * 
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more
     *         in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i) {
        if (i == 1) {
            return Color.BLUE;
        } else if (i == 2) {
            return Color.ORANGE;
        } else if (i == 3) {
            return Color.CYAN;
        } else if (i == 4) {
            return Color.GREEN;
        } else if (i == 5) {
            return Color.MAGENTA;
        } else if (i == 6) {
            return Color.PINK;
        } else if (i == 7) {
            return Color.RED;
        } else if (i == 8) {
            return Color.YELLOW;
        } else if (i == 9) {
            return new Color(0, 0, 0);
        } else if (i == 10) {
            return new Color(0, 0, 100);
        } else if (i == 11) {
            return new Color(100, 0, 0);
        } else if (i == 12) {
            return new Color(0, 100, 0);
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * 
     * @param _state information about the new state of the GUI
     */
    public static void UpdateGrid(int[][] _state) {

        int x = _state[0].length;
        int y = _state.length;
        int sizeOfEachSquare = 40; // Update later

        CreateDisplay(x, y, sizeOfEachSquare);
        SetDisplay();
        for (int i = 0; i < state[i].length; i++) {
            for (int j = 0; j < state.length; j++) {
                state[j][i] = _state[i][j];
            }
        }

        // Tells the system a frame update is required
        thisUI.repaint();
    }
}

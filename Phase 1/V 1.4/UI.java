
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
    static JLabel timeLable;
    static Font font1;
    static Font font2;

    public static UI thisUI;
    private static boolean hasCreatedDisplay;
    private static boolean hasCreatedTrials;
    private static boolean hasCreatedTime;

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
        //labelTitle = new JLabel();
        //trialsLabel = new JLabel();
        thisUI = this;
        hasCreatedDisplay = false;
        hasCreatedTrials = false;
        hasCreatedTime = false;

    }

    public void UpdateGrid(int[][] _state) {

        int x = _state[0].length;
        int y = _state.length;

        int sizeOfEachSquare = CountSquareSize(x, y);

        if (!hasCreatedDisplay) {
            CreateDisplay();
        }
        SetDisplay(_state, sizeOfEachSquare);
        // Tells the system a frame update is required
        thisUI.repaint();
    }

    public void UpdateTrials(int trials) {
        if (!hasCreatedTrials) {
            CreateTrials();
        }
        trialsLabel.setText("Number of trials: " + trials);
        //trialsLabel.setFont(font1);
        panel.add(trialsLabel);

        thisUI.repaint();

    }

    public void UpdateTime(long time){
        if (!hasCreatedTime){
            CreateTime();
        }
        timeLable.setText("Total execution time: "+ time);
        panel.add(timeLable);

        thisUI.repaint();
    }

    private static void SetDisplay(int[][] _state, int _size) {
        int x = _state[0].length;
        int y = _state.length;
        size = _size;

        int deltaXSize = 00;
        int deltaYSize = 00;

        thisUI.setPreferredSize(new Dimension(x * size + deltaXSize, y * size + deltaYSize));

        OverrideStateWithMinusOnes(x, y);
        FillStateValues(_state);
    }

    private static void OverrideStateWithMinusOnes(int x, int y) {
        state = new int[y][x];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    private static void FillStateValues(int[][] newState) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                state[y][x] = newState[y][x];
            }
        }
    }

    private static void CreateTrials() {

        trialsLabel = new JLabel("Number of trials: " + 0);
        labelTitle = new JLabel("Pentominoes");
        
        panel.add(labelTitle);
        panel.add(trialsLabel);
        
        trialsLabel.setFont(font1);
        labelTitle.setFont(font2);
        labelTitle.setForeground(Color.RED);
        window.pack();
        window.setVisible(true);

        hasCreatedTrials = true;
    }

    public static void CreateTime(){
        timeLable = new JLabel("Total execution time: " + 0);
        
        panel.add(timeLable);

        timeLable.setFont(font1);
        window.pack();
        window.setVisible(true);

        hasCreatedTime = true;
    }

    private static void CreateDisplay() {

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

        hasCreatedDisplay = true;
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
    private static int CountSquareSize(int x, int y) {
        int xScreenSize = 1920 - 300;
        int yScreenSize = 1080 - 300;

        int squareLength = xScreenSize / x;
        int squareHeight = yScreenSize / y;

        if (squareHeight > squareLength) {
            return squareLength;
        } else {
            return squareHeight;
        }
    }
}
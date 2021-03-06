<<<<<<< HEAD
<<<<<<< HEAD
/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel{

    private static JFrame frame = new JFrame();
    private static int[][] state;
    private static int size;

    static JPanel gridPanel = new JPanel();
    static JPanel scorePanel = new JPanel();
    static JPanel highscoresPanel = new JPanel();
    static JLabel scoreLabel = new JLabel("Score: 0");
    static JLabel highScoresLabel = new JLabel("Highscores: ");
    static JLabel rank1 = new JLabel("Rank 1: ");
    static JLabel rank2 = new JLabel("Rank 2: ");
    static JLabel rank3 = new JLabel("Rank 3: ");
    static JLabel rank4 = new JLabel("Rank 4: ");
    static JLabel rank5 = new JLabel("Rank 5: ");
    static JLabel labelTitle;
    static JLabel trialsLabel;
    static Font font1;
    static Font font2;

    //private static int score = 0;

    public static UI thisUI;
    private static boolean hasCreatedDisplay;

    /**
     * Constructor for the GUI. Sets everything up
     * 
     * @param x     x position of the GUI
     * @param y     y position of the GUI
     * @param _size size of the GUI
     */
    public UI() {
        font1 = new Font("Calbri", Font.BOLD, 12);
        font2 = new Font("Calibri", Font.BOLD, 30);
        thisUI = this;
        hasCreatedDisplay = false;
    }

    public void updateGrid(int[][] matrixToDisplay) {

        // scores
        scoreLabel.setText("Score: " + Integer.toString(Pentis.score));
        rank1.setText("Rank 1: " + Pentis.sortedHighscores[0]);
        rank2.setText("Rank 2: " + Pentis.sortedHighscores[1]);
        rank3.setText("Rank 3: " + Pentis.sortedHighscores[2]);
        rank4.setText("Rank 4: " + Pentis.sortedHighscores[3]);
        rank5.setText("Rank 5: " + Pentis.sortedHighscores[4]);

        int x = matrixToDisplay.length;
        int y = matrixToDisplay[0].length;

        int sizeOfEachSquare = CountSquareSize(x, y);

        if (!hasCreatedDisplay) {
            CreateDisplay();
        }
        SetDisplay(matrixToDisplay, sizeOfEachSquare);

        // Tells the system a frame update is required
        thisUI.repaint();
    }

    private static void SetDisplay(int[][] _state, int _size) {
        int x = _state[0].length;
        int y = _state.length;
        size = _size;

        thisUI.setMaximumSize(new Dimension(x * size, y * size));

        OverrideStateWithMinusOnes(x, y);
        FillStateValues(_state);
    }

    private static void OverrideStateWithMinusOnes(int x, int y) {
        state = new int[x][y];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    private static void FillStateValues(int[][] newState) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                state[y][x] = newState[x][y];
            }
        }
    }

    private static void CreateDisplay() {

        gridPanel.setBounds(10, 10, 273,681);
        gridPanel.setLayout(new GridLayout(0, 1));
        gridPanel.add(thisUI);

        highscoresPanel.setBackground(Color.lightGray);
        highscoresPanel.setBounds(10+10+273, 10, 200,200);

        scorePanel.setBackground(Color.lightGray    );
        scorePanel.setBounds(10+10+273,10+10+200,200,200);
        highscoresPanel.setLayout(new GridLayout(6,0));
        scorePanel.add(scoreLabel);

        highscoresPanel.add(highScoresLabel);
        highscoresPanel.add(rank1);
        highscoresPanel.add(rank2);
        highscoresPanel.add(rank3);
        highscoresPanel.add(rank4);
        highscoresPanel.add(rank5);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(249,207,221));

        frame.setLocationRelativeTo(null);
        frame.setTitle("Pentominotris");
        frame.setLayout(null);
        frame.setSize(503,880);
        frame.setVisible(true);

        frame.add(gridPanel);
        frame.add(highscoresPanel);
        frame.add(scorePanel);
       
        hasCreatedDisplay = true;
        setupKeyListener();
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
        localGraphics2D.setColor(new Color(230,230,200));
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
            return Color.WHITE;
        }
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * 
     * @param _state information about the new state of the GUI
     */
    private static int CountSquareSize(int x, int y) {
        int xScreenSize = 1920 - 400;
        int yScreenSize = 1080 - 400;

        int squareLength = yScreenSize / x;
        int squareHeight = xScreenSize / y;

        if (squareHeight > squareLength) {
            return squareLength;
        } else {
            return squareHeight;
        }
    }
    
    private static void setupKeyListener(){
        frame.addKeyListener(new Input());
    }

    
}

=======
/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel{

    private JFrame frame = new JFrame();
    private int[][] state;
    private int size;

    JPanel gridPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel highscoresPanel = new JPanel();
    JPanel invisHighscoresPanel = new JPanel();
    JLabel scoreLabel = new JLabel("Score: 0");
    JLabel highScoresLabel = new JLabel("Highscores: ");
    JLabel rank1 = new JLabel("Rank 1: ");
    JLabel rank2 = new JLabel("Rank 2: ");
    JLabel rank3 = new JLabel("Rank 3: ");
    JLabel rank4 = new JLabel("Rank 4: ");
    JLabel rank5 = new JLabel("Rank 5: ");
    JLabel labelTitle;
    JLabel trialsLabel;
    Font font1;
    Font font2;

    //private int score = 0;

    private boolean hasCreatedDisplay;

    private long displayDelay = 300;
    //private boolean hasCreatedTrials;

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
        // labelTitle = new JLabel();
        // trialsLabel = new JLabel();
        hasCreatedDisplay = false;
        //hasCreatedTrials = false;

    }

    public void updateGrid(int[][] matrixToDisplay, int score) {

        //score = Pentis.score;
        scoreLabel.setText("Score: " + score);
        //highScoresLabel.setText("High");
        //highScoresLabel.setText(HelperMethods.highScoreOrdered(Pentis.highScores));
        rank1.setText("Rank 1: " + Pentis.sortedHighscores[0]);
        rank2.setText("Rank 2: " + Pentis.sortedHighscores[1]);
        rank3.setText("Rank 3: " + Pentis.sortedHighscores[2]);
        rank4.setText("Rank 4: " + Pentis.sortedHighscores[3]);
        rank5.setText("Rank 5: " + Pentis.sortedHighscores[4]);



        
        
        

        // int x = matrixToDisplay[0].length;
        // int y = matrixToDisplay.length;
        int x = matrixToDisplay.length;
        int y = matrixToDisplay[0].length;

        int sizeOfEachSquare = CountSquareSize(x, y);

        if (!hasCreatedDisplay) {
            CreateDisplay();
        }
        SetDisplay(matrixToDisplay, sizeOfEachSquare);
        // Tells the system a frame update is required
        this.repaint();
        waitTime(displayDelay);
    }
    

    private void SetDisplay(int[][] _state, int _size) {
        int x = _state[0].length;
        int y = _state.length;
        // int x = _state.length;
        // int y = _state[0].length;
        size = _size;

        this.setMaximumSize(new Dimension(x * size, y * size));

        OverrideStateWithMinusOnes(x, y);
        FillStateValues(_state);
    }

    private void OverrideStateWithMinusOnes(int x, int y) {
        //state = new int[y][x];
        state = new int[x][y];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    private void FillStateValues(int[][] newState) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                state[y][x] = newState[x][y];
            }
        }
    }

    private void CreateDisplay() {

        //gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridPanel.setBounds(10, 10, 273,681);
        gridPanel.setLayout(new GridLayout(0, 1));
        gridPanel.add(this);

        highscoresPanel.add(invisHighscoresPanel);
        highscoresPanel.setBackground(Color.lightGray);
        highscoresPanel.setBounds(10+10+273, 10, 200,200);

        //ScoreListener scoreListener = new ScoreListener();
        //Pentis.score.addActionListener(scoreListener);
        //highScores = 

        //scoreLabel = new JLabel("Score: 0");
        //scoreLabel = new JLabel();
        //scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scorePanel.setBackground(Color.lightGray    );
        //scorePanel.setLayout(new GridLayout(0, 1));
        scorePanel.setBounds(10+10+273,10+10+200,200,200);
        invisHighscoresPanel.setLayout(new GridLayout(6,0));
        invisHighscoresPanel.setBackground(Color.lightGray);
        invisHighscoresPanel.setBounds(10+10+273 + 10, 10 + 10, 180,180);
        //scoreLabel.setAlignmentY(SwingConstants.CENTER);
        scorePanel.add(scoreLabel);

        invisHighscoresPanel.add(highScoresLabel);
        invisHighscoresPanel.add(rank1);
        invisHighscoresPanel.add(rank2);
        invisHighscoresPanel.add(rank3);
        invisHighscoresPanel.add(rank4);
        invisHighscoresPanel.add(rank5);



        //frame.setLayout(new GridLayout(0, 1));
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(249,207,221));
        //frame.getContentPane().setBackground(Color.black);

        frame.setLocationRelativeTo(null);
        frame.setTitle("Pentominotris");
        frame.setLayout(null);
        frame.setSize(503,880);
        //frame.pack();
        frame.setVisible(true);

        frame.add(gridPanel);
        frame.add(highscoresPanel);
        frame.add(scorePanel);
       
        hasCreatedDisplay = true;
        setupKeyListener();
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
        localGraphics2D.setColor(new Color(230,230,200));
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

        // while (true) {
        //     score = Pentis.score;

        // }

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
            return Color.WHITE;
        }
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * 
     * @param _state information about the new state of the GUI
     */
    private int CountSquareSize(int x, int y) {
        int xScreenSize = 1920 - 400;
        int yScreenSize = 1080 - 400;

        int squareLength = yScreenSize / x;
        int squareHeight = xScreenSize / y;

        if (squareHeight > squareLength) {
            return squareLength;
        } else {
            return squareHeight;
        }
    }
    
    private void setupKeyListener(){
        frame.addKeyListener(new Input());
    }

    private void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
    
}

>>>>>>> d158b094497fd2b9a6a8ebd45edec8b27ff7387d
=======
/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel{

    private JFrame frame = new JFrame();
    private int[][] state;
    private int size;

    JPanel gridPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel highscoresPanel = new JPanel();
    JPanel invisHighscoresPanel = new JPanel();
    JLabel scoreLabel = new JLabel("SCORE: 0");
    JLabel highScoresLabel = new JLabel("HIGHSCORES: ");
    JLabel rank1 = new JLabel("RANK 1: ");
    JLabel rank2 = new JLabel("RANK 2: ");
    JLabel rank3 = new JLabel("RANK 3: ");
    JLabel rank4 = new JLabel("RANK 4: ");
    JLabel rank5 = new JLabel("RANK 5: ");
    JLabel labelTitle;
    JLabel trialsLabel;
    Font font1;
    Font font2;

    private boolean hasCreatedDisplay;

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
        // labelTitle = new JLabel();
        // trialsLabel = new JLabel();
        hasCreatedDisplay = false;
        //hasCreatedTrials = false;

    }

    public void updateGrid(int[][] matrixToDisplay, int score) {

        //score = Pentis.score;
        scoreLabel.setText("SCORE: " + score);
        //highScoresLabel.setText("High");
        //highScoresLabel.setText(HelperMethods.highScoreOrdered(Pentis.highScores));
        rank1.setText("RANK 1: " + Pentis.sortedHighscores[0]);
        rank2.setText("RANK 2: " + Pentis.sortedHighscores[1]);
        rank3.setText("RANK 3: " + Pentis.sortedHighscores[2]);
        rank4.setText("RANK 4: " + Pentis.sortedHighscores[3]);
        rank5.setText("RANK 5: " + Pentis.sortedHighscores[4]);

        



        
        
        

        // int x = matrixToDisplay[0].length;
        // int y = matrixToDisplay.length;
        int x = matrixToDisplay.length;
        int y = matrixToDisplay[0].length;

        int sizeOfEachSquare = CountSquareSize(x, y);

        if (!hasCreatedDisplay) {
            CreateDisplay();
        }
        SetDisplay(matrixToDisplay, sizeOfEachSquare);
        // Tells the system a frame update is required
        this.repaint();
    }
    

    private void SetDisplay(int[][] _state, int _size) {
        int x = _state[0].length;
        int y = _state.length;
        // int x = _state.length;
        // int y = _state[0].length;
        size = _size;

        this.setMaximumSize(new Dimension(x * size, y * size));

        OverrideStateWithMinusOnes(x, y);
        FillStateValues(_state);
    }

    private void OverrideStateWithMinusOnes(int x, int y) {
        //state = new int[y][x];
        state = new int[x][y];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    private void FillStateValues(int[][] newState) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                state[y][x] = newState[x][y];
            }
        }
    }

    private void CreateDisplay() {

        Font myFont = new Font("Calibri", Font.BOLD, 23);

        scoreLabel.setForeground(new Color(110,203,219));
        highScoresLabel.setForeground(new Color(110,203,219));
        rank1.setForeground(new Color(238,63,88));
        rank2.setForeground(new Color(246,131,37));
        rank3.setForeground(new Color(248,182,25));
        rank4.setForeground(new Color(60,177,74));
        rank5.setForeground(new Color(10,183,237));

        scoreLabel.setFont(myFont);
        highScoresLabel.setFont(myFont);


        Color panelColor = new Color(15,22,33);

        Image pentisLogo = null;
        try {
            pentisLogo = ImageIO.read(getClass().getResource("pentisGameLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pentisLogo = pentisLogo.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        ImageIcon pentisIcon = new ImageIcon(pentisLogo);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(pentisIcon);
        //imageLabel.setHorizontalAlignment(800);

        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(281+10+10, 10+200+10+200+10, 200, 200);
        logoPanel.setBackground(new Color(44,50,128));
        logoPanel.add(imageLabel);


        // gridPanel location and size
        gridPanel.setBounds(10, 10, 281,673);
        //gridPanel.setLayout(new GridLayout(0, 1));
        gridPanel.setLayout(new BorderLayout());
        gridPanel.add(this);

        highscoresPanel.add(invisHighscoresPanel);
        highscoresPanel.setBackground(panelColor);

        // highscoresPanel location and size
        highscoresPanel.setBounds(10+10+281, 10, 200,200);

        
        scorePanel.setBackground(panelColor);
        // scorePanel location and size
        scorePanel.setBounds(10+10+281,10+10+200,200,200);
        invisHighscoresPanel.setLayout(new GridLayout(6,0));
        invisHighscoresPanel.setBackground(panelColor);
        invisHighscoresPanel.setBounds(10+10+273 + 10, 10 + 10, 180,180);
        //scoreLabel.setAlignmentY(SwingConstants.CENTER);
        scorePanel.add(scoreLabel);

        invisHighscoresPanel.add(highScoresLabel);
        invisHighscoresPanel.add(rank1);
        invisHighscoresPanel.add(rank2);
        invisHighscoresPanel.add(rank3);
        invisHighscoresPanel.add(rank4);
        invisHighscoresPanel.add(rank5);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(44,50,128));

        frame.setLocationRelativeTo(null);
        frame.setTitle("Pentis");
        frame.setLayout(null);
        frame.pack();
        frame.setSize(10+10+10+281+200,720);
        frame.setVisible(true);

        frame.add(gridPanel);
        frame.add(highscoresPanel);
        frame.add(scorePanel);
        frame.add(logoPanel);
       
        hasCreatedDisplay = true;
        setupKeyListener();
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
        localGraphics2D.setColor(new Color(0,0,0));
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

        // while (true) {
        //     score = Pentis.score;

        // }

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
            return new Color(0,102,204); // blue
        } else if (i == 2) {
            return new Color(245,136,34); // orange
        } else if (i == 3) {
            return new Color(23,189,235); // cyan
        } else if (i == 4) {
            return new Color(94,186,71); // green
        } else if (i == 5) {
            return new Color(238,62,88); // magenta
        } else if (i == 6) {
            return new Color(255,102,102); // pinky
        } else if (i == 7) {
            return new Color(204,0,0); // reddy
        } else if (i == 8) {
            return new Color(255,199,34); // yellow
        } else if (i == 9) {
            return new Color(167,62,151); // purple
        } else if (i == 10) {
            return new Color(102, 102, 255);
        } else if (i == 11) {
            return new Color(178, 255, 102);
        } else if (i == 12) {
            return new Color(255, 51, 255);
        } else {
            return new Color(15,22,33);
        }
    }

    /**
     * 
     * @param x matrix rows
     * @param y matrix columns
     * @return square size
     */
    private int CountSquareSize(int x, int y) {
        int xScreenSize = 1920 - 400;
        int yScreenSize = 1080 - 400;

        int squareLength = yScreenSize / x; 
        int squareHeight = xScreenSize / y; 

        // System.out.println(squareHeight);
        // System.out.println(squareLength);

        if (squareHeight > squareLength) {
            return squareLength;
        } else {
            return squareHeight;
        }
    }
    
    private void setupKeyListener(){
        frame.addKeyListener(new Input());
    }

}
>>>>>>> 8801ab492abbdd3ad941c90274520c131974e560

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

    public void updateGrid(int[][] matrixToDisplay) {

        //score = Pentis.score;
        scoreLabel.setText("Score: " + Integer.toString(Pentis.score));
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

        Image pentisLogo = null;
        try {
            pentisLogo = ImageIO.read(getClass().getResource("pentisGameLogo.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pentisLogo = pentisLogo.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        ImageIcon pentisIcon = new ImageIcon(pentisLogo);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(pentisIcon);
        //imageLabel.setHorizontalAlignment(800);

        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(281+10+10, 10+200+10+200+10, 200, 200);
        logoPanel.setBackground(new Color(249,207,221));
        logoPanel.add(imageLabel);


        // gridPanel location and size
        gridPanel.setBounds(10, 10, 281,673);
        //gridPanel.setLayout(new GridLayout(0, 1));
        gridPanel.setLayout(new BorderLayout());
        gridPanel.add(this);

        highscoresPanel.add(invisHighscoresPanel);
        highscoresPanel.setBackground(Color.lightGray);

        // highscoresPanel location and size
        highscoresPanel.setBounds(10+10+281, 10, 200,200);

        
        scorePanel.setBackground(Color.lightGray    );
        // scorePanel location and size
        scorePanel.setBounds(10+10+281,10+10+200,200,200);
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(249,207,221));

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

    private void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
    
}


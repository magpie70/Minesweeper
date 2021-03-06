import javax.swing.*;
import java.awt.event.*;

import java.util.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// 10 - mined
// 11 - revealed
// 12 in queue
// 13 show bomb
// 1-8 bombs
// empty - nothing

public class GUIZ extends JFrame {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;


    public String name1 = JOptionPane.showInputDialog(null,"player 1");
    public String name2 = JOptionPane.showInputDialog(null,"player 2");


    public int level=5;

    public boolean reset = false;

    public int clicks=1;

    public int columns = 10;
    public int rows =10;


    int spacing = 1;
    public int mx = -50;
    public int my = -50;



    public boolean allgood = true;    // variable indicating lost

    public int ResetButtonX = screenWidth / 2;      // variables for reset button
    public int ResetButtonY = 10;

    public int CenterX = ResetButtonX + 25;
    public int CenterY = ResetButtonY + 25;

    public int ScoreboardX1 = screenWidth / 20;       //variables for scoreboards of palyer 1 and 2
    public int ScoreboardX2 = screenWidth - 150;      // Values are dependent on the screen of the computer
    public int ScoreboardY1 = 10;
    public int ScoreboardY2 = 10;

    public boolean win = false;
    public boolean lost = false;

    public int score1=0;
    public int score2=0;

    public int turn = 1;


    Random rand = new Random();

    int[][] gamemap = new int[rows + 2][columns + 2];

    Grid grid;


    public GUIZ() {
        this.setTitle("Minesweeper");
        this.setSize(screenWidth, screenHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

        // initializaiton
        for (int i = 0; i <= rows + 1; ++i) {
            for (int j = 0; j <= columns + 1; ++j) {
                gamemap[i][j] = 0;
            }
        }
        for (int i = 0; i <= columns + 1; ++i) {
            gamemap[0][i] = -1;
            gamemap[rows + 1][i] = -1;
        }
        for (int i = 0; i <= rows + 1; ++i) {
            gamemap[i][0] = -1;
            gamemap[i][columns + 1] = -1;
        }


        // counting neighbours
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (rand.nextInt(100) < level && gamemap[i][j] != -1) { // number of mines on the grid
                    gamemap[i][j] = 10;
                    for (int x = i - 1; x <= i + 1; ++x) {
                        for (int y = j - 1; y <= j + 1; ++y) {
                            if (gamemap[x][y] != 10 && gamemap[x][y] != -1) {
                                gamemap[x][y]++;
                            }
                        }
                    }
                }
            }
        }
        ////////////////////////////////////
//

        grid = new Grid();
        this.setContentPane(grid);

        Position position = new Position();
        this.addMouseMotionListener(position);

        Click click = new Click();
        this.addMouseListener(click);

    }

    public class Grid extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(getImage("background"),0,0,screenWidth,screenHeight,this);
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= columns; j++) {
                    g.setColor(Color.gray);
                    if (gamemap[i][j] == 11) {
                        g.setColor(Color.white);
                    }
                    if (gamemap[i][j] == 13)
                        g.setColor(Color.red);

                    if (gamemap[i][j] >= 21 && gamemap[i][j] <= 28) {
                        g.setColor(Color.white);
                    }

                    if (mx >= spacing + j * 40 + screenWidth / 3 + 9 && mx < spacing + j * 40 + screenWidth / 3 + 9 + 40 - spacing * 2 && my >= spacing + i * 40 + screenHeight / 4 + 30 && my < spacing + i * 40 + screenHeight / 4 + 30 + 40 - spacing * 2) {
                        g.setColor(Color.lightGray);
                    }
                    g.fillRect(spacing + j * 40 + screenWidth / 3, spacing + i * 40 + screenHeight / 4, 40 - spacing * 2, 40 - spacing * 2);
                    if (gamemap[i][j] >= 21 && gamemap[i][j] <= 28) {
                        if (gamemap[i][j] == 21) {                //Coloring each number
                            g.setColor(Color.blue);
                        } else if (gamemap[i][j] == 22) {
                            g.setColor(Color.green);
                        } else if (gamemap[i][j] == 23) {
                            g.setColor(Color.red);
                        } else if (gamemap[i][j] == 24) {
                            g.setColor(new Color(0, 0, 128));
                        } else if (gamemap[i][j] == 25) {
                            g.setColor(new Color(178, 34, 34));
                        } else if (gamemap[i][j] == 26) {
                            g.setColor(new Color(72, 209, 204));
                        } else if (gamemap[i][j] == 27) {
                            g.setColor(new Color(17, 34, 65));
                        } else if (gamemap[i][j] == 28) {
                            g.setColor(Color.yellow);
                        }
                        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
                        g.drawString(Integer.toString(gamemap[i][j] - 20), j * 40 + screenWidth / 3 + 13, i * 40 + screenHeight / 4 + 30);
                    }

                    //drawing bomb inside revealed eell

                    if (gamemap[i][j] == 13) {
                        g.drawImage(getImage("bomb"), j * 40 + screenWidth / 3 + 2, spacing + i * 40 + screenHeight / 4, 40 - spacing * 2, 40 - spacing * 2, this);
                    }

                    //drawing flag inside revealed cell
                    if (gamemap[i][j] == 17) {
                        g.drawImage(getImage("flag"), j * 40 + screenWidth / 3 + 2, spacing + i * 40 + screenHeight / 4, 40 - spacing * 2, 40 - spacing * 2, this);
                    }

                    //unflagging cell
                    if (gamemap[i][j] == 18) {
                        g.setColor(Color.gray);
                    }
                }
            }


            if (allgood == true) {
                g.drawImage(getImage("happy1"),ResetButtonX, ResetButtonY, 60, 60,this);
            } else {
                g.drawImage(getImage("sad1"),ResetButtonX, ResetButtonY, 60, 60,this);
            }


            //Creating scoreboards for player1 and player2

            g.setColor(Color.white);
            g.fillRect(ScoreboardX1, ScoreboardY1, 70, 30);
            g.setColor(Color.black);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString(Integer.toString(score1), ScoreboardX1, ScoreboardY1 + 22);
            g.setColor(Color.white);
            g.fillRect(ScoreboardX2, ScoreboardY2, 70, 30);
            g.setColor(Color.black);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString(Integer.toString(score2), ScoreboardX2, ScoreboardY1 + 22);

            g.setColor(new Color(255,64,64));
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString( name1 , ScoreboardX1, ScoreboardY1 + 60);

            g.setColor(new Color(255,64,64));
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString( name2 , ScoreboardX2, ScoreboardY1 + 60);



            //Creating message box
            g.setColor(Color.WHITE);
            g.fillRect(ResetButtonX-250,10, 220,50);

            if (win == true && lost == false) {
                if (score1 > score2) {
                    g.setColor(Color.green);
                    g.setFont(new Font("Times New Roman", Font.BOLD, 23));
                    g.drawString(name1 + "  wins!!", ResetButtonX - 250, 45);
                } else {
                    g.setColor(Color.green);
                    g.setFont(new Font("Times New Roman", Font.BOLD, 23));
                    g.drawString(name2+"  wins!!", ResetButtonX - 250, 45);
                }
            }
            if(turn%2==0){
                if (win == false && lost == true) {
                    g.setColor(Color.green);
                    g.setFont(new Font("Times New Roman", Font.BOLD, 23));
                    g.drawString(name2 + "  wins!!", ResetButtonX - 250, 45);
                }
            }
            else{
                if (win == false && lost == true) {
                    g.setColor(Color.green);
                    g.setFont(new Font("Times New Roman", Font.BOLD, 23));
                    g.drawString(name1 + "  wins!!", ResetButtonX - 250, 45);
                }
            }
        }
    }

    public class Position implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
        }
    }

    private void exploreCells(int i, int j) {
        if (gamemap[i][j] == 10) {
            gamemap[i][j] = 13;
            return;
        }
        if(!(gamemap[i][j]==11||gamemap[i][j] == 21 || gamemap[i][j] == 22 || gamemap[i][j] == 23 || gamemap[i][j] == 24 || gamemap[i][j] == 25 || gamemap[i][j] == 26 || gamemap[i][j] == 27 || gamemap[i][j] == 28)) {
            if (turn % 2 == 0)
                score1++;

            if (turn % 2 == 1)
                score2++;
        }

        if (gamemap[i][j] >= 1 && gamemap[i][j] <= 8) {
            gamemap[i][j] += 20;
            return;
        }

        if(gamemap[i][j] == 21 || gamemap[i][j] == 22 || gamemap[i][j] == 23 || gamemap[i][j] == 24 || gamemap[i][j] == 25 || gamemap[i][j] == 26 || gamemap[i][j] == 27 || gamemap[i][j] == 28){
            return;
        }
        if(gamemap[i][j]==13){
            return;
        }

        gamemap[i][j] = 11;

        for (int x = i - 1; x <= i + 1; ++x) {
            for (int y = j - 1; y <= j + 1; ++y) {
                if (gamemap[x][y] == 0) {
                    gamemap[x][y] = 12;
                    exploreCells(x, y);
                }
                if (gamemap[x][y] >= 1 && gamemap[x][y] <= 8) {
                    gamemap[x][y] += 20;
                    if(!(gamemap[i][j]==11||gamemap[i][j] == 21 || gamemap[i][j] == 22 || gamemap[i][j] == 23 || gamemap[i][j] == 24 || gamemap[i][j] == 25 || gamemap[i][j] == 26 || gamemap[i][j] == 27 || gamemap[i][j] == 28)) {
                        if (turn % 2 == 0)
                            score1++;

                        if (turn % 2 == 1)
                            score2++;
                    }
                }

            }
        }
        grid.invalidate();
        return;
    }


    public class Click implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON1) {
                turn++;
                if (BoxX() != -1 && BoxY() != -1) {
                    exploreCells(BoxX(), BoxY());
                }

                if (pressedReset() == true) {
                    resetGame();
                    turn=1;
                    score1=0;
                    score2=0;
                    clicks=1;
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                clicks ++;
                if(clicks%2==0) {
                    flag(BoxX(), BoxY());
                }
                else{
                    unflag(BoxX(), BoxY());
                }
            }

            mx = e.getX();
            my = e.getY();

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }


    }



    private int BoxY() {
        for (int j = 1; j <= columns; j++) {
            if (mx >= spacing + j * 40 + screenWidth/3 + 9 && mx < spacing + j * 40 + screenWidth/3 + 9 + 40 - spacing * 2) {
                return j;
            }
        }

        return -1;
    }

    private int BoxX() {
        for (int i = 1; i <= rows; ++i) {
            if (my >= spacing + i * 40 + screenHeight/4 + 30 && my < spacing + i * 40 + screenHeight/4 + 30 + 40 - spacing * 2) {
                return i;
            }
        }
        return -1;
    }


    private void resetGame() { // Reset method to start the game from the beginning
        reset=true;
        allgood = true;
        win = false;
        lost = false;

        for (int i = 0; i <= rows + 1; ++i) {
            for (int j = 0; j <= columns + 1; ++j) {
                gamemap[i][j] = 0;
            }
        }
        for (int i = 0; i <= columns + 1; ++i) {
            gamemap[0][i] = -1;
            gamemap[rows + 1][i] = -1;
        }
        for (int i = 0; i <= rows + 1; ++i) {
            gamemap[i][0] = -1;
            gamemap[i][columns + 1] = -1;
        }


        // counting neighbours
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (rand.nextInt(100) < level && gamemap[i][j] != -1) { // number of mines on the grid
                    gamemap[i][j] = 10;
                    for (int x = i - 1; x <= i + 1; ++x) {
                        for (int y = j - 1; y <= j + 1; ++y) {
                            if (gamemap[x][y] != 10 && gamemap[x][y] != -1) {
                                gamemap[x][y]++;
                            }
                        }
                    }
                }
            }
        }
        reset=false;
    }

    private boolean pressedReset() { // method to recognize whenever the mouse is on smile
        int c = (int) Math.sqrt(Math.abs(mx - CenterX) * Math.abs(mx - CenterX) + Math.abs(my - CenterY) * Math.abs(my - CenterY));
        if (c < 55) {
            return true;
        }

        return false;
    }

    public void StatusOfVictory() {
        for (int i = 1; i <= rows ; i++) {
            for (int j = 1; j <= columns ; j++) {
                if(gamemap[i][j]==13){
                    allgood= false;
                    lost=true;
                }
            }
        }
        if(sumOfRevealedCells()>=columns*rows - sumOfMines()){
            win=true;
        }
    }

    private int sumOfMines() {
        int sum = 0;
        for (int i = 1; i <=rows; i++) {
            for (int j = 1; j <=columns; j++) {
                if (gamemap[i][j] == 10) {
                    sum++;
                }
            }
        }
        return sum;
    }
    private int sumOfRevealedCells(){
        int sum=0;
        for (int i = 1; i <=rows; i++) {
            for (int j = 1; j <=columns; j++) {
                if (gamemap[i][j] == 11 || gamemap[i][j] == 21 || gamemap[i][j] == 22 || gamemap[i][j] == 23 || gamemap[i][j] == 24 || gamemap[i][j] == 25 || gamemap[i][j] == 26 || gamemap[i][j] == 27 || gamemap[i][j] == 28|| gamemap[i][j]==17) {
                    sum ++;
                }
            }
        }
        return sum;
    }

    private Image getImage(String name){
        String filename = "images/" + name.toLowerCase()+".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private void flag(int i, int j){
        gamemap[i][j]=17;
        if(gamemap[i][j]==10 && gamemap[i][j]==17) {
            gamemap[i][j] = 13;
        }
    }

    private void unflag(int i, int j){
        gamemap[i][j]=18;
    }


}
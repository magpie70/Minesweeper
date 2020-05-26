import java.util.*;
public class Game {

    //field is main layer wher bobms are present and display is visible for us
    int[][] Field;
    Object[][] Display;

    int x;//row
    int y;//column
    int b;//bomb
    boolean process = true;




    public Game(int rows, int columns, int bombs) {
        x = rows;
        y = columns;
        b = bombs;
        Field = new int[x][y];
        Display = new Object[x][y];
    }

    //places bombs on field and mark it as 10
    public void CreateBombs() {
        Random rand = new Random();
        int temp = 0;
        while (temp < b) {
            int mx = rand.nextInt(x);
            int my = rand.nextInt(y);
            if (this.Field[mx][my] != 9) {
                this.Field[mx][my] = 9;
                temp++;
            }
        }
    }

    //fills field with values of nearby bombs
    public void NumberofBombs() {
        for (int i = 0; i < Field.length; i++) {
            for (int j = 0; j < Field[0].length; j++) {
                if (Field[i][j] != 9) {
                    int bombcount = 0;
                    //check spot above
                    if (j > 0) {
                        if (Field[i][j - 1] == 9)
                            bombcount++;
                    }
                    //check spot below
                    if (j <Field[0].length - 1) {
                        if (Field[i][j + 1] == 9)
                            bombcount++;
                    }
                    //check spot to the right
                    if (i <Field.length - 1) {
                        if (Field[i + 1][j] == 9)
                            bombcount++;
                    }
                    //check spot to the left
                    if (i > 0) {
                        if (Field[i- 1][j] == 9)
                            bombcount++;
                    }
                    //check spot upper left
                    if (i > 1 && j > 1) {
                        if (Field[i - 1][j - 1] == 9)
                            bombcount++;
                    }
                    //check spot upper right
                    if (j >0 && i <Field.length - 1) {
                        if (Field[i + 1][j - 1] == 9)
                            bombcount++;
                    }
                    //check spot bottom left
                    if (j <Field[0].length - 1 && i> 0) {
                        if (Field[i - 1][j + 1] == 9)
                            bombcount++;
                    }
                    //check spot bottom right
                    if (j <Field[0].length - 1 && i <Field.length - 1) {
                        if (Field[i + 1][j + 1] == 9)
                            bombcount++;
                    }
                    Field[i][j] = bombcount;

                }
            }
        }


    }

    //fills field
    public void fillVisible() {
        for (int i = 0; i < Display.length; i++) {
            for (int j = 0; j <Display[0].length; j++)
                Display[i][j] = '?';
        }
    }

    //create dispaly
    public void printVisible() {
        for (int i = 0; i <Display.length; i++) {
            for (int j = 0; j < Display[0].length; j++)
                System.out.print(Display[i][j] + " ");
            System.out.println();
        }
    }

    public void makeGuess(int x, int y) {
        if (Field[x][y] == 9) {
            process= false;

        } else if (Field[x][y] == 0)
            this.Openbox(x, y);
        else
            Display[x][y] = Field[x][y];

    }

    public void Openbox(int x, int y) {
        Display[x][y] = " ";
        if (y > 0) {
            if ((Field[x][y - 1] == 0 && (Display[x][y - 1].equals('?')) || (Field[x][y - 1] != 0)))
                this.makeGuess(x, y- 1);
        }
        //check spot below
        if (y < Field[0].length - 1) {
            if ((Field[x][y+ 1] == 0 && (Display[x][y+ 1].equals('?')) || (Field[x][y + 1] != 0)))
                this.makeGuess(x, y + 1);
        }
        if (y > 0 && x> 0) {
            if ((Field[x- 1][y - 1] == 0 && (Display[x- 1][y - 1].equals('?')) || (Field[x - 1][y - 1] != 0)))
                this.makeGuess(x - 1, y - 1);
        }
        //check spot to the right
        if (y > 0 && x < Field.length - 1) {
            if ((Field[x + 1][y - 1] == 0 && (Display[x + 1][y - 1].equals('?')) || (Field[x + 1][y - 1] != 0)))
                this.makeGuess(x+ 1, y- 1);
            if (y < Field[0].length - 1 && x> 0) {
                if ((Field[x - 1][y + 1] == 0 && (Display[x - 1][y+ 1].equals('-')) || (Field[x- 1][y+ 1] != 0)))
                    this.makeGuess(x - 1, y + 1);
            }
            //check spot bottom right
            if (y< Field[0].length - 1 && x < Field.length - 1) {
                if ((Field[x + 1][y + 1] == 0 && (Display[x+ 1][y+ 1].equals('?')) || (Field[x + 1][y + 1] != 0)))
                    this.makeGuess(x + 1, y + 1);
            }

        }
        if (x< Field.length - 1) {
            if ((Field[x+ 1][y] == 0 && (Display[x + 1][y].equals('?')) || (Field[x + 1][y] != 0)))
                this.makeGuess(x+ 1, y);
        }
        //check spot to the left
        if (x > 0) {
            if ((Field[x - 1][y] == 0 && (Display[x - 1][y].equals('?')) || (Field[x - 1][y] != 0)))
                this.makeGuess(x- 1, y);
        }
    }


    public boolean WinCondition() {
        for (int countrow = 0; countrow < Display.length; countrow++) {
            for (int countcolumn = 0; countcolumn < Display[0].length; countcolumn++) {
                if (Display[countrow][countcolumn].equals('?') && Field[countrow][countcolumn] != 9)
                    return false;

            }
        }
        return true;
    }

}

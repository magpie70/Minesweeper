import java.util.*;
public class GameRunner {

    public static void main(String[] args) {
        Scanner OneMoreChanse = new Scanner(System.in);
        int rows = 9;
        int bombs = 10;
        int columns = 9;
        int guessrow;
        int guesscolumn;
        String var = "";
        Boolean isCon=true;
        while (isCon) {
            Game g = new Game(rows, columns, bombs);
            g.CreateBombs();
            g.NumberofBombs();
            g.fillVisible();
            g.printVisible();
            while (g.process == true) {
                try{
                    System.out.println("Enter your move (row[1-9] column[1-9]): ");
                    guessrow = OneMoreChanse.nextInt();
                    guesscolumn = OneMoreChanse.nextInt();
                    g.makeGuess(guessrow-1, guesscolumn-1);
                    g.printVisible();

                    if (g.WinCondition() == true) {
                        System.out.println("You win. Congrats!");
                        g.process= false;
                    }}catch (Exception e){
                    System.out.println("invalid number try again!!");
                }
            }
            System.out.println("Oops! You lose. Would you like to play again? (Y/N):");
            var = OneMoreChanse.next();
            if(var.equals("Y")||var.equals("Yes"))
                isCon=true;
            else
                isCon=false;
        }
    }

}
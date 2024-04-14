// Imports
// -----------------------------------------------------------------------------------
import java.util.Scanner;
// -----------------------------------------------------------------------------------

class Main
{
    public static void main(String[] args) throws Exception
    {
        // Variables
        // -----------------------------------------------------------------------------------
        boolean solve, play;
        String input = "";

        Board board;
        Scanner keyboard = new Scanner(System.in);
        // -----------------------------------------------------------------------------------

        while (true)
        {
            play = false;
            solve = false;
            
            do
            {
                board = new Board(); // Creates a Sudoku Board instance
                board.generate(); // Generates a valid, solvable Sudoku Board
                board.display(); // Displays the Board to user

                // Takes user input based on given options
                // -----------------------------------------------------------------------------------
                while (!(input.equals("p")||input.equals("s")||input.equals("g")))
                {
                    System.out.print("\nPlay, Solve, or Re-Generate? [p/s/g]: ");
                    input = keyboard.nextLine();
    
                    switch (input)
                    {
                        case "p": // Play
                            play = true;
                            break;
                        case "s": // Solve
                            solve = true;
                            break;
                        case "g": // Regenerate
                            break;
                        default:
                            System.out.println("Invalid input.");
                    }
                }
    
                input = "";
                
            } while (!(solve)&&!(play));

            if (play)
            {
                int n, x, y;
                
                Playable p = new Playable(board); // Create a new Playable Sudoku object

                while ((p.isComplete())&&(p.getTries()>0))
                {
                    p.display(); // Displays Unique Playable Board to User

                    // Takes in user-inputed x and y values based on the board
                    // -----------------------------------------------------------------------------------
                    System.out.print("\nEnter X-index on the Board: ");
                    x = keyboard.nextInt();

                    System.out.print("\nEnter Y-index on the Board: ");
                    y = keyboard.nextInt();

                    // User options based on entered values
                    if ((x==0)&&(y==0))
                    {
                        break;
                    }
                    else if ((x==10)&&(y==10))
                    {
                        p.xcomplete();
                        p.display();

                        Thread.sleep(3000);

                        break;
                    }
                    
                    System.out.print("\nEnter a number to place: ");
                    n = keyboard.nextInt();
                    // -----------------------------------------------------------------------------------

                    p.place(n,x,y); // Places the value on the board and checks if it is valid.

                    Thread.sleep(2000);
                    
                }

                p.display();

                System.out.println("\nSudoku Board Complete.");
            }
            else
            {
                Sudoku s = new Sudoku(board.getBoard()); // Creates new Sudoku instance with generated board
                s.complete(true); // Solves the loaded board
    
                Thread.sleep(3000);
            }
        }
        
        // -----------------------------------------------------------------------------------

        // Example Board
        // {{0,0,0,2,6,0,7,0,1},
        // {6,8,0,0,7,0,0,9,0},
        // {1,9,0,0,0,4,5,0,0},
        // {8,2,0,1,0,0,0,4,0},
        // {0,0,4,6,0,2,9,0,0},
        // {0,5,0,0,0,3,0,2,8},
        // {0,0,9,3,0,0,0,7,4},
        // {0,4,0,0,5,0,0,3,6},
        // {7,0,3,0,1,8,0,0,0}}
    }
}
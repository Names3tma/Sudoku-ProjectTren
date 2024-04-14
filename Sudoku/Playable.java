    // This Class allows the user to interact with
// the Board to play Sudoku. It uses methods from
// the Sudoku class to work.

// Imports
// ---------------------------------------------------------------------------------------
// * No Imports Needed
// ---------------------------------------------------------------------------------------

// -----------------------------------------------------------------------------------
class Playable
{
    // Variables
    // -----------------------------------------------------------------------------------
    private int[][] playableBoard; // Array for user-interaction
    private final int[][] answerBoard; // Array for input correction

    private int currentx = 0;
    private int currenty = 0;

    private static final String WHITE = "\u001B[37m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED =  "\033[1;31m";
    private static final String GREENBG = "\u001B[42m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    private int attempts = 5;
    // -----------------------------------------------------------------------------------

    // Class Constructor
    // -----------------------------------------------------------------------------------
    public Playable(Board b) throws Exception
    {
        playableBoard = b.getBoard(); // Initalizes variables with base Sudoku board

        answerBoard = (new Sudoku(b.getBoard()).complete(false)); // Solves the base Sudoku board and
        //                                                           initializes the variables with the
        //                                                           solved Sudoku board.
    }
    // -----------------------------------------------------------------------------------

    // Complete Method
    // -----------------------------------------------------------------------------------
    public void xcomplete()
    {
        this.playableBoard = this.answerBoard;
    }
    // -----------------------------------------------------------------------------------
    
    // User Place Method
    // -----------------------------------------------------------------------------------
    public void place(int n, int x, int y)
    {
        // Fixes user-inputed index values
        x--;
        y--;

        currentx = x;
        currenty = y;

        // Checks if inputed value is correct at desired indicies.
        if (isValid(n,x,y))
        {
            this.playableBoard[x][y] = n;
            
            System.out.println("Valid placement.");
        }
        else
        {
            System.out.println("Invalid placement.");
            attempts--;
        }
    }
    // -----------------------------------------------------------------------------------

    // IsValid Method
    // -----------------------------------------------------------------------------------
    public boolean isValid(int n, int x, int y)
    {
        if (n == this.answerBoard[x][y])
        {
            return true;
        }
        
        return false;
    }
    // -----------------------------------------------------------------------------------

    // IsComplete Method
    // -----------------------------------------------------------------------------------
    public boolean isComplete()
    {
        for (int x=0;x<9;x++)
        {
            for (int y=0;y<9;y++)
            {
                if (this.playableBoard[x][y]!=0)
                {
                    return true;
                }
            }
        }

        return false;
    }
    // -----------------------------------------------------------------------------------

    // Tries Accessor
    // -----------------------------------------------------------------------------------
    public int getTries()
    {
        return attempts;
    }
    // -----------------------------------------------------------------------------------

    // Clear Console
    // -----------------------------------------------------------------------------------
    public void clear()
    {
        System.out.print("\033[H\033[2J");
    }
    // -----------------------------------------------------------------------------------

    // Contains Method
    // -----------------------------------------------------------------------------------
    public boolean contains(int[][] array, int target)
    {
        for (int x=0;x<9;x++)
        {
            for (int y=0;y<9;y++)
            {
                if (array[x][y]==target)
                {
                    return true;
                }
            }
        }

        return false;
    }
    // -----------------------------------------------------------------------------------

    // Unique Board Display
    // -----------------------------------------------------------------------------------
    public void display()
    {
        clear();

        System.out.println("\t\t\tAttempts Left: " + attempts + "\n\n     1  2  3     4  5  6     7  8  9\n  ___________________________________");

        for (int y=0;y<9;y++)
        {
            if (y%3==0 && y!=0)
            {
                System.out.println("   ----------------------------------");
            }
            
            System.out.print((y+1)+"  |");
            
            for (int x=0;x<9;x++)
            {
                if (x%3==0 && x!=0)
                {
                    System.out.print(" | ");
                }

                if (playableBoard[x][y]==0)
                {
                    System.out.print(" " + RED  + playableBoard[x][y] + RESET + " ");
                }
                else if (!(contains(playableBoard,0)))
                {
                    System.out.print(" " + GREEN  + playableBoard[x][y] + RESET + " ");
                }
                else if (currentx==x && currenty==y)
                {
                    System.out.print(" " + GREENBG + WHITE + playableBoard[x][y] + RESET + " ");
                }
                else
                {
                    System.out.print(" " + YELLOW + playableBoard[x][y] + RESET + " ");
                }
            }

            System.out.println();
        }

        System.out.println("_____________________________________\nEnter [0,0] To Exit.\nEnter [10,10] To Auto-Complete");
    }
    // -----------------------------------------------------------------------------------
}
// 
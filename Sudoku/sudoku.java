// Rules to Mention of Sudoku
// o There cannot be identical numbers in the same 1x1 row along the x and y-axis.
// o There cannot be identical numbers in the same 3x3 square.
// o There must be one of each number in all 9 rows in different 1x1 units, going up and down.

// Needed Methods:
// o Method 1: onX() [ Is this number already on the x-axis? ]
// o Method 2: onY() [ Is this number already on the y-axis? ]
// o Method 3: inBox() [ Is this number already inside its respective 3x3? ]
// o Method 4: valid() [ Does this number meet all the requirements? ]
// o Method 5: solve() [ Call to solve board. ]
// o Method 6: display() [ Display the board to console. ]

// Algorithms needed:
// o Back-tracking method
//   -> Recusrively form a solution to a problem one step at a time that meets the conditions
//   -> Recursion allow the algorithm to "back-trace" to a valid solution if a certain solution
//      fails to meet conditions.

// Imports
// ---------------------------------------------------------------------------------------
// * No Imports Needed
// ---------------------------------------------------------------------------------------

class Sudoku
{

    // Private Variables
    // -----------------------------------------------------------------------------------
    private int[][] board;

    private int steps = 0;

    private int currentx = 0;
    private int currenty = 0;

    private static final String WHITE = "\u001B[37m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\033[1;31m";
    private static final String RESET = "\u001B[0m";
    private static final String GREENBG = "\u001B[42m";
    // -----------------------------------------------------------------------------------

    // Sudoku Constructor #!
    // -----------------------------------------------------------------------------------
    public Sudoku()
    {
        this.board = new int[9][9];
    }
    // -----------------------------------------------------------------------------------
    
    // Sudoku Constructor #2
    // -----------------------------------------------------------------------------------
    public Sudoku(int[][] board)
    {
        this.board = board;
    }
    // -----------------------------------------------------------------------------------

    // SetBoard Method
    // -----------------------------------------------------------------------------------
    public void setBoard(int[][] board)
    {
        this.board = board;
    }
    // -----------------------------------------------------------------------------------

    // CopyBoard Method
    // This algorithm sequences two initialized arrays, moving the content from one array to the other.
    // -----------------------------------------------------------------------------------
    private int[][] copyBoard(int[][] base, int[][] copy)
    {
        for (int y = 0; y<base.length;y++)
        {
            for (int x = 0; x<base.length;x++)
            {
                base[x][y] = copy[x][y];
            }
        }

        return base;
    }


    // -----------------------------------------------------------------------------------
    
    // GetBoard Method
    // -----------------------------------------------------------------------------------
    public int[][] getBoard()
    {
        return board;
    }
    // -----------------------------------------------------------------------------------
    
    // Complete Method
    // This method is a sequence of code that takes the total time it took
    // for the full recursive solve() method to complete, and satisfy
    // the user-interface.
    // -----------------------------------------------------------------------------------
    public int[][] complete(boolean r)
    {
        int[][] returnBoard = new int[9][9];
        
        long s = System.currentTimeMillis();
        
        solve(r);
        
        returnBoard = copyBoard(returnBoard, board);
        
        copyBoard(returnBoard, board);

        long f = System.currentTimeMillis();

        long t = f-s;

        display();

        if (contains(returnBoard, 0))
        {
            System.out.println("Error: Board not solvable.");
        }
        else
        {
            System.out.println("Solved in: " + this.steps + " steps.\nTook "+((float)t/1000)+" Seconds to solve.");
        }

        return returnBoard;
    }
    // -----------------------------------------------------------------------------------
    
    // Clear Console
    // -----------------------------------------------------------------------------------
    public void clear()
    {
        System.out.print("\033[H\033[2J");
    }
    // -----------------------------------------------------------------------------------
    
    // Unique Board Display
    // -----------------------------------------------------------------------------------
    public void display()
    {
        clear();

        if (contains(this.board, 0))
        {
            System.out.println("\nSolving...\n_________________________________\n");
        }
        else
        {
            System.out.println("\nSolved!\n_________________________________\n");
        }
        
        for (int y=0;y<9;y++)
        {
            if (y%3==0 && y!=0)
            {
                System.out.println("----------------------------------");
            }

            for (int x=0;x<9;x++)
            {
                if (x%3==0 && x!=0)
                {
                    System.out.print(" | ");
                }

                if (this.board[x][y]==0)
                {
                    System.out.print(" " + RED  + this.board[x][y] + RESET + " ");
                }
                else if (!(contains(this.board,0)))
                {
                    System.out.print(" " + GREEN  + this.board[x][y] + RESET + " ");
                }
                else if (currentx==x && currenty==y)
                {
                    System.out.print(" " + GREENBG + WHITE + this.board[x][y] + RESET + " ");
                }
                else
                {
                    System.out.print(" " + YELLOW + this.board[x][y] + RESET + " ");
                }
            }

            System.out.println();
        }

        System.out.println("_________________________________");
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

    // Validate Method
    // This method calls all three conditional methods to evaluate the given target.
    // -----------------------------------------------------------------------------------
    private boolean valid(int x, int y, int target)
    {
        return !(inBox(x,y,target)||onX(x,target)||onY(y,target));
    }
    // -----------------------------------------------------------------------------------

    // Solve Sudoku Method (Recursive)
    // The Sudoku Program uses back-tracking recursion to find values in each index that
    // satisfies the conditions, or rules of Sudoku, to create a completely solved Sudoku board.
    // -----------------------------------------------------------------------------------
    private boolean solve(boolean r)
    {
        // Display option
        if (r)
        {
            display();
        }
        
        this.steps++; // Count every time method is called (steps).
        
        for (int y=0;y<9;y++)
        {
            for (int x=0;x<9;x++) // Iterate over the array from left-to-right
            {
                // Base Case
                if (this.board[x][y]==0) // If the target equals 0 (Not solved);
                {
                    this.currentx = x;
                    this.currenty = y;
                    
                    for (int i=1;i<=9;i++) // Go through all possible values and see which one satisfies.
                    {
                        if (valid(x,y,i)) // If target value (i) meets conditions:
                        {
                            this.board[x][y] = i; // Solved

                            if (solve(r)) // Iterate again through array to check if all the values are still satisfied.
                            {
                                return true;
                            }
                            else // Otherwise, set the current index value to 0 (Not solved)
                            {
                                this.board[x][y] = 0;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }
    // -----------------------------------------------------------------------------------
    
    // onX Method
    // -----------------------------------------------------------------------------------
    private boolean onX(int x, int target)
    {
        for (int i=0;i<9;i++)
        {
            if (this.board[x][i]==target)
            {
                return true;
            }
        }

        return false;
    }
    // -----------------------------------------------------------------------------------

    // onY Method
    // -----------------------------------------------------------------------------------
    private boolean onY(int y, int target)
    {
        for (int i=0;i<9;i++)
        {
            if (this.board[i][y]==target)
            {
                return true;
            }
        }

        return false;
    }
    // -----------------------------------------------------------------------------------

    // inBox Method
    // -----------------------------------------------------------------------------------
    private boolean inBox(int x, int y, int target)
    {
        int xx = x-x%3;
        int yy = y-y%3;

        for (int i=xx;i<xx+3;i++)
        {
            for (int k=yy;k<yy+3;k++)
            {
                if (this.board[i][k]==target)
                {
                    return true;
                }
            }
        }

        return false;
    }
    // -----------------------------------------------------------------------------------
}
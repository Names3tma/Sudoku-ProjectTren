// This class is responsible for generating and scrapping a valid Sudoku
// board from https://www.sudokuweb.org/ that can be used inside of the Sudoku class.

// Imports
// ---------------------------------------------------------------------------------------
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
// ---------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------
class Board
{
    // Private Variables
    // ---------------------------------------------------------------------------------------
    private int[][] board = new int[9][9];
    
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\033[1;31m";
    private static final String RESET = "\u001B[0m";
    // -----------------------------------------------------------------------------------


    // Clear Console Accessor
    // -----------------------------------------------------------------------------------
    public int[][] getBoard()
    {
        int[][] returnBoard = new int[9][9];

        for (int y = 0; y<returnBoard.length;y++)
        {
            for (int x = 0; x<returnBoard.length;x++)
            {
                returnBoard[x][y] = board[x][y];
            }
        }
        
        return returnBoard;
    }
    // -----------------------------------------------------------------------------------

    // Generate Board Method
    // ---------------------------------------------------------------------------------------
    public void generate()
    {   
        int l = 9;
        
        int[] elements = new int[l*l]; // Create a primitive list to store recieved values
        
        try
        {
            String path = "#right";
            int n = 0;
            
            Document document = Jsoup.connect("https://www.sudokuweb.org/").get(); // Use API to connect to website

            // This algorithm sources the website for specific elements that contain
            // the values to 'generate' the Sudoku board.
            // -----------------------------------------------------------------------------------
            do
            {
                path = "#right";
                
                if (n != 0)
                {
                    path = path+n;
                }

                // "#right[n] > span.true");
                Elements element = document.select(path+" > span.true");

                // Check if recieved element meets requirements
                if (!(element.html().equals("")) && !(element.html().equals("&nbsp;")))
                {
                    elements[n] = Integer.parseInt((element.html()));
                }
                else
                {
                    elements[n] = 0;
                }

                n++;
                
            } while (n<l*l);

            n = 0;

            // Source elements into a primitive integer array
            for (int x=0; x<l; x++)
            {
                for (int y=0; y<l; y++)
                {
                    this.board[x][y] = elements[n];
                    n++;
                    display();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    // -----------------------------------------------------------------------------------

    // Clear Console
    // -----------------------------------------------------------------------------------
    public void clear()
    {
        System.out.print("\033[H\033[2J");
    }
    // -----------------------------------------------------------------------------------
    
    // Unique Board Display Method
    // -----------------------------------------------------------------------------------
    public void display()
    {
        clear();
        
        System.out.println("\nGenerating Board...\n_________________________________\n");
        
        for (int y=0;y<9;y++)
        {
            if (y%3==0 && y!=0)
            {
                System.out.println("---------------------------------");
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
}
import java.util.*;
/**
 * Board for Checkers
 */
public class Board  
{
    //The Board class contains mainly the 2D array of Spaces that is the abstract
    //board of the game. It records the number of pieces of each side and it knows
    //its diagonal lines
    
    //it contains a method that moves one abstract piece from one abstract
    //space to another and checks whether there is a multiple capture available, or 
    //a crowning to be king
    private Space[][] spaces = new Space[8][8];
    private ArrayList<Line> lines = new ArrayList<Line>();
    private int numBlackPiece = 12;
    private int numWhitePiece = 12;

    /**
     * Constructor for objects of class Board
     */
    public Board(Space[][] spaces)
    {
        //constructor receives the 2D array of spaces and the arraylist of lines
        this.spaces=spaces;
        this.lines=setLines(spaces);
    }
    
    //accessors
    public ArrayList<Line> getLines()
    {
        return lines;
    }
    
    public Space[][] getSpaces()
    {
        return spaces;
    }
    
    //prints the board
    public String toString()
    {
        for(int i = 0; i < spaces.length; i++)
        {
            for(int o = 0; o < spaces[0].length; o++)
            {
                if(spaces[i][o].isEmpty())
                {   
                    System.out.print("[     ]");
                }
                else
                {
                    System.out.print("[" + spaces[i][o].getPiece().toString() + "]");
                }
            }
            System.out.println();
        }
        return "";
    }
    
    //try moving piece from startspace to endspace, returns a state
    public int tryToMovePiece(Space startSpace, Space endSpace)
    {
        /**
         * return values
         *  -1 = invalid Piece (either not available or invalid)
         *  -2 = invalid Space (either not empty or invalid)
         *  1 = all good
         *  2 = only captured a piece
         *  3 = only crowned king
         *  4 = both captured a piece and became a king
         */
        if(startSpace.isEmpty())
        {
            System.out.println("Invalid piece to move at: " + startSpace.toString());
            return -1;
        }
        ArrayList<Space> spacesToMove = new ArrayList<Space>();
        int currentRow = startSpace.getRow();
        int currentCol = startSpace.getCol();
        /** assinging movement limitations
         * 1 (White) = can only move forward(up the board)
         * 2 (Black) = can only move backward(down the board)
         * 3 (King) = can move both ways
         */
        int movement = -1;
        if(startSpace.getPiece().isBlack())
        {movement = 2;}
        else
        {movement = 1;}
        if(startSpace.getPiece().isKing())
        {movement = 3;}
        
        //retrieving the lines that the space is on
        ArrayList<Line> myLines = startSpace.getMyLines();
        for(Line line:myLines)
        {
            if(movement == 2||movement == 3) //black piece can only move downwards
            {
                if(line.availableLowerSpace(startSpace) != null)
                {
                    spacesToMove.add(line.availableLowerSpace(startSpace));
                }
            }
            if(movement == 1 || movement == 3) //white piece can only move up
            {
                if(line.availableUpperSpace(startSpace) != null)
                {
                    spacesToMove.add(line.availableUpperSpace(startSpace));
                }
            }
        }
        
        //if the player wishes to move to a legit space, then check capture and move
        System.out.println("Available spaces for " + startSpace.getPiece().toString() + " piece at " + startSpace.toString() + ": " + spacesToMove.toString());
        if(spacesToMove.contains(endSpace))
        {
            System.out.println("Moving piece from " + startSpace.toString() + " to " + endSpace.toString() + "\n");
            startSpace.getPiece().inSpace(endSpace);
            //checking if there is any capture spaces
            if(Math.abs(endSpace.getRow() - startSpace.getRow()) == 2)
            {
                Space middleSpace = spaces[(endSpace.getRow() + startSpace.getRow()) / 2][(endSpace.getCol() + startSpace.getCol()) / 2];
                System.out.println("*****Capturing a " + middleSpace.getPiece().toString() + " piece at " + middleSpace.toString() + "*****\n");
                Piece removedPiece = middleSpace.removePiece();
                if(removedPiece.isBlack()){numBlackPiece -= 1;}
                else{numWhitePiece -= 1;}
                System.out.println("Number of black pieces left on board: " + numBlackPiece);
                System.out.println("Number of white pieces left on board: " + numWhitePiece);
                endSpace.receivePiece(startSpace.removePiece());
                //check if the piece qualifies to become king
                if((endSpace.getRow() == 0 && !endSpace.getPiece().isBlack()) || (endSpace.getRow() == 7 && endSpace.getPiece().isBlack()))
                {
                    endSpace.getPiece().becomeKing();
                    System.out.println("♕♕♕Crowned " + endSpace.getPiece().toString() + " piece at " + endSpace.toString() + " to King♕♕♕\n");
                    System.out.println(this.toString() + "\n");
                    return 4;
                }
                System.out.println(this.toString() + "\n");
                return 2;
            }
            endSpace.receivePiece(startSpace.removePiece());
            //check if the piece qualifies to become king
            if((endSpace.getRow() == 0 && !endSpace.getPiece().isBlack()) || (endSpace.getRow() == 7 && endSpace.getPiece().isBlack()))
            {
                endSpace.getPiece().becomeKing();
                System.out.println("♕♕♕Crowned " + endSpace.getPiece().toString() + " piece at " + endSpace.toString() + " to King♕♕♕");
                System.out.println(this.toString() + "\n");
                return 3;
            }
            System.out.println(this.toString() + "\n");
            return 1;
        }
        else //the space is not legit to be moved to
        {
            System.out.println("Invalid space to move to: " + endSpace.toString());
            return -2;
        }
    }
    
    //check for win condition: either the opponent can make no moves because 
    //all their pices are blocked or they are all captured
    public boolean checkForWhiteWin()
    {
        if(numBlackPiece == 0){return true;}
        //checking if black can make no moves
        for(int i = 0; i < spaces.length; i++)
        {
            for(int o = 0; o < spaces[0].length; o++)
            {
                if(spaces[i][o].isEmpty() || !spaces[i][o].getPiece().isBlack())
                {
                    continue;
                }
                ArrayList<Space> spacesToMove = new ArrayList<Space>();
                //retrieving the lines that the space is on
                ArrayList<Line> myLines = spaces[i][o].getMyLines();
                int movement = 2;
                if(spaces[i][o].getPiece().isKing())
                {
                    movement = 3;
                }
                for(Line line:myLines)
                {
                    if(movement == 2||movement == 3) //black piece can only move downwards
                    {
                        if(line.availableLowerSpace(spaces[i][o]) != null)
                        {
                            spacesToMove.add(line.availableLowerSpace(spaces[i][o]));
                        }
                    }
                    if(movement == 1 || movement == 3) //white piece can only move up
                    {
                        if(line.availableUpperSpace(spaces[i][o]) != null)
                        {
                            spacesToMove.add(line.availableUpperSpace(spaces[i][o]));
                        }
                    }
                }
                if(spacesToMove.size() > 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    //checking for black win
    public boolean checkForBlackWin()
    {
        if(numWhitePiece == 0){return true;}
        //checking if white can make no moves
        for(int i = 0; i < spaces.length; i++)
        {
            for(int o = 0; o < spaces[0].length; o++)
            {
                if(spaces[i][o].isEmpty() || spaces[i][o].getPiece().isBlack())
                {
                    continue;
                }
                ArrayList<Space> spacesToMove = new ArrayList<Space>();
                //retrieving the lines that the space is on
                ArrayList<Line> myLines = spaces[i][o].getMyLines();
                int movement = 1;
                if(spaces[i][o].getPiece().isKing())
                {
                    movement = 3;
                }
                for(Line line:myLines)
                {
                    if(movement == 2||movement == 3) //black piece can only move downwards
                    {
                        if(line.availableLowerSpace(spaces[i][o]) != null)
                        {
                            spacesToMove.add(line.availableLowerSpace(spaces[i][o]));
                        }
                    }
                    if(movement == 1 || movement == 3) //white piece can only move up
                    {
                        if(line.availableUpperSpace(spaces[i][o]) != null)
                        {
                            spacesToMove.add(line.availableUpperSpace(spaces[i][o]));
                        }
                    }
                }
                if(spacesToMove.size() > 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    //initializing lines and adding attributed lines to space objects
    public ArrayList<Line> setLines(Space[][] spaces) 
    {
        ArrayList<Line> lines = new ArrayList<Line>();
        Space[] twoSpaceLine1 = {spaces[1][0],spaces[0][1]};
        Space[] twoSpaceLine2 = {spaces[7][6],spaces[6][7]};
        Space[] firstLine = {spaces[3][0],spaces[2][1],spaces[1][2],spaces[0][3]};
        Space[] secondLine = {spaces[5][0],spaces[4][1],spaces[3][2],spaces[2][3],spaces[1][4],spaces[0][5]};
        Space[] thirdLine = {spaces[7][0],spaces[6][1],spaces[5][2],spaces[4][3],spaces[3][4],spaces[2][5],spaces[1][6],spaces[0][7]};
        Space[] fourthLine = {spaces[7][2],spaces[6][3],spaces[5][4],spaces[4][5],spaces[3][6],spaces[2][7]};
        Space[] fifthLine = {spaces[7][4],spaces[6][5],spaces[5][6],spaces[4][7]};
        Space[] sixthLine = {spaces[5][0],spaces[6][1],spaces[7][2]};
        Space[] seventhLine = {spaces[3][0],spaces[4][1],spaces[5][2],spaces[6][3],spaces[7][4]};
        Space[] eigthLine = {spaces[1][0],spaces[2][1],spaces[3][2],spaces[4][3],spaces[5][4],spaces[6][5],spaces[7][6]};
        Space[] ninthLine = {spaces[0][1],spaces[1][2],spaces[2][3],spaces[3][4],spaces[4][5],spaces[5][6],spaces[6][7]};
        Space[] tenthLine = {spaces[0][3],spaces[1][4],spaces[2][5],spaces[3][6],spaces[4][7]};
        Space[] eleventhLine = {spaces[0][5],spaces[1][6],spaces[2][7]};
        Line myLine = new Line(firstLine,1);
        lines.add(myLine);
        lines.add(new Line(secondLine,2));
        lines.add(new Line(thirdLine,3));
        lines.add(new Line(fourthLine,4));
        lines.add(new Line(fifthLine,5));
        lines.add(new Line(sixthLine,6));
        lines.add(new Line(seventhLine,7));
        lines.add(new Line(eigthLine,8));
        lines.add(new Line(ninthLine,9));
        lines.add(new Line(tenthLine,10));
        lines.add(new Line(eleventhLine,11));
        lines.add(new Line(twoSpaceLine1,12));
        lines.add(new Line(twoSpaceLine2,13));
        return lines;
    }

    
}

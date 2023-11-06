import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * The checker piece
 */
public class Piece extends Actor
{
    //this is the abstract piece class. It contains information of the piece like
    //isBlack and isKing and isHighlighted, and also it knows which space it is at.
    private boolean isKing;
    private boolean isBlack;
    private Space currentSpace;
    private boolean highlighted;
    /**
     * Constructor for objects of class Piece
     */
    public Piece(boolean isBlack, boolean isKing)
    {
        this.isBlack = isBlack;
        this.isKing = isKing;
    }
    
    //one line accessors and mutators for the instance variables
    public boolean isKing()
    {
        return this.isKing;
    }
    public boolean isBlack()
    {
        return this.isBlack;
    }
    
    public void becomeKing()
    {
        isKing = true;
    }
    
    public void inSpace(Space space)
    {
        currentSpace = space;
    }
    
    public Space mySpace()
    {
        return this.currentSpace;
    }
    
    //this method returns an arraylist of spaces that the piece can capture
    public ArrayList<Space> canCaptureSpaces()
    {
        ArrayList<Space> moves = this.myMoves();
        ArrayList<Space> canCapture = new ArrayList<Space>();
        for(Space space:moves)
        {
            if(Math.abs(space.getRow() - currentSpace.getRow()) == 2)
            {
                canCapture.add(space);
            }
        }
        return canCapture;
    }
    
    //this method returns a boolean to check if the piece has possible moves
    //meaning it isn't blocked or it can capture
    public boolean hasMoves()
    {
        ArrayList<Space> spacesToMove = new ArrayList<Space>();
        int currentRow = currentSpace.getRow();
        int currentCol = currentSpace.getCol();
        /** assinging movement limitations
         * 1 (White) = can only move forward(up the board)
         * 2 (Black) = can only move backward(down the board)
         * 3 (King) = can move both ways
         */
        int movement = -1;
        if(isBlack())
        {movement = 2;}
        else
        {movement = 1;}
        if(isKing())
        {movement = 3;}
        
        //retrieving the lines that the space is on
        ArrayList<Line> myLines = currentSpace.getMyLines();
        for(Line line:myLines)
        {
            if(movement == 2||movement == 3) //black piece can only move downwards
            {
                if(line.availableLowerSpace(currentSpace) != null)
                {
                    spacesToMove.add(line.availableLowerSpace(currentSpace));
                }
            }
            if(movement == 1 || movement == 3) //white piece can only move up
            {
                if(line.availableUpperSpace(currentSpace) != null)
                {
                    spacesToMove.add(line.availableUpperSpace(currentSpace));
                }
            }
        }
        
        if(spacesToMove.size() > 0)
        {
            return true;
        }
        return false;
    }
    
    //this method returns an arraylist of spaces that the piece can move to, 
    //including spaces that results in capturing a piece
    public ArrayList<Space> myMoves()
    {
        ArrayList<Space> spacesToMove = new ArrayList<Space>();
        int currentRow = currentSpace.getRow();
        int currentCol = currentSpace.getCol();
        /** assinging movement limitations
         * 1 (White) = can only move forward(up the board)
         * 2 (Black) = can only move backward(down the board)
         * 3 (King) = can move both ways
         */
        int movement = -1;
        if(isBlack())
        {movement = 2;}
        else
        {movement = 1;}
        if(isKing())
        {movement = 3;}
        
        //retrieving the lines that the space is on
        ArrayList<Line> myLines = currentSpace.getMyLines();
        for(Line line:myLines)
        {
            if(movement == 2||movement == 3) //black piece can only move downwards
            {
                if(line.availableLowerSpace(currentSpace) != null)
                {
                    spacesToMove.add(line.availableLowerSpace(currentSpace));
                }
            }
            if(movement == 1 || movement == 3) //white piece can only move up
            {
                if(line.availableUpperSpace(currentSpace) != null)
                {
                    spacesToMove.add(line.availableUpperSpace(currentSpace));
                }
            }
        }
        
        return spacesToMove;
    }

    //printing the piece
    public String toString()
    {
        if(this.isBlack && this.isKing)
        {
            return "KINGB";
        }
        else if(!this.isBlack && this.isKing)
        {
            return "KINGW";
        }
        else if(this.isBlack)
        {
            return "Black";
        }
        return "White";
    }


}

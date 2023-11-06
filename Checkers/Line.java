import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Diagonal lines in the checkers board
 */
public class Line
{
    //the Line class contains the spaces in diagonal lines on the board
    //it contains methos that checks if the piece can move to the spaces in the line
    //and it the piece can capture another piece in the line
    private Space[] spaces;
    private int lineNumber;

    /**
     * Constructor for objects of class Line
     */
    public Line(Space[] spaces, int lineNumber)
    {
        //constructor adds space to the instance variable spaces[]
        this.lineNumber = lineNumber;
        this.spaces = spaces;
        for(Space i:spaces)
        {
            i.addToMyLines(this);
        }
    }
    //accessor
    public Space[] getSpaces()
    {
        return spaces;
    }
    
    //returns an int that describes the state of the space right above the current space in the line
    public int upperSpaceOccupation(Space space)
    {
        //returns: -1 = invalid edge, 0 = empty, 1 = opponent piece, 2 = own piece
        int currentRow = space.getRow();
        Space upperSpace = null;
        for(Space i:spaces)
        {
            if(i.getRow() == currentRow - 1)
            {
                upperSpace = i;
            }
        }
        if(upperSpace == null)
        {
            return -1;
        }
        else if(upperSpace != null && upperSpace.isEmpty())
        {
            return 0;
        }
        else if(upperSpace.getPiece() != null && upperSpace.getPiece().isBlack() != space.getPiece().isBlack())
        {
            return 1;
        }
        else if(upperSpace.getPiece() != null && upperSpace.getPiece().isBlack() == space.getPiece().isBlack())
        {
            return 2;
        }
        return -1;
    }
    
    //returns the closest upper space that the piece can move from current space.
    //could return null (blocked or invalid), neighbor space right above (empty space)
    //or two spaces above (capturing opponent's piece) 
    public Space availableUpperSpace(Space space)
    {
        int occupation = this.upperSpaceOccupation(space);
        if(occupation == -1||occupation == 2)
        {
            return null;
        }
        else if(occupation == 0)
        {
            Space upperSpace = null;
            for(Space i:spaces)
            {
                if(i.getRow() == space.getRow() - 1)
                {
                    upperSpace = i;
                }
            }
            return upperSpace;
        }
        else if(occupation == 1)
        {
            Space upperSpace = null;
            for(Space i:spaces)
            {
                if(i.getRow() == space.getRow() - 2)
                {
                    if(i.isEmpty())
                        upperSpace = i;
                }
            }
            return upperSpace;
        }
        return null;
    }
    
    //refer to upperSpaceOccupation
    public int lowerSpaceOccupation(Space space)
    {
        //returns: -1 = invalid edge, 0 = empty, 1 = opponent piece, 2 = own piece
        if(space.getPiece() == null)
        {
            
            System.out.print("Got null piece again at " + space);
        }
        int currentRow = space.getRow();
        Space lowerSpace = null;
        for(Space i:spaces)
        {
            if(i.getRow() == currentRow + 1)
            {
                lowerSpace = i;
            }
        }
        if(lowerSpace == null || space == null)
        {
            return -1;
        }
        else if(lowerSpace != null && lowerSpace.isEmpty())
        {
            return 0;
        }
        else if(lowerSpace.getPiece() != null && lowerSpace.getPiece().isBlack() != space.getPiece().isBlack())
        {
            return 1;
        }
        else if(lowerSpace.getPiece() != null && lowerSpace.getPiece().isBlack() == space.getPiece().isBlack())
        {
            return 2;
        }
        return -1;
    }
    
    //refer to availableUpperSpace
    public Space availableLowerSpace(Space space)
    {
        int occupation = this.lowerSpaceOccupation(space);
        if(occupation == -1||occupation == 2)
        {
            return null;
        }
        else if(occupation == 0)
        {
            Space lowerSpace = null;
            for(Space i:spaces)
            {
                if(i.getRow() == space.getRow() + 1)
                {
                    lowerSpace = i;
                }
            }
            return lowerSpace;
        }
        else if(occupation == 1)
        {
            Space lowerSpace = null;
            for(Space i:spaces)
            {
                if(i.getRow() == space.getRow() + 2)
                {
                    if(i.isEmpty())
                        lowerSpace = i;
                }
            }
            return lowerSpace;
        }
        return null;
    }
    
    //obtains the Pieces in this line
    public Piece[] getPieces()
    {
        Piece[] myPieces = new Piece[this.spaces.length];
        for(int i = 0; i < spaces.length; i++)
        {
            myPieces[i] = spaces[i].getPiece();
        }
        return myPieces;
    }
    
    //prints the line
    public String toString()
    {
        String returnString = "Line " + lineNumber + "\n";
        for(Space s:spaces)
        {
            returnString += s.toString() + "\n";
        }
        return returnString;
    }


}

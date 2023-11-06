import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 Checker board spaces
 */
public class Space extends Actor
{
    //the abstract spacea are stored in the 2D array spaces[][] in the world.
    //it contains its row and col number, and it knows the piece that is in it.
    //it also knows which lines it is in.
    private int row;
    private int col;
    private Piece myPiece;
    private ArrayList<Line> myLines = new ArrayList<Line>();
    
    public Space(int row, int col)
    {
        //constructor
        this.row = row;
        this.col = col;
    }
    //one line accessors
    public Piece getPiece()
    {   return myPiece; } 
    
    public int getRow() 
    {   return row; }
    
    public int getCol()
    {   return col; }
    
    public ArrayList<Line> getMyLines()
    {   return myLines;}
    
    public void receivePiece(Piece p)
    {
        myPiece = p;
    }
    //removes myPiece and returns it
    public Piece removePiece()
    {
        Piece tempPiece = myPiece;
        myPiece = null;
        return tempPiece;
    }
    
    public boolean isEmpty()
    {
        return myPiece == null;
    }    
    //adds a line to myLines
    public void addToMyLines(Line line)
    {
        if(line != null)
            myLines.add(line);
    }
    //prints the space
    public String toString()
    {
        return "[" + row + "][" + col + "]";
    }
}

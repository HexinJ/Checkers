import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class GraphicSpace extends Space
{
    //the GraphicSpace class is the graphic version of Space, it has information like
    //the row and col number in the myGraphicSpaces[][] 2D array it resides in the world.
    //it knows its coordinates pixels in the world. It knows its GraphicPiece, Piece, and Space
    //it has its own image, which has the unhighlighted state (transparent square) and 
    //highlighted state (transparent square with yellow outline)
    
    //****All the mouse detections are in the act() method of this class****
    
    private int row;
    private int col;
    private int coorX;
    private int coorY;
    private boolean isHighlighted;
    private GreenfootImage myImage;
    private GraphicPiece myGraphicPiece;
    private Piece myPiece;
    private Space mySpace;
    private CheckerWorld world;
    
    public GraphicSpace(int row, int col, int coorX, int coorY)
    {   //constructor creates the transparent square
        super(row,col);
        this.row = row;
        this.col = col;
        this.coorX = coorX;
        this.coorY = coorY;
        this.isHighlighted = isHighlighted;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //prints the GraphicSpace
    public String toString()
    {
        return "" + row + " " + col;
    }
    //updates myPiece
    public void updatePiece(Piece piece)
    {
        myPiece = piece;
    }
    //after the world is constructed, this method is called to add information to its
    //instance variables
    public void addInformation()
    {
        
        world = (CheckerWorld) getWorld();
        mySpace = world.getSpaces()[row][col];
        myPiece = mySpace.getPiece();

    }
    //selects the GraphicSpace (w/ yellow outline)
    public void select()
    {
        isHighlighted = true;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //unselects the GraphicSpace (transparent square)
    public void unSelect()
    {
        isHighlighted = false;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //accessors and mutators for graphicPiece
    public void receiveGraphicPiece(GraphicPiece a)
    {
        myGraphicPiece = a;
    }
    
    public GraphicPiece removeGraphicPiece()
    {
        GraphicPiece temp = myGraphicPiece;
        myGraphicPiece = null;
        return temp;
    }
    
    public GraphicPiece getGraphicPiece()
    {
        return myGraphicPiece;
    }
    //generates the image based on isHighlighted
    public GreenfootImage generateImage()
    {
        if(!isHighlighted)
        {
            return new GreenfootImage("transparent_square.png");
        }
        else
        {
            return new GreenfootImage("transparent_square_highlighted.png");
        }
    }
    //more one line accessors
    public int getRow()
    {return row;}
    public int getCol()
    {return col;}
    public int getXCoor()
    {return coorX;}
    public int getYCoor()
    {return coorY;}
    
    //selects GraphicSpaces that can be captured in a multiple capture
    public void captureMultiple()
    {
        ArrayList<Space> spaces = myPiece.canCaptureSpaces();
        myGraphicPiece.select();
        for(Space space:spaces)
        {
            world.getGraphicSpaces()[space.getRow()][space.getCol()].select();
        }
    }
    
    //GraphicSpace and GraphicPiece changes the images depending on the
    //states in the world (isBlackTurn, isSelectingPhase, MultipleCapture...)
    //and the mouse inputs (hover, click...)
    public void act()
    {
        MouseInfo MI = Greenfoot.getMouseInfo();
        //selecting phase mouse hover on object - highlight piece
        if(Greenfoot.mouseMoved(this) && myGraphicPiece!=null && world.isBlackTurn() == myGraphicPiece.isBlack() 
        && world.isSelectingPhase() && myPiece.hasMoves() && world.getMultipleCapture() == false)
        {
            myGraphicPiece.select();
        }
        //selecting phase mouse hover off object - unhighlight piece
        else if(Greenfoot.mouseMoved(null) && myGraphicPiece!=null && world.isBlackTurn() == myGraphicPiece.isBlack() 
        && world.isSelectingPhase() && world.getMultipleCapture() == false)
        {
            myGraphicPiece.unSelect();
        }
        //selecting phase mouse click on object - lock object on highlight
        else if(Greenfoot.mouseClicked(this) && myGraphicPiece!=null && world.isBlackTurn() == myGraphicPiece.isBlack() 
        && world.isSelectingPhase() && myPiece.hasMoves() && world.getMultipleCapture() == false)
        {
            if(world.isBlackTurn()){world.getMessageBox().setMessage("Black side please select a space to move", 16,0);}
            else if(!world.isBlackTurn()){world.getMessageBox().setMessage("White side please select a space to move", 16,0);}
            myGraphicPiece.select();
            world.changeToPlacingPhase();
            world.setSelectedPiece(myGraphicPiece);
            world.setSelectedSpace(this);
        }
        //placing phase mouse hover on an available space - highlight space
        else if(!world.isSelectingPhase() && myGraphicPiece==null && Greenfoot.mouseMoved(this))
        {
            ArrayList<Space> availableSpaces = world.selectedPiece().getPiece().myMoves();
            
            for(Space i:availableSpaces)
            {
                if(i.getRow()==row&&i.getCol()==col)
                {
                    this.select();
                }
            }
        }
        //placing phase mouse hover off an available space - unhighlight space
        else if(!world.isSelectingPhase() && Greenfoot.mouseMoved(null))
        {
            this.unSelect();
        }
        //when placing a piece in an available space - move the piece to the space
        else if(!world.isSelectingPhase() && myGraphicPiece==null && Greenfoot.mouseClicked(this))
        {
            ArrayList<Space> availableSpaces = world.selectedPiece().getPiece().myMoves();
            
            for(Space i:availableSpaces)
            {
                if(i.getRow()==row&&i.getCol()==col)
                {
                    world.moveGraphicPiece(world.selectedSpace(),world.getGraphicSpaces()[row][col]);
                    
                }
            }
        }
        //the selected graphicSpaces when captureMultiple is true
        else if(world.getMultipleCapture() && this.isHighlighted && Greenfoot.mouseClicked(this))
        {
            
            world.deselectAllGS();
            world.setMultipleCapture(false);
            world.moveGraphicPiece(world.selectedSpace(),this);
            
        }
    }
}

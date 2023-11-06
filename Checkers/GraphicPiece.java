import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class GraphicPiece extends Piece
{
    //this is the graphic version of the piece class. It contains the same information as 
    //the piece class, it knows which abstract piece it is, and in addition it contains its
    //own greenfoot image. 
    private boolean isBlack;
    private boolean isKing;
    private boolean highLighted;
    private GreenfootImage myImage;
    private Piece myPiece;

    //constructor generates an image
    public GraphicPiece(boolean isBlack, boolean isKing)
    {
        super(isBlack, isKing);
        this.isBlack = isBlack;
        this.isKing = isKing;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //accessor
    public boolean isRoyalty()
    {
        return isKing;
    }
    //this changes the image of the graphic piece on the world to the selected version
    public void select()
    {
        highLighted = true;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //this unselects the graphic piece and the image returns to normal
    public void unSelect()
    {
        highLighted = false;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //this changes the image of the graphic piece to king
    public void becomeKing()
    {
        isKing = true;
        myImage = generateImage();
        myImage.scale(58,58);
        setImage(myImage);
    }
    //receives the abstract piece
    public void receivePiece(Piece piece)
    {
        myPiece = piece;
    }
    //accessor
    public Piece getPiece()
    {
        return myPiece;
    }
    //a bunch of images depending on the conditions isBlack, isKing, isHighlighted
    public GreenfootImage generateImage()
    {
        if(isBlack&&!isKing&&!highLighted)
        {
            return new GreenfootImage("Black_plain.png");
        }
        else if(isBlack&&!isKing&&highLighted)
        {
            return new GreenfootImage("Black_plain_highlighted.png");
        }
        else if(isBlack&&isKing&&!highLighted)
        {
            return new GreenfootImage("Black_king.png");
        }
        else if(isBlack&&isKing&&highLighted)
        {
            return new GreenfootImage("Black_king_highlighted.png");
        }
        else if(!isBlack&&!isKing&&!highLighted)
        {
            return new GreenfootImage("White_plain.png");
        }
        else if(!isBlack&&!isKing&&highLighted)
        {
            return new GreenfootImage("White_plain_highlighted.png");
        }
        else if(!isBlack&&isKing&&!highLighted)
        {
            return new GreenfootImage("White_king.png");
        }
        else if(!isBlack&&isKing&&highLighted)
        {
            return new GreenfootImage("White_king_highlighted.png");
        }
        return null;
    }
    
    public void act()
    {
        
        
    }
    
    
    
    
    
}

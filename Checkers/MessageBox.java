import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

//message box class - displays a message in the world
//contains a private greenfoot image that displays a text in the world
public class MessageBox extends Actor
{
    private GreenfootImage myImage;
    private String message;
    private int size;
    private boolean isAuthor;
    public MessageBox(String message, int size, boolean isAuthor)
    {
        this.message = message;
        this.size = size;
        this.isAuthor = isAuthor;
        myImage = new GreenfootImage(message, size, Color.BLACK, Color.WHITE);
        setImage(myImage);
    }
    
    //changes the message and updates the image
    public void setMessage(String message, int size, int delay)
    {
        this.size = size;
        myImage = new GreenfootImage(message, size, Color.BLACK, Color.WHITE);
        setImage(myImage);
        Greenfoot.delay(delay);
    }
    
    //this method returns the message box to default message of selecting pieces
    public void act()
    {   if(!isAuthor)
        {
            CheckerWorld world = (CheckerWorld) getWorld();
            boolean blackTurn = world.isBlackTurn();
            MouseInfo MI = Greenfoot.getMouseInfo();
            if(blackTurn && world.isSelectingPhase() && !world.getMultipleCapture())
            {
                this.setMessage("Black side please select a piece", 16,0);
            }
            else if(!blackTurn && world.isSelectingPhase() && !world.getMultipleCapture())
            {
                this.setMessage("White side please select a piece", 16,0);
            }
            return;
        }
    }
}

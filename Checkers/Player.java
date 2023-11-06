import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Player extends Actor 
{
    //the player class contains the number of wins of this player and an image
    //that is the win counter in the world
    public final String name;
    private final boolean isBlack;
    private int wins = 0;
    private GreenfootImage myWin;
    /**
     * Constructor for objects of class Player
     */
    public Player(String name, boolean isBlack)
    {
        this.name = name;
        this.isBlack = isBlack;
        myWin = new GreenfootImage(name + " wins: " + wins, 12, Color.BLACK, Color.WHITE);
    }
    
    //updates the win counter
    public void setWinImage()
    {
        myWin = new GreenfootImage(name + " wins: " + wins, 12, Color.BLACK, Color.WHITE);
        setImage(myWin);
    }
    //one line accessors and mutators
    public String getName()
    {   return name; }
    
    public boolean isBlack()
    {   return this.isBlack; }
    
    public void addWin()
    {   wins++; }
    public int getWins()
    {   return wins; }
    //gets an input from the user (not used)
    public void getInput(String msg)
    {
        String input = Greenfoot.ask(msg);
    }
}

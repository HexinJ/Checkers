import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * CheckerWorld where the game runs
 * @author Hexin Jiang, Haverford College
 * @class CS107
 * @instructor John Dougherty
 */
public class CheckerWorld extends World
{
    //the world of the game, which includes instance variables of all the structures,
    //players, booleans of state or phase, and contains some helper methods that 
    //is a part of running the game
    private Board board;
    private final int[][] InitialSpaces = {{1,3,5,7},{0,2,4,6},{1,3,5,7}};
    private final int[][] InitialSpaces2 = {{0,2,4,6},{1,3,5,7},{0,2,4,6}};
    private Space[][] spaces = new Space[8][8];
    private GraphicSpace[][] myGraphicSpaces = new GraphicSpace[8][8];
    private boolean blackTurn = true; //which turn is it
    private boolean selectingPhase = true; //selecting phase or placing phase
    private boolean multipleCapture = false; //the state where a piece can capture multiple pieces
    private GraphicPiece selectedGraphicPiece;
    private GraphicSpace selectedGraphicSpace;
    private Player p1;
    private Player p2;
    private MessageBox messageBox;
    
    //private GreenfootImage img;
    
    public CheckerWorld()
    {    
        // Create a new world with 612*612 cells with a cell size of 1x1 pixels.
        super(612, 612, 1); 
        setPaintOrder(GraphicSpace.class,GraphicPiece.class);
        //setting the checker board background
        setBackground("istockphoto-476469645-612x612.jpg");
        //initializing spaces for the board
        for(int i = 0; i < spaces.length; i++)
        {
            for(int o = 0; o < spaces[0].length; o++)
            {
                spaces[i][o] = new Space(i,o);
            }
        }
        
        //adding the black initial pieces
        for(int i = 0; i < InitialSpaces.length; i++)
        {
            for(int o = 0; o < InitialSpaces[0].length; o++)
            {
                Piece myPiece = new Piece(true,false);
                spaces[i][InitialSpaces[i][o]].receivePiece(myPiece);
                myPiece.inSpace(spaces[i][InitialSpaces[i][o]]);
            }
        }
        
        //adding the white initial pieces
        for(int i = 5; i < InitialSpaces2.length + 5; i++)
        {
            for(int o = 0; o < InitialSpaces2[0].length; o++)
            {
                Piece myPiece = new Piece(false,false);
                spaces[i][InitialSpaces2[i-5][o]].receivePiece(myPiece);
                myPiece.inSpace(spaces[i][InitialSpaces2[i-5][o]]);
            }
        }
        
        //initializing the board
        board = new Board(spaces);
        System.out.println(board);
        
        //initializing graphic spaces
        //initializing graphic pieces
        for(int i = 0; i <= 7; i++)
        {
            for(int o = 0; o <= 7; o++)
            {
                //adjustments to the position
                int xCoor = 100 + 60 * o;
                int yCoor = 92 + 59 * i;
                if(o >= 2)
                {
                    xCoor -= 1;
                }
                if(o >= 4)
                {
                    xCoor -= 1;
                }
                if(o >= 6)
                {
                    xCoor -= 1;
                }
                if(o == 1 || o == 3 || o == 5 || o == 7)
                {
                    if(i == 0 || i == 2)
                    {
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,true);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                    else if(i == 4)
                    {
                        initializeGraphicSpace(i,o,xCoor,yCoor,null);
                    }
                    else if(i == 6)
                    {
                        if(o == 1)
                        {
                            xCoor -= 1;
                        }
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,false);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                }
                if(o == 0 || o == 2 || o == 4 || o == 6)
                {
                    if(i == 1)
                    {
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,true);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                    else if(i == 3)
                    {
                        initializeGraphicSpace(i,o,xCoor,yCoor,null);
                    }
                    else if(i == 5 || i == 7)
                    {
                        if(o == 0)
                        {
                            xCoor -= 1;
                        }
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,false);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                }
            }
        }
        
        
        //initializing players
        p1 = new Player("Black",true); //player 1 is the black side player
        p2 = new Player("White",false); //player 2 is the white side player
        p1.setWinImage();
        p2.setWinImage();
        addObject(p1,38,588);
        addObject(p2,36,600);
        
        //initializing messageBox
        messageBox = new MessageBox("Welcome to Checkers!",24,false);
        addObject(messageBox,306,595);
        MessageBox author = new MessageBox("Developed by Ben Jiang", 12,true);
        addObject(author,540,10);
    }
    
    public void reset() //this method resets the board by removing all objects and actors
    //in the world and setting them like in the constructor of the class
    //also resets the many boolean values below
    {
        blackTurn = true;
        selectingPhase = true;
        multipleCapture = false;
        selectedGraphicPiece = null;
        selectedGraphicSpace = null;
        removeObjects(getObjects(GraphicPiece.class));
        removeObjects(getObjects(GraphicSpace.class));
        //initializing spaces for the board
        for(int i = 0; i < spaces.length; i++)
        {
            for(int o = 0; o < spaces[0].length; o++)
            {
                spaces[i][o] = new Space(i,o);
            }
        }
        
        //adding the black initial pieces
        for(int i = 0; i < InitialSpaces.length; i++)
        {
            for(int o = 0; o < InitialSpaces[0].length; o++)
            {
                Piece myPiece = new Piece(true,false);
                spaces[i][InitialSpaces[i][o]].receivePiece(myPiece);
                myPiece.inSpace(spaces[i][InitialSpaces[i][o]]);
            }
        }
        
        //adding the white initial pieces
        for(int i = 5; i < InitialSpaces2.length + 5; i++)
        {
            for(int o = 0; o < InitialSpaces2[0].length; o++)
            {
                Piece myPiece = new Piece(false,false);
                spaces[i][InitialSpaces2[i-5][o]].receivePiece(myPiece);
                myPiece.inSpace(spaces[i][InitialSpaces2[i-5][o]]);
            }
        }
        
        //initializing the board
        board = new Board(spaces);
        System.out.println(board);
        
        //initializing graphic spaces
        //initializing graphic pieces
        for(int i = 0; i <= 7; i++)
        {
            for(int o = 0; o <= 7; o++)
            {
                //adjustments to the position
                int xCoor = 100 + 60 * o;
                int yCoor = 92 + 59 * i;
                if(o >= 2)
                {
                    xCoor -= 1;
                }
                if(o >= 4)
                {
                    xCoor -= 1;
                }
                if(o >= 6)
                {
                    xCoor -= 1;
                }
                if(o == 1 || o == 3 || o == 5 || o == 7)
                {
                    if(i == 0 || i == 2)
                    {
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,true);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                    else if(i == 4)
                    {
                        initializeGraphicSpace(i,o,xCoor,yCoor,null);
                    }
                    else if(i == 6)
                    {
                        if(o == 1)
                        {
                            xCoor -= 1;
                        }
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,false);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                }
                if(o == 0 || o == 2 || o == 4 || o == 6)
                {
                    if(i == 1)
                    {
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,true);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                    else if(i == 3)
                    {
                        initializeGraphicSpace(i,o,xCoor,yCoor,null);
                    }
                    else if(i == 5 || i == 7)
                    {
                        if(o == 0)
                        {
                            xCoor -= 1;
                        }
                        GraphicPiece myGP = initializeGraphicPiece(xCoor,yCoor,false);
                        myGP.receivePiece(spaces[i][o].getPiece());
                        initializeGraphicSpace(i,o,xCoor,yCoor,myGP);
                    }
                }
            }
        }
    }
    
    public void deselectAllGS()//this method deselects all graphic spaces (making them
    //not having the yellow outline)
    {
        for(GraphicSpace[] i:myGraphicSpaces)
        {
            for(GraphicSpace o:i)
            {
                if(o!=null)
                {o.unSelect();}
            }
        }
    }
    
    //a bunch of accessors and mutators for the boolean states below:
    public MessageBox getMessageBox()
    {
        return messageBox;
    }
    
    public boolean getMultipleCapture()
    {
        return multipleCapture;
    }
    
    public void setMultipleCapture(boolean a)
    {
        multipleCapture = a;
    }
    
    public Space[][] getSpaces()
    {
        return spaces;
    }
    
    public Board getBoard()
    {
        return board;
    }
    
    public void setSelectedSpace(GraphicSpace myGS)
    {
        selectedGraphicSpace = myGS;
    }
    
    public GraphicSpace selectedSpace()
    {
        return selectedGraphicSpace;
    }
    
    public GraphicSpace[][] getGraphicSpaces()
    {
        return myGraphicSpaces;
    }
    
    public CheckerWorld getWorld()
    {
        return this;
    }
    
    public boolean isBlackTurn()
    {
        return blackTurn;
    }
    
    public boolean isSelectingPhase()
    {
        return selectingPhase;
    }
    
    public void changeToPlacingPhase()
    {
        selectingPhase = false;
    }
    
    public void changeToSelectingPhase()
    {
        selectingPhase = true;
    }
    
    public void setSelectedPiece(GraphicPiece myGP)
    {
        selectedGraphicPiece = myGP;
    }
    
    public GraphicPiece selectedPiece()
    {
        return selectedGraphicPiece;
    }
    
    public void changeTurn()
    {
        blackTurn = !blackTurn;
    }
    
    //this method moves a graphic piece from one graphic space to another and 
    //also moves the piece on the board from one space to another
    //it checks whether the piece is capturing, or is crowned king, or can capture again
    //*At the end of each move, this method checks if there is a winner. If there is a winner,
    //it resets the board
    public void moveGraphicPiece(GraphicSpace start, GraphicSpace end)
    {
        this.setSelectedPiece(null);
        GraphicPiece temp = start.removeGraphicPiece();
        GraphicPiece newGP = initializeGraphicPiece(end.getXCoor(),end.getYCoor(),temp.isBlack());
        if(temp.isRoyalty())
        {
            newGP.becomeKing();
        }
        removeObject(temp);
        end.receiveGraphicPiece(newGP);
        end.unSelect();
        
        int status = board.tryToMovePiece(spaces[start.getRow()][start.getCol()],spaces[end.getRow()][end.getCol()]);
        newGP.receivePiece(spaces[end.getRow()][end.getCol()].getPiece());
        end.updatePiece(spaces[end.getRow()][end.getCol()].getPiece());
        
        //capturing a piece graphically
        if(status == 2)
        {
            System.out.println("Detected capture graphic");
            GraphicSpace mid = myGraphicSpaces[(start.getRow() + end.getRow()) / 2][(start.getCol() + end.getCol()) / 2];
            GraphicPiece lostSoul = mid.removeGraphicPiece();
            removeObject(lostSoul);
            //checking if the same piece can capture again
            ArrayList<Space> canCapture = end.getGraphicPiece().getPiece().canCaptureSpaces();
            if(canCapture.size() > 0)
            {
                this.changeToSelectingPhase();
                multipleCapture = true;
                messageBox.setMessage("Please continue to capture another piece",16,0);
                this.setSelectedPiece(end.getGraphicPiece());
                this.setSelectedSpace(end);
                end.captureMultiple();
                return;
            }
        }
        //crowning king graphically
        else if(status == 3)
        {
            System.out.println("Detected king graphic");
            newGP.becomeKing();
        }
        //capture and crowning king graphically
        else if(status == 4)
        {
            System.out.print("Detected both graphic");
            GraphicSpace mid = myGraphicSpaces[(start.getRow() + end.getRow()) / 2][(start.getCol() + end.getCol()) / 2];
            GraphicPiece lostSoul = mid.removeGraphicPiece();
            removeObject(lostSoul);
            newGP.becomeKing();
            //checking if the same piece can capture again
            ArrayList<Space> canCapture = end.getGraphicPiece().getPiece().canCaptureSpaces();
            if(canCapture.size() > 0)
            {
                this.changeToSelectingPhase();
                multipleCapture = true;
                messageBox.setMessage("Please continue to capture another piece",16,0);
                this.setSelectedPiece(end.getGraphicPiece());
                this.setSelectedSpace(end);
                end.captureMultiple();
                return;
            }
        }
        //check for win condition
        if(board.checkForWhiteWin())
        {
            System.out.println("detected white win");
            p2.addWin();
            p2.setWinImage();
            messageBox.setMessage("White side wins! Resetting board...",20,150);
            this.reset();
            return;
        }
        else if(board.checkForBlackWin())
        {
            System.out.println("detected black win");
            p1.addWin();
            p1.setWinImage();
            messageBox.setMessage("Black side wins! Resetting board...",20,150);
            this.reset();
            return;
        }
        this.changeToSelectingPhase();
        this.changeTurn();
        System.out.println("Next turn: isBlackTurn = " + blackTurn);
    }
    
    //this method initializes a graphic space object so it can be placed in the world
    public void initializeGraphicSpace(int rol, int col, int xCoor, int yCoor, GraphicPiece myGP)
    {
        GraphicSpace myGS = new GraphicSpace(rol,col,xCoor,yCoor);
        myGS.receiveGraphicPiece(myGP);
        myGraphicSpaces[rol][col] = myGS;
        addObject(myGS,xCoor,yCoor);
        myGS.addInformation();
    }
    
    //this method initializes a graphic piece object
    public GraphicPiece initializeGraphicPiece(int x, int y, boolean isBlack)
    {
        GraphicPiece myGP = new GraphicPiece(isBlack,false);
        addObject(myGP,x,y);
        return myGP;
    }
}

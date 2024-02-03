import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class door extends elevator
{

    private myWorld mWorld;

    boolean closed = false;
    boolean bMetaldoor = false;

    static GreenfootImage openImgMetal = new GreenfootImage("door.png");
    static GreenfootImage closedImgMetal = new GreenfootImage("door.png");

    static GreenfootImage openImg = new GreenfootImage("door.png");
    static GreenfootImage closedImg = new GreenfootImage("door.png");

    GreenfootImage imgClosed = closedImg;
    GreenfootImage imgOpened = openImg;

    public int OriginalX;

    public boolean Scrolling;

    int level;

    myWorld mworld;

    public door()
    {
        level = 0;
        Scrolling = false;
    }//alt constructor

    public door(int level)
    {
        this.level = level;
    }//altconst2

    public door(boolean clo)
    {
        Scrolling = clo;
    }//constructor

    //////////////////////////////////////
    public void act() 
    {       
        Actor player = getOneObjectAtOffset(-1, -29, Player.class);
        if(player != null)
            playerExit();
        //         getImage().setTransparency(mworld.t);

        else if(Scrolling)
            setLocation(mworld.doorPos, getY());
    }

    public void addedToWorld(World world) {
        mworld = (myWorld) world; 
    }// addedtowrld

    private void playerExit()
    {
        String key = Greenfoot.getKey();
        if(key == ("down"))
        {
            Actor player = getOneObjectAtOffset(-1, -29, Player.class);
            if(player != null)
            {    
                Greenfoot.playSound("42.wav");
                setImage("door.png");
                if(mworld.ThisLevel==56)
                {
                    mworld.ThisLevel = -1;
                    mworld.setLevel(-1);
                }
                else if(level == 0)
                {
                    mworld.nextLevel();
                }
                else if(level != 0)
                {
                    mworld.setLevel(level);
                }
            }
        }
    }//pexit
}//class
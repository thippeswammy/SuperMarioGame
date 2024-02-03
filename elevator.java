import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class elevator here.
 * 
 * Credits to mutantleg for writing this 
 * 
 */
public class elevator extends liftsANDdoors
{
    /**
     * Act - do whatever the elevator wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public int yOffset;

    int pX, pY;
    int cX, cY;

    myWorld mworld;

    boolean bActive = true; //if true it holds players

    public elevator()
    {     
        yOffset = 25;
    }

    public boolean isActive()
    {
        return bActive;
    }//isactiv

    public void act() 
    {

        pX = getX();// x and y before moving
        pY = getY();

        setLocation(getX(),getY()-2);

        cX = getX()-pX;
        cY = getY()-pY;

        if (getX() > 630) {setLocation(10, getY());}
        if (getY() < 10) {setLocation(getX(), 470);}

        if (getY() > 475)  {setLocation(getX(), 10);}

    }//act    
}

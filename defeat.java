import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class defeat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class defeat extends effect
{
    /**
     * Act - do whatever the defeat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private myWorld mworld;

    float ys = -4;
    float y = 0;

    public void addedToWorld(World world) {
        mworld = (myWorld) world; 
        y = getY();
    }//added

    public void act() 
    {
        y += ys;
        if (ys< 6) {ys+=0.3;}

        if(y<0) { y = 0; ys = 1;}
        setLocation(getX(),(int) y);
        if (y > 470) { mworld.restartLevel();}

    }    //act
}//class

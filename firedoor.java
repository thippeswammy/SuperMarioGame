import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class easydoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class firedoor  extends elevator
{

    myWorld mworld;
    /**
     * Act - do whatever the easydoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        Actor player = getOneObjectAtOffset(-1, -29, Player.class);
        if(player != null)
        {
            playerExit();
        }
    }    

    private void playerExit()
    {         
        String key = Greenfoot.getKey();
        if(key == ("down"))
        {
            Greenfoot.playSound("42.wav");
            //mworld.FireMode=true;
            mworld.toggleFireDifficulty();
            ((myWorld) getWorld()).nextLevel();
        }
    }//pexit

    public void addedToWorld(World world) 
    {
        mworld = (myWorld) world;
    }// addedtowrld
}
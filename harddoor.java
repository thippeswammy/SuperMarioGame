import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class harddoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class harddoor  extends elevator
{

    myWorld mworld;
    /**
     * Act - do whatever the harddoor wants to do. This method is called whenever
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
            myWorld mw = (myWorld) getWorld();
            Greenfoot.playSound("42.wav");
            mworld.FireMode=false;
            mw.toggleDifficulty(); 
            ((myWorld) getWorld()).nextLevel();
        }
    }//pexit

    public void addedToWorld(World world) {
        mworld = (myWorld) world; 
    }// addedtowrld
}

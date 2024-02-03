import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class secretDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class secretDoor  extends elevator
{
    /**
     * Act - do whatever the secretDoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        playerExit();
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
                mworld.ThisLevel=55;
                mworld.setLevel(mworld.ThisLevel);
            }
        }
    }//pexit   
}

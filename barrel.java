import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * I don't think this sublass is even used in the game...
 */
public class barrel extends gridder
{
    public int byebye = 0;
    int yOffset = 23;
    /**
     * Act - do whatever the barrel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected void addedToWorld(World world) 
    {
        grid();
    }
    
    public void act()
    {
        if(byebye == 1)
        {
            getWorld().addObject(new Debris(5), getX(), getY());
            ((myWorld) getWorld()).score += 5;
            getWorld().removeObject(this);
            return;
        }
    }
}

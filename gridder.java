import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Locks the blocks to a grid. Thanks Ninto. Using the floats seems to make it a tiny bit more accurate.
 */
public abstract class gridder extends elevator
{
    int gridSize = 5;

    protected void addedToWorld(World world)
    {
        grid();
    }

    public void grid()
    {
        float r = getX()-(getX() % gridSize);
        float c = getY()-(getY() % gridSize);

        if(getX() % gridSize >= gridSize/2)
            r += gridSize-gridSize/2;
        else
            r += gridSize/2;

        if(getY() % gridSize >= gridSize/2)
            c += gridSize-gridSize/2;

        else
            c += gridSize/2;

        setLocation((int)r, (int)c);
    }
}
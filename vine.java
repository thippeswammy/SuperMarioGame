import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class vine  extends ladder
{   
    static int segments;
    int sy;
    boolean move;
    boolean addedOne = false;

    public vine(int segs)
    {
        segments = segs;
        move = false;
    }

    public vine()
    {
        move = true;
    }

    protected void addedToWorld(World world)
    {
        sy = getY();
    }

    public void act()
    {
        if(move)
        {
            if(getY() > sy-22)
            {
                setLocation(getX(), getY()-1);
            }
            else
            {
                move = false;
            }
        }
        if(segments > 1 && move == false && !addedOne)
        {
            getWorld().addObject(new vine(), getX(), getY());
            segments--;
            addedOne = true;
        }
    }
}

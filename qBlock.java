import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * It's a block containing either a coin, or a 1up
 */
public class qBlock  extends barrel
{
    public int yOffset=23;
    boolean animate;
    boolean ladder;
    int segments;
    boolean hasladder = false;
    /**
     * Act - do whatever the qBlock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final GreenfootImage[] img = {
            new GreenfootImage("QBlock0.png"),
            new GreenfootImage("QBlock1.png"),
            new GreenfootImage("QBlock2.png"),
            new GreenfootImage("QBlock3.png"),
            new GreenfootImage("block.png"),
            new GreenfootImage(32, 32)
        };
    private boolean done = false;
    private int first, last, treasure;
    private double speed, cur;

    public qBlock()
    {
        setImage(img[0]);
        set(0, 3, .1);
        animate = true;
        ladder = false;
    }

    public qBlock(int ladderSegments)
    {
        setImage(img[0]);
        set(0, 3, .1);
        animate = true;
        ladder = true;
        segments = ladderSegments;
    }

    public qBlock(boolean visible)
    {
        animate = false;
        setImage(img[0]);
        getImage().setTransparency(0);
    }

    protected void addedToWorld(World world) 
    {
        grid();
    }

    public void act() 
    {
        if(done == false)
        {
            if(!animate)
            {
            }
            else
                animate();
            if(byebye==1)
            {
                //                getWorld().addObjectToMap(a, (int)Math.round(mapX()), (int)Math.round(mapY()));
                done = true;
                setImage(img[4]);
            }

            if(ladder == false)
            {
                int numba = Greenfoot.getRandomNumber(4);
                if(((myWorld) getWorld()).playerHealth < 3)
                {
                    if(numba == 0)
                    {
                        if(byebye==1)
                        {
                            getWorld().addObject(new Shroom(), getX(), getY()-25);
                            byebye = 2;
                        }
                        if(byebye==2)
                        {
                            setImage("block.png");
                        }
                    }

                    else
                    {
                        if(byebye==1)
                        {
                            getWorld().addObject(new Coin(false), getX(), getY()-35);
                            Greenfoot.playSound("54.wav");
                            byebye = 2;
                        }
                        if(byebye==2)
                        {
                            setImage("block.png");
                        }
                    }
                }
                else
                {
                    if(byebye==1)
                    {
                        getWorld().addObject(new Coin(false), getX(), getY()-35);
                        Greenfoot.playSound("54.wav");
                        byebye = 2;
                    }
                    if(byebye==2)
                    {
                        setImage("block.png");
                    }
                }
            }
        }
        else if(ladder)
        {
            if(byebye==1)
            {
                if(!hasladder)
                {
                    getWorld().addObject(new ladder(segments), getX(), getY()-22);
                    hasladder = true;
                }
                byebye = 2;
            }
            if(byebye==2)
            {
                setImage("block.png");
            }
        }
    }

    private void set(int first, int last, double speed)
    {
        this.first = first;
        this.last = last;
        this.speed = speed;
        cur = first;
    }

    private void animate()
    {
        cur += speed;
        if(cur >= last + 1) cur = first;
        setImage(img[(int)cur]);
    }

}
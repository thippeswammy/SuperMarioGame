import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A copy of the door class that has the ability to move.
 */
public class pDoor extends elevator
{
    float x, y, xs, ys;

    static final GreenfootImage img = new GreenfootImage("door.png");
    static final int height = img.getHeight() / 2;

    public boolean Scrolling;

    int level;

    myWorld mworld;

    public pDoor()
    {
        level = 0;
        Scrolling = false;
    }//alt constructor

    public pDoor(int level)
    {
        this.level = level;
    }//altconst2

    public pDoor(boolean clo)
    {
        Scrolling = clo;
    }//constructor

    private void findBrick()
    {
        barrel mg = null;
        Pushable mh = null;

        mg = (barrel) getOneIntersectingObject(barrel.class);
        mh = (Pushable) getOneIntersectingObject(Pushable.class);

        if (mg!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isIce(getX(), getY()-14)&& !mworld.isGround(getX(), getY()+14) ){
            // mworld.paintBg(mg.getX(),mg.getY());

            if (
            (getY())  < (mg.getY()- mg.yOffset) 
            )
            {
                y = mg.getY() - mg.yOffset;
                ys = 0;

            }//ys

        }//mgnull

        if (mh!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isIce(getX(), getY()-14)&& !mworld.isGround(getX(), getY()+14) ){
            // mworld.paintBg(mg.getX(),mg.getY());

            if (
            (getY())  < (mh.getY()- mh.getImage().getHeight() /2 - 5) 
            )
            {
                y = mh.getY() - mh.getImage().getHeight() /2 -5;
                ys = 0;

            }//ys

        }//mgnull
    }//void

    private void findElevator()
    {
        elevator mg = null;
        elevator mg2 = null;

        mg = (elevator) getOneIntersectingObject(elevator.class);
        mg2 = (elevator) getOneObjectAtOffset(0, height + 4, elevator.class);

        if(mg instanceof barrel) // Dont look for barrels
        {}

        if(mg2 instanceof barrel)
        {}

        else if (mg!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isGround(getX(), getY()+14) && y>30){
            // mworld.paintBg(mg.getX(),mg.getY());

            if(y<=30)
            {
            }
            else if (
            //   ys > 0  
            mg.isActive()
            && ys > -1
            && getY() < mg.getY()
            )
            {
                if(mg instanceof door || mg instanceof secretDoor || mg instanceof falsedoor)
                {
                    y--;
                }
                else
                {
                    y = mg.getY() - mg.yOffset - height;
                }

                if(mg2 != null)
                {
                    y = mg2.getY() - mg2.yOffset - height;
                    x = x + mg.cX;
                }

                y = y + mg.cY;
                ys = 0;
            }//ys
        }//mgnull
    }//void

    //////////////////////////////////////
    public void act() 
    {       
        try
        {
            Actor player = getOneObjectAtOffset(-1, -29, Player.class);

            if(player != null)
                playerExit();

            if (ys < 5 ) {ys += 0.3;}

            findElevator();
            findBrick();

            experiment();

            x += xs;
            y += ys;
            setLocation((int) x, (int) y);
        }
        catch(Exception ex)
        {
            return;
        }
        return;
    }

    public void addedToWorld(World world)
    {
        x = getX();
        y = getY();
        mworld = (myWorld) world; 
    }// addedtowrld

    private void experiment()
    {
        if (mworld.isJumpPad(getX(), getY()+14) )
        { ys = -13;}///
        if (mworld.isGround(getX(), (int)(int)y+height) || mworld.isIce(getX(), (int)y+height) && !mworld.isWater(getX(), getY()))//ground.equals(Color.BLACK))
        { ys = 0;}//x =320; y = 240;}

        if(mworld.isWater(getX(), getY()) && !mworld.isGround(getX(), getY()+16))
        {
            ys -= 0.2;
        }

        if (mworld.isGround(getX(), (int)y+height+1) || mworld.isIce(getX(), (int)y+height+1))
        {
            y -= 0.5;
            ys = 0;
        }///

        if (mworld.isGround(getX(), getY()-height) || mworld.isIce(getX(), getY()-height))
        { y += 1; 
            if (ys < 0){ys = 0;}
        }///

        if (mworld.isGround(getX()+14, getY()) || mworld.isIce(getX()+14, getY()))
        { x -= 1;
            if (xs > 0) {xs = 0;}
        }////

        if (mworld.isGround(getX()-14, getY()) || mworld.isIce(getX()-14, getY()))
        { x += 1;
            if (xs < 0) {xs = 0;}
        }///

    }//experiment

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
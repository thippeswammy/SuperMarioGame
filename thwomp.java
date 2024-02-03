import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class thwomp extends dynamicACTORS
{
    ////////////////////////////
    public myWorld mworld;

    boolean bRemoved = false;
    boolean bKnockedOut = false;

    int health = 2;
    int dmgdelay = 0;
    int kickdelay = 0;
    int usualkick = 1000;

    int stunned = 0;
    int usualstun = 250;

    float x,y, xs,ys;

    int xoffset = 14;
    int yoffset = 14;
    int extrayoffset = 8;

    boolean smash = false;

    // boolean bCanBeKicked = false;
    boolean bMeleeAttack = true;
    int attackdelay = 0;

    public void act()
    {
        if (bRemoved) {return;}

        //        checkbackground();

        if(smash== true && getY() <= mworld.PlayerY)
        {
            smash();
        }

        if(smash == false && y >= 98)
        {
            y = y - 4;
        }

        if(smash == false)
        {
            find();
        }

        setLocation((int) x, (int) y);
    }//actend
    ///////////////////////////////////////////////////////////////////////////////////////////   
    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
        x = getX();
        y = getY();
    } //addedtoworldend
    //////////////////////////////////////////////////////////////////////////////

    public void find()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        Yoshi yoshi = (Yoshi) getOneIntersectingObject(Yoshi.class);

        if(player == null && this.getY() < mworld.PlayerY && smash == false &&
        (this.getX()==mworld.PlayerX || this.getX() == mworld.PlayerX-1 || 
            this.getX()==mworld.PlayerX+1 || this.getX() == mworld.PlayerX-2 || 
            this.getX()== mworld.PlayerX+2))
        {
            smash = true;
        }

        if(mworld.getObjects(Player.class).isEmpty() && yoshi == null && smash == false &&
        this.getY() < mworld.PlayerY && (this.getX()==mworld.PlayerX || 
            this.getX() == mworld.PlayerX-1 || this.getX()==mworld.PlayerX+1 || 
            this.getX() == mworld.PlayerX-2 || this.getX()== mworld.PlayerX+2))
        {
            smash = true;
        }
    }

    public void smash()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        Yoshi yoshi = (Yoshi) getOneIntersectingObject(Yoshi.class);

        setImage("thwomp2.PNG");
        y +=12;
        y += 0.6f;

        if(mworld.getObjects(Yoshi.class).isEmpty() & player != null && player.hurtdelay<=0)
        {
            player.Thwomped();
        }

        if(yoshi != null && yoshi.hurtdelay<=0)
        {
            yoshi.Thwomped();
        }

        if(getY() >= mworld.PlayerY - 20)
        {
            smash = false;
            setImage("thwomp.PNG");
        }
    }

}//classend
//////////////////////////////////////////////////////////////////////EEEEND


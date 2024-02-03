import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * Yep, mutantleg's coding, slightly modified and themed
 * image.
 */
public class KingBoo extends enemy
{
    ////////////////////////////////////
    boolean bDefeated = false;

    int value = 40;

    int goalx;
    int goaly;
    boolean bGoalset = false;

    int bosshealth = 4;

    //accelration
    float xa=0;
    float ya=0;

    //////////

    static GreenfootImage Normal = new GreenfootImage("wormhead.PNG");
    static GreenfootImage Hurt = new GreenfootImage("wormheadhurt.PNG");

    //////////////////////////////////
    public KingBoo()
    {
        bCanBeAttackedFromBelow = false;
        bKillOnFall = false;
        usualstun = 40;
        health = 400;
        maxrun = 3;

        if (mworld.Harder)
        {
            bosshealth = 6;
        }//hrd
        head();
    }//constructor

    protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
    }//added

    ////////////////////////////////////
    public void act() 
    {
        if(getWorld().getObjects(HealthBar.class).isEmpty())
        {
            if(bosshealth == 4)
                getWorld().addObject(new HealthBar(4), 46, 49);

            else if(bosshealth == 6)
                getWorld().addObject(new HealthBar(6), 66, 49);
        }

        if (bRemoved) {return;}

        if (!bDefeated)
        {
            setgoal();
            if (stunned <= 0) {followgoal();}
            if (dmgdelay <= 0)
            {
                setImage(Normal);
            }
            move();
            //            partsfollow();
            hasreachedgoal();
        }
        else
        {
            gravity();
            movement();

        }//endif

        delays();
        Limits();
        setLocation((int) x, (int) y);

        if (bosshealth <= 0 && !bDefeated)
        {
            getWorld().addObject(new Coin(true), getX(), getY());
            bDefeated = true;
            bMeleeAttack = false;
            bCanBeAttackedAtAll = false;
            bKillOnFall = true;
            //            partsfall();
        }//defeat

        ((myWorld) getWorld()).kingHealth = bosshealth;

        if(bDefeated)
        {
            setRotation(getRotation()+8);
        }

        if(getY() > 600)
        {
            getWorld().removeObject(this);
        }

        if (health < 0) { removeme();}
    }  //act  
    /////////////////
    public void removeme()
    {
        bRemoved = true;

        //        for (int i = 0; i < totalparts;i++)
        //        {

        //            getWorld().addObject(new effect(), parts[i].getX(),parts[i].getY());
        //            getWorld().removeObject(parts[i]);
        //        }//next i

        getWorld().addObject(new effect(),getX(),getY());
        getWorld().removeObject(this);
    }//killthis

    //////////
    public void damageStun(int dmg)
    {
        if (dmgdelay <= 0)
        {
            //  ys = 3;   
            // dmgdelay = 10;
            stunned = usualstun;
        }
    }//damstunoverload

    /////////////////////////////////////////////
    private void move()
    {

        if (stunned <= 0)
        {
            xs += xa;
            if (xs> maxrun) {xs = maxrun;}
            if (xs<-maxrun) {xs = -maxrun;}

            ys += ya;
            if (ys > maxrun) {ys = maxrun;}
            if (ys < -maxrun) {ys = -maxrun;}
        }
        else
        {
            if (ys < 6) {ys+= 0.4;}
        }

        if (y > 440 && ys > 0 && dmgdelay <=0) 
        {
            ys = -16; 
            xs = 0;
            stunned = 40;
            dmgdelay = 50;
            Greenfoot.playSound("kick.wav");
            setImage(Hurt);
            health = 100;
            bosshealth--;

            //            parthurt = 0;
        }

        x += xs;
        y += ys;
    }//move

    private void followgoal()
    {
        int x = goalx - getX();
        int y = goaly - getY();
        float length =(float) Math.sqrt((x*x)+(y*y));

        xa = x / length;
        ya = y / length;
    }//

    private void hasreachedgoal()
    {
        if (getX() > goalx+12 || getX() < goalx-12) {return;}

        if (getY() > goaly+12 || getY() < goaly-12) {return;}

        bGoalset = false;
    }//reachedgoal

    private void setgoal()
    {
        if (bGoalset) {return;}
        bGoalset = true;
        goalx = goalx + Greenfoot.getRandomNumber(380)+130;
        if (goalx > 600) {goalx = 70;}
        goaly = Greenfoot.getRandomNumber(200)+110;
    }//setgoal

    private void Limits()
    {
        if(x < 15)
        {
            x++;
        }
        if(x > 625)
        {
            x--;
        }
        if(y < 15)
        {
            y++;
        }
        if(y > 465)
        {
            y--;
        }
    }

    public void head()
    {
        getImage().scale(52,52);
        getImage().setTransparency(50);
    }
    ////////////////////////////////
}//class

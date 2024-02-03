import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;

/**
 * Hazards are just things you cannot destroy
 * but they move around and trying to hurt you
 * but just patterns, no intelligence involved
 */
public class fireball extends FX
{

    public myWorld mworld;

    public int dir = 0;
    private int basey = 0;
    private float angle = 0;
    private int yy = 0;
    private int xp = 0;

    private int dist = 20;
    private int speed = 6;
    private float freq = 15f;

    int miny = 0+8;
    int maxy = 480-8;
    int minx = 0+8;
    int maxx = 640-8;
    int maxcount = 0;
    int mincount = 0;

    int x;
    int y;

    int t = 255;

    public fireball(int ection)
    {
        dir = ection;
    }//altconstructor

    public fireball(int dis, int direction, int spe, float fre)
    {
        dist = dis;
        dir = direction;
        speed = spe;
        freq = fre;
    }//constructor

    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
        basey = getY();
        yy = getY();
        xp = getX();

        x = getX();
        y = getY();

        setLocation(getX(), getY());
    } //addedtoworldend

    public void act() 
    {
        x = getX();
        y = getY();

        huntEnemy();

        //          angle +=freq;
        //          if (angle > 359) {angle = 0;}
        //        yy = (int) (basey + Math.sin(Math.toRadians(angle))*dist);
        // (180/3.1415f)
        maxcount++;

        if (dir == 0) {

            setLocation(x+speed,yy);
            if (maxcount > 90) {mworld.removeObject(this);}
        }//dir0

        if (dir == 16) {
            setLocation(x-speed,yy);
            if (maxcount > 90) {mworld.removeObject(this);}
        }//dir0

        //         if(x < minx+10)
        //         {
        //             dir = 0;
        //         }
        //         if(x > maxx-10)
        //         {
        //             dir = 16;
        //         }
        //         if(y < miny-15)
        //         {
        //             y++;
        //             mworld.removeObject(this);
        //         }

        if(x > 10 && y < 470 && x < 630)
        {  
            checkBackground();
        }

    }    //act

    public void huntEnemy()
    {

        enemy evil = (enemy) getOneIntersectingObject(enemy.class);
        //         KoopaTroopa mean = (KoopaTroopa) getOneIntersectingObject(KoopaTroopa.class);
        //         endboss terrible = (endboss) getOneIntersectingObject(endboss.class);
        //         worm shun = (worm) getOneIntersectingObject(worm.class);
        //         kingpig death = (kingpig) getOneIntersectingObject(kingpig.class);
        //         firedemon eek = (firedemon) getOneIntersectingObject(firedemon.class);
        edemon aah = (edemon) getOneIntersectingObject(edemon.class);
        if (evil != null)
        {
            evil.knockmeout();
        }//endif

        if(aah != null)
        {
            aah.health=-1;
            aah.knockmeout();
        }
    }//huntPlayer end

    public void checkBackground()
    {
        if(x > 10 && y < 470 && x < 630)
        {
            if(mworld.isGround(x+7, y-7) & dir == 0)
            {
                dir = 16;
            }

            if(mworld.isGround(x-7, y-7) & dir == 16)
            {
                dir = 0;
            }

            if(!mworld.isGround(x, y+5))
            {
                yy = yy+3;
            }

            if(mworld.isGround(x,y+5))
            {
                yy = yy-3;
            }
        }
    }
}//class
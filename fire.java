import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * fire - and also the spark
 */
public class fire extends hazard
{

    static GreenfootImage[] frames = new GreenfootImage[4];

    static GreenfootImage[] sparkframes = new GreenfootImage[4];

    private float curframe = 0;
    private float animspeed = 0.3f;

    float dist;

    private float xdist;
    private float ydist;

    float angle = 0;
    private float rotspeed = 0.1f;

    int basex;
    int basey;

    boolean isSpark = false;

    boolean bRotate = true;

    public fire(float ang, float rot, int disx, int disy,boolean spork)
    {
        bRotate = true;
        loadframes();
        isSpark = spork;
        angle = ang;
        rotspeed = rot;
        xdist = disx;
        ydist = disy;
    }//const4withspark

    public fire(boolean spork)
    {
        loadframes();
        isSpark = spork;
        bRotate = true;

        xdist = 0;
        ydist = 0;
        angle = 0;
        rotspeed = 0;
    }//isspark

    public fire(float ang, float rot, int disx, int disy)
    {
        bRotate = true;
        loadframes();

        angle = ang;
        rotspeed = rot;
        xdist = disx;
        ydist = disy;
    }//const3

    public fire(float ang, float rot, int dis)
    {
        bRotate = true;
        loadframes();

        angle = ang;
        rotspeed = rot;
        xdist = dis;
        ydist = dis;
    }//const2

    public fire()
    {
        bRotate = false;
        loadframes();
    }//fire

    public void fireset(float ang, float rot,  int disx, int disy)
    {
        bRotate = true;
        angle = ang;
        rotspeed = rot;
        xdist = disx;
        ydist = disy;
    }//fireset

    public void fireset(float ang, float rot, int dis)
    {
        bRotate = true;
        // loadframes();

        angle = ang;
        rotspeed = rot;
        xdist = dis;
        ydist = dis;
    }//fireset

    protected void addedToWorld(World world)
    {
        basey = getY();
        basex = getX();
    } //addedtoworldend

    private void loadframes()
    {
        if (frames[0] != null) {return;}
        frames[0] = new GreenfootImage("fireframea.gif");
        frames[1] = new GreenfootImage("fireframeb.gif");
        frames[2] = new GreenfootImage("fireframec.gif");
        frames[3] = new GreenfootImage("fireframed.gif");

        sparkframes[0] = new GreenfootImage("sparkframea.gif");
        sparkframes[1] = new GreenfootImage("sparkframeb.gif");
        sparkframes[2] = new GreenfootImage("sparkframec.gif");
        sparkframes[3] = new GreenfootImage("sparkframed.gif");
    }//void

    private void animate(GreenfootImage[] myframes)
    {
        curframe += animspeed;
        if (curframe >= 4 ) {curframe = 0;}

        setImage( myframes[(int) curframe] );
    }//

    private void rotate()
    {
        if (!bRotate) {return;}

        angle += rotspeed;
        if (angle > 360) {angle = 0;}
        if (angle < 0) {angle = 360;}
        int yy = (int) (basey + (Math.sin(Math.toRadians(angle))*ydist));
        int xx = (int) (basex + (Math.cos(Math.toRadians(angle))*xdist));

        setLocation(xx,yy);

    }//rotate

    private void jumpenemy()
    {
        enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);

        if (mutantleg != null 
        && mutantleg.getClass() != edemon.class
        && !mutantleg.bKnockedOut
        ) 
        { mutantleg.ys = -8; }
    }//jumpen

    /////////////////////////////
    public void act() 
    {
        // mworld.setfireCoords(getX(), getY());
        huntPlayer();
        rotate();
        if (!isSpark) {animate(frames);} else { animate(sparkframes); jumpenemy(); }
    }   //act 
}//class

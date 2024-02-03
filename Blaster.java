import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Music here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blaster  extends FX
{
    GreenfootSound music = new GreenfootSound("level.mid");
    GreenfootSound music2 = new GreenfootSound("2.mid");
    GreenfootSound music3 = new GreenfootSound("wingcap.mid");
    int moosic = 1;
    /**
     * Act - do whatever the Music wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!((myWorld) getWorld()).cancelled)
        {
            if(((myWorld) getWorld()).Music.isPlaying() && !((myWorld) getWorld()).cancelled)
            {
                ((myWorld) getWorld()).stopAllAudio();
                ((myWorld) getWorld()).Music.play();
                ((myWorld) getWorld()).cancelled = true;
            }
            if(((myWorld) getWorld()).MUSIC.isPlaying() && !((myWorld) getWorld()).cancelled)
            {
                ((myWorld) getWorld()).stopAllAudio();
                ((myWorld) getWorld()).MUSIC.play();
                ((myWorld) getWorld()).cancelled = true;
            }
            if(((myWorld) getWorld()).BossMusic.isPlaying() && !((myWorld) getWorld()).cancelled)
            {
                ((myWorld) getWorld()).stopAllAudio();
                ((myWorld) getWorld()).BossMusic.play();
                ((myWorld) getWorld()).cancelled = true;
            }
            if(((myWorld) getWorld()).Boss2Music.isPlaying() && !((myWorld) getWorld()).cancelled)
            {
                ((myWorld) getWorld()).stopAllAudio();
                ((myWorld) getWorld()).Boss2Music.play();
                ((myWorld) getWorld()).cancelled = true;
            }
            ((myWorld) getWorld()).cancelled = true;
        }

        if(Greenfoot.mouseClicked(this))
        {
            if(moosic < 3)
            {
                ((myWorld) getWorld()).moosic = true;
                moosic++;
                ((myWorld) getWorld()).stopAllAudio();
                ((myWorld) getWorld()).music(moosic);
            }
            else
            {
                ((myWorld) getWorld()).moosic = false;
                moosic = 0;

                ((myWorld) getWorld()).music.stop();
                ((myWorld) getWorld()).music2.stop();
                ((myWorld) getWorld()).music3.stop();
                ((myWorld) getWorld()).Music.stop();
                ((myWorld) getWorld()).BossMusic.stop();
                ((myWorld) getWorld()).Boss2Music.stop();
                ((myWorld) getWorld()).MUSIC.stop();
            }

            ((myWorld) getWorld()).soundfile = moosic;
            setImage("m"+moosic+".png");
        }
    }    
}

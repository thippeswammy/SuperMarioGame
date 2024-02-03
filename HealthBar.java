import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends FX
{
    GreenfootImage image = new GreenfootImage(80, 20);
    GreenfootImage Health[] = new GreenfootImage[7];
    int health, maxHealth;

    public HealthBar(int bossHealth)
    {
        maxHealth = bossHealth;
        health = bossHealth;
        setImage(image);

        for(int i = 0; i < 7; i++)
        {
            Health[i] = new GreenfootImage(20, 20);
        }

        if(bossHealth <= 4)
        {
            Health[1].setColor(Color.RED);
            Health[1].fillRect(0, 0, 20, 20);
            Health[2].setColor(Color.ORANGE);
            Health[2].fillRect(0, 0, 20, 20);
            Health[3].setColor(Color.YELLOW);
            Health[3].fillRect(0, 0, 20, 20);
            Health[4].setColor(Color.GREEN);
            Health[4].fillRect(0, 0, 20, 20);

            image.drawImage(Health[1], 0, 0);
            image.drawImage(Health[2], 20, 0);
            image.drawImage(Health[3], 40, 0);
            image.drawImage(Health[4], 60, 0);
        }
        else if(bossHealth > 4)
        {
            image.scale(120, 20);
            Health[1].setColor(Color.RED);
            Health[1].fillRect(0, 0, 20, 20);
            Health[2].setColor(Color.ORANGE);
            Health[2].fillRect(0, 0, 20, 20);
            Health[3].setColor(Color.ORANGE);
            Health[3].fillRect(0, 0, 20, 20);
            Health[4].setColor(Color.YELLOW);
            Health[4].fillRect(0, 0, 20, 20);
            Health[5].setColor(Color.YELLOW);
            Health[5].fillRect(0, 0, 20, 20);
            Health[6].setColor(Color.GREEN);
            Health[6].fillRect(0, 0, 20, 20);

            image.drawImage(Health[1], 0, 0);
            image.drawImage(Health[2], 20, 0);
            image.drawImage(Health[3], 40, 0);
            image.drawImage(Health[4], 60, 0);
            image.drawImage(Health[5], 80, 0);
            image.drawImage(Health[6], 100, 0);
        }
    }

    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(((myWorld) getWorld()).kingHealth < this.health)
        {
            this.health--;

            image.clear();
            
            if(health <= 0)
            {
                getWorld().removeObject(this);
                return;
            }

            for(int i = 1; i <= health; i++)
            {
                image.drawImage(Health[i], 20 * i -20, 0);
            }
        }
    }    
}

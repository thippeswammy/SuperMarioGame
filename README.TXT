------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: Super Mario
PURPOSE OF PROJECT: Just for Fun
VERSION or DATE: 2010
HOW TO START THIS PROJECT: Click the "Run" button.
AUTHORS: Valued Programmer - Mutantleg.
         DonaldDuck - put it all together
USER INSTRUCTIONS: Arrow keys to move, down arrow to enter pipes.


import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fish here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fish  extends enemy
{
    /**
     * Act - do whatever the Fish wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public int health = 9000;
    public int damagestun = 0;
    public int flipDelay;
    float y;

    public Fish()
    {
        int number = Greenfoot.getRandomNumber(20)+12;
        getImage().scale(number, number);
        setRotation(0);
    }


    public void act() 
    {
        setLocation(getX()-1, getY()+(int)y);

        if(getY()>=640 || getY()<=0)
        {
            setLocation(getX(), 320);
        }

        if(getX() <= -50)
        {
            setLocation(640+50, getY());
        }
        dmgdelay=0;
    }
}



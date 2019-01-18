import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.*;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import  sun.audio.*;    
import  java.io.*;

//imports
public class PipeComponent extends JComponent implements ActionListener//class PipeComponent has an iheritance relationship with JComponent ("extends")
{
    //instance field variables declared but not initialized to have the scope of the full class and for possible initialization in the constructor
    JFrame frame;//variable frame of type JFrame declared
    Random gen = new Random();//keyword 'new' preps for the construction of a Random variable by preparing memory space
    Graphics2D g2;
    int x1,x2,x3;//multiple variables of the same type may be declared together in this manner
    int y1,y2,y3;
    JLabel background;
    ImageIcon backgroundImage;
    Timer t = new Timer(2, this);
    JPanel panelMain;
    JLabel label2;
    JLabel label3;
    Bird bird;
    boolean scored1 = false;
    boolean scored2 = false;
    boolean scored3 = false;
    boolean collided = false;

    public PipeComponent(JFrame f, Bird bird)
    {   //constructor is where the internal data of the object is defined or where the instance field variables are usually initialized
        frame = f;
        this.bird = bird;
        resetPipes();
        t.start();
        backgroundImage = new ImageIcon("flappyBackground.png");
        background = new JLabel(PipeComponent.resize(backgroundImage, 1000, 700));//resizes the ImageIcon to certain dimensions (refer to the method in this class)
        label2 = new JLabel("Score: " + String.valueOf(bird.getScore()), SwingConstants.CENTER);//prints out the current score to the bottom of the screen(refer to methods in Bird class)
        label3 = new JLabel("Highscore: " + String.valueOf(bird.getHighScore()), SwingConstants.CENTER);
        label2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
        label2.setVerticalAlignment(SwingConstants.BOTTOM);//sets vertical position of variable referenced by label2 to the value of the static and final constant BOTTOM
        label3.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
        label3.setVerticalAlignment(SwingConstants.TOP);
    }

    public void paintComponent(Graphics g)//paintComponent method is again overridden here--exact method signature is used
    {
        g2 = (Graphics2D) g;//variable g2 casted as Graphics2D 
        //casts are required when going from general to specific
        refreshBirdScore();

        if(!bird.getNotHover())
        {
            resetPipes();
        }

        g2.setColor(new Color(0, 225, 0));//method setColor invoked on variable g2 of type Graphics2D to define a new color with specific values
        Rectangle2D.Double x1PipeA = new Rectangle2D.Double(x1,0,150,y1);//x1PipeA of type Rectangle2D.Double represents the top half of the main part of the flappy bird pipe
         //x1PipeA moves across the screen in relation to the values of x1 and y1
        g2.draw(x1PipeA);//draw method invoked on variable g2 to draw the x1PipeA in the JFrame
        g2.fill(x1PipeA);//fill method invoked on variable g2 to fill inthe x1PipeA in the JFrame
        Rectangle2D.Double x1PipeB = new Rectangle2D.Double(x1,y1+200,150,700-y1);//x1PipeB of type Rectangle2D.Double represents the bottom half of the main part of the flappy bird pipe
        //y1+200 creates a 200 space gap between x1PipeA and x1PipeB
         //700-y1 causes x1PipeB to only extend to the end of the frame, based on the value of variable y1
        g2.draw(x1PipeB);
        g2.fill(x1PipeB);

        Rectangle2D.Double x2PipeA = new Rectangle2D.Double(x2,0,150,y2);
        g2.draw(x2PipeA);
        g2.fill(x2PipeA);
        Rectangle2D.Double x2PipeB = new Rectangle2D.Double(x2,y2+200,150,700-y2);
        g2.draw(x2PipeB);
        g2.fill(x2PipeB);

        Rectangle2D.Double x3PipeA = new Rectangle2D.Double(x3,0,150,y3);
        g2.draw(x3PipeA);
        g2.fill(x3PipeA);
        Rectangle2D.Double x3PipeB = new Rectangle2D.Double(x3,y3+200,150,700-y3);
        g2.draw(x3PipeB);
        g2.fill(x3PipeB);

        Rectangle2D.Double x1PipeCrossA = new Rectangle2D.Double(x1-30,y1-75,210,75);//x1PipeCrossA of type Rectangle2D.Double represents the cross-section of the upper pipe
        //x1-30 causes the cross-section to be 30 pixels to the left
        //y1-75 causes the cross-section to start 75 pixels above the end of x1PipeA so the bottom of the cross-section and the bottom of x1PipeA meet
        g2.draw(x1PipeCrossA);
        g2.fill(x1PipeCrossA);
        Rectangle2D.Double x1PipeCrossB = new Rectangle2D.Double(x1-30,y1+200,210,75);//x1PipeCrossB of type Rectangle2D.Double represents the cross-section of the lower pipe
        //y1+200 because the y axis starts at the top of the screen and runs poitively to the bottom; +200 will cause the cross-section to start at the top of x1PipeB, the bottom pipe
        g2.draw(x1PipeCrossB);
        g2.fill(x1PipeCrossB);

        Rectangle2D.Double x2PipeCrossA = new Rectangle2D.Double(x2-30,y2-75,210,75);
        g2.draw(x2PipeCrossA);
        g2.fill(x2PipeCrossA);
        Rectangle2D.Double x2PipeCrossB = new Rectangle2D.Double(x2-30,y2+200,210,75);
        g2.draw(x2PipeCrossB);
        g2.fill(x2PipeCrossB);

        Rectangle2D.Double x3PipeCrossA = new Rectangle2D.Double(x3-30,y3-75,210,75);
        g2.draw(x3PipeCrossA);
        g2.fill(x3PipeCrossA);
        Rectangle2D.Double x3PipeCrossB = new Rectangle2D.Double(x3-30,y3+200,210,75);
        g2.draw(x3PipeCrossB);
        g2.fill(x3PipeCrossB);

        g2.setColor(Color.GREEN);
        Rectangle2D.Double x1StripeA1 = new Rectangle2D.Double(x1 + 20,0,35,y1);//left stipe of the top first pipe
        g2.draw(x1StripeA1);
        g2.fill(x1StripeA1);
        Rectangle2D.Double x1StripeA2 = new Rectangle2D.Double(x1 + 20,y1+200,35,700-y1);//right stripe of the top first pipe
        g2.draw(x1StripeA2);
        g2.fill(x1StripeA2);
        Rectangle2D.Double x1StripeB1 = new Rectangle2D.Double(x1 + 95,0,35,y1);//left stripe of the bottom first pipe
        g2.draw(x1StripeB1);
        g2.fill(x1StripeB1);
        Rectangle2D.Double x1StripeB2 = new Rectangle2D.Double(x1 + 95,y1+200,35,700-y1);//right stripe of the bottom first pipe
        g2.draw(x1StripeB2);
        g2.fill(x1StripeB2);

        Rectangle2D.Double x2StripeA1 = new Rectangle2D.Double(x2 + 20,0,35,y2);
        g2.draw(x2StripeA1);
        g2.fill(x2StripeA1);
        Rectangle2D.Double x2StripeA2 = new Rectangle2D.Double(x2 + 20,y2+200,35,700-y2);
        g2.draw(x2StripeA2);
        g2.fill(x2StripeA2);
        Rectangle2D.Double x2StripeB1 = new Rectangle2D.Double(x2 + 95,0,35,y2);
        g2.draw(x2StripeB1);
        g2.fill(x2StripeB1);
        Rectangle2D.Double x2StripeB2 = new Rectangle2D.Double(x2 + 95,y2+200,35,700-y2);
        g2.draw(x2StripeB2);
        g2.fill(x2StripeB2);

        Rectangle2D.Double x3StripeA1 = new Rectangle2D.Double(x3 + 20,0,35,y3);
        g2.draw(x3StripeA1);
        g2.fill(x3StripeA1);
        Rectangle2D.Double x3StripeA2 = new Rectangle2D.Double(x3 + 20,y3+200,35,700-y3);
        g2.draw(x3StripeA2);
        g2.fill(x3StripeA2);
        Rectangle2D.Double x3StripeB1 = new Rectangle2D.Double(x3 + 95,0,35,y3);
        g2.draw(x3StripeB1);
        g2.fill(x3StripeB1);
        Rectangle2D.Double x3StripeB2 = new Rectangle2D.Double(x3 + 95,y3+200,35,700-y3);
        g2.draw(x3StripeB2);
        g2.fill(x3StripeB2);
    }

    public void refreshBirdScore()//sets the text of the JLabels that hold the scores to their current values
    {
        label2.setText("Score: " + String.valueOf(bird.getScore()));
        label3.setText("Highscore: " + String.valueOf(bird.getHighScore()));
    }

    public boolean checkBirdCollision(int x, int y)//checks collision between the bird and the pipes, given x and y of a certain pipe
    {
        boolean b = false;//assumed that they are not colliding
        if (bird.getBirdX()+bird.getW()*3>x-30 && bird.getBirdX()<x+180)//if the bird is within the width of the pipes
        {
            if(bird.getBirdY()<y)//if the bird is above the bottom of the top pipe
            {
                b = true;//sets b to true
            }
            else if(bird.getBirdY()+bird.getW()*2>y+200)//if the bird is below the top of the bottom pipe
            {
                b = true;//sets b to true
            }
        }
        return b;//returns b, which is true if they are colliding and false if they are not colliding
    }

    public void resetPipes()//sets the pipes to their original positions
    {
        x1 = 800 + 500;//sets the pipes' original starting positions back to off the screen
        x2 = 1200 + 500;
        x3 = 1600 + 500;
        y1 =  gen.nextInt(351) + 50;//sets the ys back to random integers from 0 to 351
        y2 =  gen.nextInt(351) + 50;
        y3 =  gen.nextInt(351) + 50;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(bird.getNotHover())//unless the bird is hovering
        {
            x1 = x1 - 1;
            x2 = x2 - 1;
            x3 = x3 - 1;
        } //otherwise the pipes don't move
        if (x1<=-200) //if the pipe is off the screen, behind the bird
        {
            y1 = gen.nextInt(351) + 50;//sets new random y value
            x1=1000;//sets the x back to off the screen
        }
        else if (x2<=-200) ////if the pipe is off the screen, behind the bird
        {
            y2 = gen.nextInt(351) + 50;//sets new random y value
            x2=1000;//sets the x back to off the screen
        }
        else if (x3<=-200) //if the pipe is off the screen, behind the bird
        {
            y3 = gen.nextInt(351) + 50;//sets new random y value
            x3=1000;//sets the x back to off the screen
        }

        if(!scored1)//if the bird hasn't been scored for that pipe yet
        {
            if (bird.getBirdX()>(x1-5)&&bird.getBirdX()<(x1+5) && bird.getBirdY()<(y1+200) && bird.getBirdY()>(y1))//if the bird is within the place to be scored
            {
                scored1 = true; //has been scored for this pipe
                bird.addScore();//adds 1 to the score of the bird
            }
        }
        else if(bird.getBirdX()>x1+20)//otherwise, if the bird is passed the score place
        {
            scored1 = false;//has not been scored for this pipe yet, for next time around
        }
        if(!scored2)//if the bird hasn't been scored for that pipe yet
        {
            if (bird.getBirdX()>(x2-5)&&bird.getBirdX()<(x2+5) && bird.getBirdY()<(y2+200) && bird.getBirdY()>(y2))//if the bird is within the place to be scored
            {
                scored2 = true;  //has been scored for this pipe
                bird.addScore();//adds 1 to the score of the bird
            }
        }
        else if(bird.getBirdX()>x2+20)//otherwise, if the bird is passed the score place
        {
            scored2 = false;//has not been scored for this pipe yet, for next time around
        }
        if(!scored3)//if the bird hasn't been scored for that pipe yet
        {
            if (bird.getBirdX()>(x3-5)&&bird.getBirdX()<(x3+5) && bird.getBirdY()<(y3+200) && bird.getBirdY()>(y3))//if the bird is within the place to be scored
            {
                scored3 = true;  //has been scored for this pipe
                bird.addScore();//adds 1 to the score of the bird
            }
        }
        else if(bird.getBirdX()>x3+20)//otherwise, if the bird is passed the score place
        {
            scored3 = false;//has not been scored for this pipe yet, for next time around
        }

        if(checkBirdCollision(x1,y1)) bird.die();//if collided with this pipe, bird dies
        else if(checkBirdCollision(x2,y2)) bird.die();//if collided with this pipe, bird dies
        else if(checkBirdCollision(x3,y3)) bird.die();//if collided with this pipe, bird dies

        repaint();//repaints

    }

    public static ImageIcon resize(ImageIcon imageIcon, int width, int height) {//method to resize images
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);//new BufferedImage constructed with explicit parameters defining width, height, and transparency
        Graphics2D g2d = (Graphics2D) bi.createGraphics();//creatGraphics method invoked on object referenced by variable bi with a cast of Graphics2D
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));//define how Graphics2d "g2d" is to be rendered within buffered image
        g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);//new Image drawn based on user input
        g2d.dispose();//after image is drawn, g2d is disposed (deleted) as it is no longer of use
        return new ImageIcon(bi);
    }

}

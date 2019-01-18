import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.geom.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.*;
//imports
public class Starter 
{
    static JFrame frame = new JFrame("Flappy Bird");//creates frame, static so can be used in both static method and static class
    static Bird bird;//declares bird, static so can be used in both static method and static class
    public static void main(String[] args)
    {
        
        frame.setSize(1000,700);/*invoke setSize method on variable referenced by frame with specified 
        int width and height*/
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes JVM when frame closed
        bird = new Bird();//creates new instance of Bird and assigns to variable bird
        PipeComponent logic = new PipeComponent(frame, bird);//construct PipeComponent and pass in frame and assign to
        ClickListener listener1 = new ClickListener(bird, logic);//creates instance of the keylistener for the bird
        frame.addKeyListener(listener1);//adds the key listener to the frame.
        Timer t = new Timer(1,bird);//creates new timer for the bird
        t.start();//starts the bird's timer
        frame.add(bird);//adds the bird to the frame
        frame.setVisible(true);//sets the frame to visible
        frame.add(logic.label2);//adds logic's label2 to the frame
        frame.setVisible(true);//sets the frame to visible
        frame.add(logic.label3);//adds logic's label3 to the frame
        frame.setVisible(true);//sets the frame to visible
        
        frame.add(logic);//adds logic to the frame
        frame.setResizable(false);//sets the frame to not be resizeable
        frame.setVisible(true);//invoke setVisible method on variable referneced by frame with boolean value true
        frame.add(logic.background);//adds the background to the frame
        frame.setVisible(true);

    }

    public static class ClickListener implements KeyListener
    {
        Bird bird;//instance field variables so that clicklistener can invoke methods on the birds and the pipes
        PipeComponent logic;
        public ClickListener(Bird bird, PipeComponent logic)
        {
            this.bird = bird;//sets instance field variables
            this.logic = logic;
        }

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e)// when a key is pressed
        {
            if(e.getKeyCode()==KeyEvent.VK_SPACE)//if they hit the space bar
            {
                bird.boost();//uses teh boost method to change the birds upward velovity to a set positive value
                bird.goFirst(true);//no longer hovering up and down, but now controlledby space bar
            }
            else if(e.getKeyCode()==KeyEvent.VK_R)//if they hit r
            {
                bird.resetScore();//sets highscore back to 0 using the resetScore method
            }
        }

        public void keyReleased(KeyEvent e) {}
    }
}

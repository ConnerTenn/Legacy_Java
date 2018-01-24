package stfx;
import robocode.*;
import java.awt.Color;

/**
 * SimpleTrack - a robot by Mr. Reid
 * Follow a Robot with the gun I have seen and shoot when in range
 */
public class SimpleTrack extends Robot
{
  // Global attribute to tell us whether we are tracking a target
  boolean target = false;
   int counter = 0;   // Count how many loops since I saw the target
   
   /** 
    * run: SimpleTrack's default behavior
    */ 
   public void run() 
   {
      // Decorate your Robot
      this.setColors(Color.green,Color.green,Color.green);

      // Assume no target to follow
      target = false;
      counter = 0;

      // Main loop (infinite - game controls when it's over)
      while(true) 
      {
        counter++;
        if (target)
        {
          // Just stay put 
          this.scan();  // This ensures that it will look for target when it has nothing better to do
        }
        else
        {
          // Look around for a target
          this.turnGunLeft(360);
        }
        
        System.out.println(counter);
        if (target == true && counter > 10)
        {
          target = false;
        }
      }
      
      // Code will never reach here -- and that okay for a change ;)
   }
   
   /**
    * onScannedRobot: What to do when you see another robot
    */
   public void onScannedRobot(ScannedRobotEvent e) 
   {
     // Found a target
     target = true;
     counter = 0;
     System.out.println("Target - Bearing: "+e.getBearing()+" Distance: "+e.getDistance());
     
     // Turn towards the target (perhaps need to lead the target based on distance?)
     this.turnRight(e.getBearing());
     
     // Take a shot if in range
     this.fire(1);
     
   }

   /**
    * onHitByBullet: What to do when you're hit by a bullet
    */
   public void onHitByBullet(HitByBulletEvent e) 
   {
    // Add code here

   }
   
}

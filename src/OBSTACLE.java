import processing.core.PImage;

import java.util.List;

public class OBSTACLE extends Obstacle_schedule {


    public OBSTACLE(String id, Point position, int animationPeriod, List<PImage> images)
    {
        super(id, position, animationPeriod, images);

    }


    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
                scheduler.scheduleEvent(this,
                        new Animation_action(this, 0),
                        (this).getAnimationPeriod());

    }


}

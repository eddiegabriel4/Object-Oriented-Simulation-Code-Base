import processing.core.PImage;

import java.util.List;

public abstract class Obstacle_schedule extends Able_to_animate {

    public Obstacle_schedule(String id, Point position, int animationPeriod, List<PImage> images)
    {
        super(id, position, images, animationPeriod);

    }

    public abstract void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}

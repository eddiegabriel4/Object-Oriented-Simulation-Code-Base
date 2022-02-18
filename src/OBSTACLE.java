import processing.core.PImage;

import java.util.List;

public class OBSTACLE implements Able_to_animate {

    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public int health;
    public int healthLimit;

    public OBSTACLE(String id, Point position, int animationPeriod, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.animationPeriod = animationPeriod;

    }

    public Point getPosition() {
        return position;
    }

    public void changePosition(Point position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int points) {
        this.health = points;
    }

    public String getID() {return id;}

    public PImage getCurrentImage() {
        return (this).images.get((this).imageIndex);

    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }


    public void executeAnimationAction(Animation_action animation_action,
                                       EventScheduler scheduler)
    {
        (this).nextImage();

        if (animation_action.repeatCount != 1) {
            scheduler.scheduleEvent(this,
                    new Animation_action(this,
                            Math.max(animation_action.repeatCount - 1,
                                    0)),
                    (this).getAnimationPeriod());
        }
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
                scheduler.scheduleEvent(this,
                        new Animation_action(this, 0),
                        (this).getAnimationPeriod());

    }


}

import processing.core.PImage;

import java.util.List;

public abstract class Able_to_animate extends Entity {

    private int animationPeriod;


    public Able_to_animate(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images);
        this.animationPeriod = animationPeriod;


    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void executeAnimationAction(Animation_action animation_action,
                                       EventScheduler scheduler) {
        (this).nextImage();

        if (animation_action.repeatCount != 1) {
            scheduler.scheduleEvent(this,
                    new Animation_action(this,
                            Math.max(animation_action.repeatCount - 1,
                                    0)),
                    (this).getAnimationPeriod());
        }
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this,
                new Activity_Action((Able_to_activate) this, world, imageStore),
                ((Able_to_activate)this).getActionPeriod());
        scheduler.scheduleEvent(this,
                new Animation_action(this,0),
                (this).getAnimationPeriod());
    }




}

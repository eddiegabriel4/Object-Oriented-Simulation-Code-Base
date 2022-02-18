import processing.core.PImage;

import java.util.List;

public class TREE implements Able_to_animate, Able_to_activate, Transformable, Entity {

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


    public TREE(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;

    }

    public String getID() {return id;}

    public Point getPosition() {
        return position;
    }

    public void changePosition(Point position) {
        this.position = position;
    }

    public void changeHealth(int points) {
        this.health = points;
    }

    public int getHealth() {
        return health;
    }

    public PImage getCurrentImage() {
        return (this).images.get((this).imageIndex);

    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void executeTreeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        if (!this.transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        return this.transformTree(world, scheduler, imageStore);

    }

    public boolean transformTree(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = new STUMP(this.id, this.position,
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
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

    public void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler) {
        this.executeTreeActivity(activity_action.world,
                activity_action.imageStore, scheduler);
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation_action(this, 0),
                (this).getAnimationPeriod());
    }



}

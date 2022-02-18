import processing.core.PImage;

import java.util.List;

public class SAPLING implements Able_to_animate, Able_to_activate, Transformable, Entity {

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

    public SAPLING(
            String id,
            Point position,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = Functions.SAPLING_ACTION_ANIMATION_PERIOD;
        this.animationPeriod = Functions.SAPLING_ACTION_ANIMATION_PERIOD;
        this.health = Functions.SAPLING_HEALTH_LIMIT;

    }

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

    public void executeSaplingActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        this.health++;
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
        return this.transformSapling(world, scheduler, imageStore);
    }

    public boolean transformSapling(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.health <= 0) {
            STUMP stump = new STUMP(this.id, this.position,
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            TREE tree = new TREE("tree_" + this.id, this.position,
                    Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }

    public void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler) {
        this.executeSaplingActivity(activity_action.world,
                activity_action.imageStore, scheduler);
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
                new Activity_Action(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation_action(this, 0),
                (this).getAnimationPeriod());
    }
}

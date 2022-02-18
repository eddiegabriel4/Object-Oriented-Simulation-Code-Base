import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FAIRY implements Able_to_animate, Able_to_activate, Entity {

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


    public FAIRY(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;

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

    public PImage getCurrentImage() {
        return (this).images.get((this).imageIndex);

    }

    public String getID() {return id;}

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void executeFairyActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fairyTarget =
                world.findNearest(this.position, new ArrayList<>(Arrays.asList(STUMP.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                SAPLING sapling = new SAPLING("sapling_" + this.id, tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(world, imageStore, scheduler);
            }
        }

        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.actionPeriod);
    }

    public boolean moveToFairy(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (this.position.adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = target.getPosition().nextPositionFairy(this, world);

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
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
        this.executeFairyActivity(activity_action.world,
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

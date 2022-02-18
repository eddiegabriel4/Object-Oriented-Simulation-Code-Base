import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DUDE_NOT_FULL implements Able_to_animate, Able_to_activate, Not_full_move, Entity {

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

    public DUDE_NOT_FULL(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.resourceLimit = resourceLimit;
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

    public void executeDudeNotFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(this.position, new ArrayList<>(Arrays.asList(TREE.class, SAPLING.class)));

        if (!target.isPresent() || !this.moveToNotFull(world,
                target.get(),
                scheduler)
                || !this.transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            DUDE_FULL miner = new DUDE_FULL(this.id, this.position, this.actionPeriod,
                    this.animationPeriod,
                    this.resourceLimit,
                    this.images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }

    public boolean moveToNotFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (this.position.adjacent(target.getPosition())) {
            this.resourceCount += 1;
            target.changeHealth(target.getHealth() - 1);
            return true;
        } else {
            Point nextPos = world.nextPositionDude(this, target.getPosition());

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
        this.executeDudeNotFullActivity(activity_action.world,
                activity_action.imageStore, scheduler);
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation_action(this,0),
                (this).getAnimationPeriod());
    }

}

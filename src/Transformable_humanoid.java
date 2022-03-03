import processing.core.PImage;

import java.util.List;
import java.util.Optional;



public abstract class Transformable_humanoid extends Able_to_move {

    private int resourceLimit;
    private int resourceCount;


    public Transformable_humanoid(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;

    }

    public boolean transformHumanoid(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {

        if (getTransformCase()) {
            Transformable_humanoid miner = getMiner();

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }

    protected abstract Transformable_humanoid getMiner();

    protected abstract boolean getTransformCase();

    public int getResourceCount() {return this.resourceCount;}

    public void changeResourceCount(int x) { this.resourceCount = x; }

    public int getResourceLimit() {return this.resourceLimit; }

    /*

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler);

     */

}


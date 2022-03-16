import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DUDE_NOT_FULL extends Transformable_humanoid {




    public DUDE_NOT_FULL(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    /*


    public void executeDudeNotFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(TREE.class, SAPLING.class)));

        if (!target.isPresent() || !this.moveToNotFull(world,
                target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

     */

    protected void doThing2(WorldModel world, EventScheduler scheduler, ImageStore imageStore){

    }


    protected void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Optional<Entity> Target){

        scheduler.scheduleEvent(this, new Activity_Action(this, world, imageStore), this.getActionPeriod());
    }

    protected boolean getCase1(Optional<Entity> target, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return !target.isPresent() || !this.move_humanoid(world,
                target.get(), scheduler) || !this.transformHumanoid(world, scheduler, imageStore);
    }

    protected Optional<Entity> getTarget(WorldModel world){

        return world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(TREE.class, SAPLING.class)));
    }

    /*

    public boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {

        if (this.getResourceCount() >= this.getResourceLimit()) {
            DUDE_FULL miner = new DUDE_FULL(this.getID(), this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getResourceLimit(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }

     */

    protected boolean getTransformCase(){
        return this.getResourceCount() >= this.getResourceLimit();
    }

    protected Transformable_humanoid getMiner(){
        return new DUDE_FULL(this.getID(), this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());
    }


    /*



    public boolean moveToNotFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            this.changeResourceCount(this.getResourceCount() + 1);
            target.changeHealth(target.getHealth() - 1);
            return true;

        } else {
            Point nextPos = world.nextPositionDude(this, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

     */

    protected Predicate<Point> canPassThrough(WorldModel world) {
        Predicate<Point> withinBounds = p -> world.withinBounds(p);
        Predicate<Point> last = p -> withinBounds.test(p) && (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == STUMP.class);
        return last;
    }



    protected boolean doMoveThing1(WorldModel world, Entity Target, EventScheduler scheduler){

        this.changeResourceCount(this.getResourceCount() + 1);
        Target.changeHealth(Target.getHealth() - 1);
        return true;

    }





}

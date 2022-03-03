import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DUDE_FULL extends Transformable_humanoid {


    public DUDE_FULL(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);

    }

    /*


    public void executeDudeFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(HOUSE.class)));

        if (fullTarget.isPresent() && this.moveToFull(world,
                fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

     */

    protected void doThing2(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.getActionPeriod());
    }

    protected void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Optional<Entity> Target) {

        this.transformHumanoid(world, scheduler, imageStore);
    }

    protected boolean getCase1(Optional<Entity> target, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return target.isPresent() && this.move_humanoid(world,
                target.get(), scheduler);
    }

    protected Optional<Entity> getTarget(WorldModel world) {

        return world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(HOUSE.class)));
    }

    /*

    public boolean transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (1 == 1) {

            DUDE_NOT_FULL miner = new DUDE_NOT_FULL(this.getID(), this.getPosition(), this.getActionPeriod(),
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
        return true;
    }

    protected Transformable_humanoid getMiner(){
        return new DUDE_NOT_FULL(this.getID(), this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());
    }


    /*



    public boolean moveToFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        }

        else {
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



    protected Point getMovePosition(WorldModel world, Entity Target){
        return world.nextPositionDude(this, Target.getPosition());
    }

    protected boolean doMoveThing1(WorldModel world, Entity Target, EventScheduler scheduler){
        return true;
    }









}

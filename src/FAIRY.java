import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FAIRY extends Able_to_move {


    public FAIRY(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {

        super(id, position, images, actionPeriod, animationPeriod);

    }

    /*




    public void executeFairyActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        Optional<Entity> fairyTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(STUMP.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                SAPLING sapling = new SAPLING("sapling_" + this.getID(), tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(world, imageStore, scheduler);
            }
        }


            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.getActionPeriod());

    }

     */



    protected void doThing2(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.getActionPeriod());
    }


    protected void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Optional<Entity> Target){

        Point tgtPos = Target.get().getPosition();

        if (this.move_humanoid(world, Target.get(), scheduler)) {
            SAPLING sapling = new SAPLING("sapling_" + this.getID(), tgtPos,
                    imageStore.getImageList(Functions.SAPLING_KEY));

            world.addEntity(sapling);
            sapling.scheduleActions(world, imageStore, scheduler);
        }
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.getActionPeriod());
    }

    protected boolean getCase1(Optional<Entity> target, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return target.isPresent();
    }

    protected Optional<Entity> getTarget(WorldModel world){

        return world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(STUMP.class)));
    }


    /*



    public boolean moveToFairy(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;

        } else {
            Point nextPos = target.getPosition().nextPositionFairy(this, world);

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
        return Target.getPosition().nextPositionFairy(this, world);
    }

    protected boolean doMoveThing1(WorldModel world, Entity Target, EventScheduler scheduler){

        world.removeEntity(Target);
        scheduler.unscheduleAllEvents(Target);
        return true;

    }






}

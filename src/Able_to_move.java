import processing.core.PImage;

import java.util.List;
import java.util.Optional;



public abstract class Able_to_move extends Able_to_activate {



    public Able_to_move(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);

    }

    public void executeHumanoidActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> Target = getTarget(world);

        if (getCase1(Target, world, scheduler, imageStore))
        {
            doThing1(world, scheduler, imageStore, Target);
        }
        else {
            doThing2(world, scheduler, imageStore);
        }
    }

    public boolean move_humanoid(WorldModel world, Entity Target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(Target.getPosition())) {
            doMoveThing1(world, Target, scheduler);
            return true;

        }
        else {
            Point nextPos = getMovePosition(world, Target);

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

    protected abstract Point getMovePosition(WorldModel world, Entity Target);

    protected abstract boolean doMoveThing1(WorldModel world, Entity Target, EventScheduler scheduler);


    public void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler) {
        this.executeHumanoidActivity(activity_action.world,
                activity_action.imageStore, scheduler);
    }

    protected abstract void doThing2(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected abstract void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Optional<Entity> Target);

    protected abstract Optional<Entity> getTarget(WorldModel world);

    protected abstract boolean getCase1(Optional<Entity> Target, WorldModel world, EventScheduler scheduler, ImageStore imageStore);




    /*

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler);

     */

}


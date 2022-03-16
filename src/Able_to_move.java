import processing.core.PImage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


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

            PathingStrategy nextPos = new AStarPathingStrategy();

            BiPredicate<Point, Point> withinReach = (p1, p2) -> PathingStrategy.CARDINAL_NEIGHBORS.apply(p1).anyMatch(p -> p.equals(p2));

            List<Point> path = nextPos.computePath(this.getPosition(), Target.getPosition(), this.canPassThrough(world), withinReach, PathingStrategy.CARDINAL_NEIGHBORS);

            if (path != null) {


                if (!this.getPosition().equals(path.get(0))) {
                    Optional<Entity> occupant = world.getOccupant(path.get(0));
                    if (occupant.isPresent()) {
                        scheduler.unscheduleAllEvents(occupant.get());
                    }

                    world.moveEntity(this, path.get(0));
                }
                return false;
            }
            return false;
        }

    }

    /*



    public Point nextPosition(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);

        if (horiz == 0 || !this.tester(world, newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);

            if (vert == 0 || !this.tester(world, newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

     */



    protected abstract Predicate<Point> canPassThrough(WorldModel world);




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


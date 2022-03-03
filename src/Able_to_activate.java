import processing.core.PImage;

import java.util.List;

public abstract class Able_to_activate extends Able_to_animate{

    private int actionPeriod;


    public Able_to_activate(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() { return this.actionPeriod; }



    public abstract void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler);

    /*

    public void executeAllActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        doThing0();
        Optional<Entity> Target = getTarget(world);

        if (getCase1(Target, world, scheduler, imageStore))
        {
            doThing1(world, scheduler, imageStore, Target);
        }
        else {
            doThing2(world, scheduler, imageStore);
        }
    }

    protected abstract void doThing0();

    protected abstract void doThing2(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected abstract void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Optional<Entity> Target);

    protected abstract Optional<Entity> getTarget(WorldModel world);

    protected abstract boolean getCase1(Optional<Entity> Target, WorldModel world, EventScheduler scheduler, ImageStore imageStore);





    public void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler) {
        this.executeAllActivity(activity_action.world,
                activity_action.imageStore, scheduler);
    }

     */




}

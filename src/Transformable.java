import processing.core.PImage;

import java.util.List;


public abstract class Transformable extends Able_to_activate {

    private int health;
    private int healthLimit;

    public Transformable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int health){
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int points) {
        this.health = points;
    }

    public void executePlantActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        doThing0();

        if (getCase1(world, scheduler, imageStore))
        {
            doThing1(world, scheduler, imageStore);
        }
    }

    public boolean transform_greenery(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            STUMP stump = new STUMP(this.getID(), this.getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }
        else if (getTransformCase1(world, scheduler, imageStore)){
            doTransformThing1(world, scheduler, imageStore);
        }

        return false;
    }

    protected abstract boolean getTransformCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected abstract boolean doTransformThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore);


    public void executeActivityAction(Activity_Action activity_action, EventScheduler scheduler) {
        this.executePlantActivity(activity_action.world,
                activity_action.imageStore, scheduler);
    }



    protected abstract void doThing0();

    protected abstract void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected abstract boolean getCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore);






    /*


    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);

     */


}

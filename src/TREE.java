import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class TREE extends Transformable {



    public TREE(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images) {
        super(id, position, images, actionPeriod, animationPeriod, health);

    }

    /*


    public void executeTreeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        if (!this.transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

     */

    protected void doThing0() {

    }


    protected void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.getActionPeriod());
    }

    protected boolean getCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return !this.transform_greenery(world, scheduler, imageStore);
    }

    /*



    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        return this.transformTree(world, scheduler, imageStore);

    }
    /*



    public boolean transformTree(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new STUMP(this.getID(), this.getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

     */

    protected boolean getTransformCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        return true;
    }

    protected boolean doTransformThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        return false;
    }



}

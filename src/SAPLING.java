import processing.core.PImage;

import java.util.List;


public class SAPLING extends Transformable {

    public SAPLING(
            String id,
            Point position,
            List<PImage> images) {

        super(id, position, images, Functions.SAPLING_ACTION_ANIMATION_PERIOD,
                Functions.SAPLING_ACTION_ANIMATION_PERIOD, Functions.SAPLING_HEALTH_LIMIT);

    }

    /*


    public void executeSaplingActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        this.changeHealth(this.getHealth() + 1);
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new Activity_Action(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

     */


    protected void doThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity_Action(this, world, imageStore),
                this.getActionPeriod());
    }

    protected void doThing0() {
        this.changeHealth(this.getHealth() + 1);
    }

    protected boolean getCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return !this.transform_greenery(world, scheduler, imageStore);
    }

    /*

    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        return this.transformSapling(world, scheduler, imageStore);
    }



    public boolean transformSapling(
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
        else if (this.getHealth() >= this.getHealthLimit()) {
            TREE tree = new TREE("tree_" + this.getID(), this.getPosition(),
                    Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }

     */

    protected boolean getTransformCase1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        return (this.getHealth() >= this.getHealthLimit());
    }



    protected boolean doTransformThing1(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        TREE tree = new TREE("tree_" + this.getID(), this.getPosition(),
                Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                Functions.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                imageStore.getImageList(Functions.TREE_KEY));

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(tree);
        tree.scheduleActions(world, imageStore, scheduler);

        return true;
    }




}

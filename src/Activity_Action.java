public class Activity_Action implements Action {

    public Able_to_activate entity;
    public WorldModel world;
    public ImageStore imageStore;



    public Activity_Action(Able_to_activate entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }



    public void executeAction(EventScheduler scheduler) {

        entity.executeActivityAction(this, scheduler);

    }


}

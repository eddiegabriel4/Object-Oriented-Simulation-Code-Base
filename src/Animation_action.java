public class Animation_action implements Action {

    public Able_to_animate entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;


    public Animation_action(Able_to_animate entity, int repeatCount)
    {

        this.entity = entity;
        this.repeatCount = repeatCount;

    }



    public void executeAction(EventScheduler scheduler) {

        entity.executeAnimationAction(this, scheduler);

    }




}

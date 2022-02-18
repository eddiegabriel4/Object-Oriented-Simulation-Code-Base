public interface Able_to_animate extends Entity {

    public int getAnimationPeriod();

    public void executeAnimationAction(Animation_action animation_action,
                                       EventScheduler scheduler);


    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}

import java.util.Optional;

public interface Not_full_move {

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler);

}

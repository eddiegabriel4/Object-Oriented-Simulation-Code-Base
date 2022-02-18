import java.util.Optional;

public interface Full_move {

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler);

}



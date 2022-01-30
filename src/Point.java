import processing.core.PImage;

import java.util.List;
import java.util.Optional;

/**
 * A simple class representing a location in 2D space.
 */
public final class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public Entity createStump(
            String id,
            List<PImage> images)
    {
        return new Entity(EntityKind.STUMP, id, this, images, 0, 0,
                0, 0, 0, 0);
    }

    public Optional<Entity> nearestEntity(
            List<Entity> entities)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = Functions.distanceSquared(nearest.position, this);

            for (Entity other : entities) {
                int otherDistance = Functions.distanceSquared(other.position, this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public Entity createSapling(
            String id,
            List<PImage> images)
    {
        return new Entity(EntityKind.SAPLING, id, this, images, 0, 0,
                Functions.SAPLING_ACTION_ANIMATION_PERIOD, Functions.SAPLING_ACTION_ANIMATION_PERIOD, 0, Functions.SAPLING_HEALTH_LIMIT);
    }

    public Entity createDudeFull(
            String id,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new Entity(EntityKind.DUDE_FULL, id, this, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    public Entity createDudeNotFull(
            String id,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new Entity(EntityKind.DUDE_NOT_FULL, id, this, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    public Entity createFairy(
            String id,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.FAIRY, id, this, images, 0, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    public Entity createTree(
            String id,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new Entity(EntityKind.TREE, id, this, images, 0, 0,
                actionPeriod, animationPeriod, health, 0);
    }

    public Entity createObstacle(
            String id, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, this, images, 0, 0, 0,
                animationPeriod, 0, 0);
    }

    public Entity createHouse(
            String id, List<PImage> images)
    {
        return new Entity(EntityKind.HOUSE, id, this, images, 0, 0, 0,
                0, 0, 0);
    }

    public Point nextPositionFairy(
            Entity entity, WorldModel world)
    {
        int horiz = Integer.signum(this.x - entity.position.x);
        Point newPos = new Point(entity.position.x + horiz, entity.position.y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(this.y - entity.position.y);
            newPos = new Point(entity.position.x, entity.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = entity.position;
            }
        }

        return newPos;
    }

    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y
                && Math.abs(this.x - p2.x) == 1);
    }
}

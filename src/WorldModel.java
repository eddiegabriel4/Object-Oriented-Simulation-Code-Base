import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    public int numRows;
    public int numCols;
    public Background background[][];
    public Entity occupancy[][];
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell( pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.changePosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell( pos) != null;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<Entity> findNearest(Point pos, List<Class> list)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind: list)
        {
            for (Entity entity : this.entities) {
                if (entity.getClass() == kind) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.changePosition(pos);
        }
    }

    /*


    public Point nextPositionDude(
            Entity entity, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - entity.getPosition().x);
        Point newPos = new Point(entity.getPosition().x + horiz, entity.getPosition().y);

        if (horiz == 0 || this.isOccupied(newPos) && this.getOccupancyCell(newPos).getClass() != STUMP.class) {
            int vert = Integer.signum(destPos.y - entity.getPosition().y);
            newPos = new Point(entity.getPosition().x, entity.getPosition().y + vert);

            if (vert == 0 || this.isOccupied(newPos) &&  this.getOccupancyCell(newPos).getClass() != STUMP.class) {
                newPos = entity.getPosition();
            }
        }

        return newPos;
    }


    public Point nextPositionFairy(
            Entity entity, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - entity.getPosition().x);
        Point newPos = new Point(entity.getPosition().x + horiz, entity.getPosition().y);

        if (horiz == 0 || this.isOccupied(newPos) && true){
            int vert = Integer.signum(destPos.y - entity.getPosition().y);
            newPos = new Point(entity.getPosition().x, entity.getPosition().y + vert);

            if (vert == 0 || this.isOccupied(newPos) && true) {
                newPos = entity.getPosition();
            }
        }

        return newPos;
    }

     */

    public void setBackground(
            Point pos, Background background)
    {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell(pos, background);
        }
    }
    public void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }


    public Optional<PImage> getBackgroundImage(
            Point pos)
    {
        if (this.withinBounds(pos)) {
            return Optional.of((this.getBackgroundCell(pos)).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }


    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void removeEntity(Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }
}

import processing.core.PImage;

import java.util.List;

public class HOUSE implements Entity{

    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public int health;
    public int healthLimit;

    public HOUSE(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;

    }

    public Point getPosition() {
        return position;
    }

    public void changePosition(Point position) {
        this.position = position;
    }

    public String getID() {return id;}

    public int getHealth() {
        return health;
    }

    public void changeHealth(int points) {
        this.health = points;
    }

    public PImage getCurrentImage() {
        return (this).images.get((this).imageIndex);

    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
}

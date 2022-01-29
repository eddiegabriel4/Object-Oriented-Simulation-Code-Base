import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public final class ImageStore
{
    public Map<String, List<PImage>> images;
    public List<PImage> defaultImages;

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key) {
        return this.images.getOrDefault(key, this.defaultImages);
    }

    public void loadImages(
            Scanner in, PApplet screen)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                Functions.processImageLine(this.images, in.nextLine(), screen);
            }
            catch (NumberFormatException e) {
                System.out.println(
                        String.format("Image format error on line %d",
                                lineNumber));
            }
            lineNumber++;
        }
    }

    public void load(
            Scanner in, WorldModel world)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), world)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }

    public boolean processLine(
            String line, WorldModel world)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[Functions.PROPERTY_KEY]) {
                case Functions.BGND_KEY:
                    return this.parseBackground(properties, world);
                case Functions.DUDE_KEY:
                    return this.parseDude(properties, world);
                case Functions.OBSTACLE_KEY:
                    return this.parseObstacle(properties, world);
                case Functions.FAIRY_KEY:
                    return this.parseFairy(properties, world);
                case Functions.HOUSE_KEY:
                    return this.parseHouse(properties, world);
                case Functions.TREE_KEY:
                    return this.parseTree(properties, world);
                case Functions.SAPLING_KEY:
                    return this.parseSapling(properties, world);
            }
        }

        return false;
    }

    public boolean parseBackground(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.BGND_COL]),
                    Integer.parseInt(properties[Functions.BGND_ROW]));
            String id = properties[Functions.BGND_ID];
            world.setBackground(pt,
                    new Background(id, this.getImageList(id)));
        }

        return properties.length == Functions.BGND_NUM_PROPERTIES;
    }

    public boolean parseSapling(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.SAPLING_COL]),
                    Integer.parseInt(properties[Functions.SAPLING_ROW]));
            String id = properties[Functions.SAPLING_ID];
            int health = Integer.parseInt(properties[Functions.SAPLING_HEALTH]);
            Entity entity = new Entity(EntityKind.SAPLING, id, pt, this.getImageList(Functions.SAPLING_KEY), 0, 0,
                    Functions.SAPLING_ACTION_ANIMATION_PERIOD, Functions.SAPLING_ACTION_ANIMATION_PERIOD, health, Functions.SAPLING_HEALTH_LIMIT);
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.SAPLING_NUM_PROPERTIES;
    }

    public boolean parseDude(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.DUDE_COL]),
                    Integer.parseInt(properties[Functions.DUDE_ROW]));
            Entity entity = pt.createDudeNotFull(properties[Functions.DUDE_ID],
                    Integer.parseInt(properties[Functions.DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Functions.DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Functions.DUDE_LIMIT]),
                    this.getImageList(Functions.DUDE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.DUDE_NUM_PROPERTIES;
    }
    public boolean parseFairy(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.FAIRY_COL]),
                    Integer.parseInt(properties[Functions.FAIRY_ROW]));
            Entity entity = pt.createFairy(properties[Functions.FAIRY_ID],
                    Integer.parseInt(properties[Functions.FAIRY_ACTION_PERIOD]),
                    Integer.parseInt(properties[Functions.FAIRY_ANIMATION_PERIOD]),
                    this.getImageList(Functions.FAIRY_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.FAIRY_NUM_PROPERTIES;
    }
    public boolean parseTree(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.TREE_COL]),
                    Integer.parseInt(properties[Functions.TREE_ROW]));
            Entity entity = pt.createTree(properties[Functions.TREE_ID],
                    Integer.parseInt(properties[Functions.TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Functions.TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Functions.TREE_HEALTH]),
                    this.getImageList(Functions.TREE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.TREE_NUM_PROPERTIES;
    }
    public boolean parseObstacle(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.OBSTACLE_COL]),
                    Integer.parseInt(properties[Functions.OBSTACLE_ROW]));
            Entity entity = pt.createObstacle(properties[Functions.OBSTACLE_ID],
                    Integer.parseInt(properties[Functions.OBSTACLE_ANIMATION_PERIOD]),
                    this.getImageList(
                            Functions.OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.OBSTACLE_NUM_PROPERTIES;
    }

    public boolean parseHouse(
            String[] properties, WorldModel world)
    {
        if (properties.length == Functions.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Functions.HOUSE_COL]),
                    Integer.parseInt(properties[Functions.HOUSE_ROW]));
            Entity entity = pt.createHouse(properties[Functions.HOUSE_ID],
                    this.getImageList(
                            Functions.HOUSE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.HOUSE_NUM_PROPERTIES;
    }
}

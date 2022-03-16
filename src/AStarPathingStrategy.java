import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        LinkedHashMap<Node, Point> openList = new LinkedHashMap<>();
        LinkedHashMap<Node, Point> closedList = new LinkedHashMap<>();
        openList.put(new Node(start), start);

        Predicate<Point> inClosedList = (n) -> !closedList.containsValue(n);

        while (!openList.isEmpty()) {
            Node current = openList.keySet().stream().min(Comparator.comparing(n -> n.getF())).get();
            openList.remove(current);


            if (withinReach.test(current.point, end)) {
                return build_path(current);
            }

            List<Point> validNeighbors = potentialNeighbors.apply(current.point).filter(canPassThrough).filter(inClosedList).collect(Collectors.toList());
            for (Point point : validNeighbors) {
                Node node = new Node(point);
                node.setPriorPoint(current);
                node.compute_f(start, end);
                if (!openList.containsValue(point)) {
                    openList.put(node, point);
                }
            }
            closedList.put(current, current.point);
        }

        closedList.clear();
        openList.clear();


        return null;


    }

    public List<Point> build_path(Node node) {

        List<Point> path = new ArrayList<>();

        while (node.getPriorPoint() != null) {
            path.add(node.point);
            node = node.getPriorPoint();
        }

        Collections.reverse(path);

        return path;


    }
}



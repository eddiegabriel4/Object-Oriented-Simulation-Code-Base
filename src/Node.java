public class Node {

    public int g;
    public int h;
    public int f;
    public Point point;
    public Node priorPoint;

    public Node (Point point) {

        this.point = point;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.priorPoint = priorPoint;

    }

    public void compute_g(Point start) {
        int gnew =  Math.abs(this.point.y - start.y) + Math.abs(this.point.x - start.x);
        if (gnew < this.g ) {
            this.g = gnew;
        }
    }

    public void compute_h(Point end) {
        this.h = Math.abs(this.point.y - end.y) + Math.abs(this.point.x - end.x);
    }

    public void compute_f(Point start, Point end) {
        compute_g(start);
        compute_h(end);
        this.f = g + h;
    }

    public int getF() {
        return this.f;
    }

    public void setPriorPoint(Node point) {
        this.priorPoint = point;
    }

    public Node getPriorPoint() {
        return this.priorPoint;
    }

}

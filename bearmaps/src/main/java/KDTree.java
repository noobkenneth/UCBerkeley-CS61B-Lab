import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;

public class KDTree {
    Point medianPoint;
    public KDTree(Collection<Node> list) {
        ArrayList<Point> listOfPoints = new ArrayList<>();
        for (Node x : list) {
            listOfPoints.add(new Point(x.id, GraphDB.projectToX(x.lon, x.lat),
                    GraphDB.projectToY(x.lon, x.lat)));
        }
        medianPoint = kdTreeHelper(listOfPoints, 0);
    }
    public Point kdTreeHelper(ArrayList<Point> points, int depth) {
        Comparator<Point> comp;
        // Select axis based on depth so that axis cycles through all valid values
        int axis = depth % 2;
        if (axis == 0) {
            comp = new SortbyX();
        } else {
            comp = new SortbyY();
        }

        // Sort point list and choose median as pivot element
        //select median by axis from pointList;
        Collections.sort(points, comp);
        int medianIndex = points.size() / 2;
        Point median = points.get(medianIndex);
        // Create node and construct subtree
        if (points.size() == 1) {
            return median;
        }
        if (points.size() == 2) {
            if (comp.compare(points.get(0), median) < 0) {
                median.left = points.get(0);
            } else {
                median.right = points.get(0);
            }
            return median;
        }


        ArrayList<Point> leftSidePoints = new ArrayList(points.subList(0, medianIndex));
        ArrayList<Point> rightSidePoints = new ArrayList(points.subList(medianIndex
                + 1, points.size()));
        median.left = kdTreeHelper(leftSidePoints, depth + 1);
        median.right = kdTreeHelper(rightSidePoints, depth + 1);

        return median;
    }


    public class SortbyX implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return Double.compare(a.x, b.x);
        }
    }


    public class SortbyY implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return Double.compare(a.y, b.y);
        }
    }


    public Point nearest(Point q) {
        return nearestHelper(medianPoint, q, medianPoint, true);
    }


    public Point nearestHelper(Point thisPoint, Point p, Point hello, boolean y) {
        if (thisPoint == null) {
            return hello;
        }
        if (thisPoint.x == p.x && thisPoint.y == p.y) {
            return thisPoint;
        }
        if (euclidean(thisPoint.x, p.x, thisPoint.y, p.y)
                < euclidean(hello.x, p.x, hello.y, p.y)) {
            hello = thisPoint;
        }
        double lineDistance = comparePoints(p, thisPoint, y);
        if (lineDistance < 0) {
            hello = nearestHelper(thisPoint.left, p, hello, !y);
            if (Math.pow(euclidean(p.x, hello.x, p.y, hello.y), 2)
                    >= lineDistance * lineDistance) {
                hello = nearestHelper(thisPoint.right, p, hello, !y);
            }
        } else {
            hello = nearestHelper(thisPoint.right, p, hello, !y);
            if (Math.pow(euclidean(p.x, hello.x, p.y, hello.y), 2)
                    >= lineDistance * lineDistance) {
                hello = nearestHelper(thisPoint.left, p, hello, !y);
            }
        }
        return hello;
    }

    static double euclidean(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static class Point {
        long nodeID;
        double x;
        double y;
        Point left;
        Point right;
        Point(long nodeID, double x, double y) {
            this.nodeID = nodeID;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "" + nodeID;
        }
    }

    private double comparePoints(Point p, Point n, boolean evenLevel) {
        if (evenLevel) {
            return p.x - n.x;
        } else {
            return p.y - n.y;
        }
    }

}

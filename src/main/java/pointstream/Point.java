package pointstream;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Point {

    private long ts;
    private String colour;

    public long getTs() {
        return ts;
    }

    public String getColour() {
        return colour;
    }

    public Point(long ts, String colour) {
        this.ts = ts;
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Point{" +
                "ts=" + ts +
                ", colour='" + colour + '\'' +
                '}';
    }

    public static List<List<Point>> getListOfPoints(Stream<Point> pointStream, int gap) {

        Function<Point, Integer> f = (Point v) -> {

            int i = 1;

            for (; i <= v.getTs(); i++) {
                if (v.getTs() <= gap * i) {
                    return i;
                }
            }

            return i * gap;
        };

        return pointStream.collect(Collectors.groupingBy(f)).values().stream().collect(Collectors.toList());
    }

    public static Map<String, List<Point>> getMapOfPoints(Stream<Point> pointStream, int gap) {
        Map<String, List<Point>> map = new HashMap<>();

        return map;
    }

    public static void main(String[] args) {

        Point p1 = new Point(1, "blue");
        Point p2 = new Point(3, "red");
        Point p3 = new Point(6, "blue");
        Point p4 = new Point(7, "blue");
        Point p5 = new Point(8, "blue");

        Stream<Point> points = Stream.of(p1, p2, p3, p4, p5);

        System.out.println(getListOfPoints(points, 3));

    }
}

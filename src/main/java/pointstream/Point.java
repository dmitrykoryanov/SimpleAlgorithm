package pointstream;

import java.util.*;
import java.util.function.BiFunction;
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

    static class Helper {

        static long bucket = 1;
        static long previousTS = 1;
        static int gap;

        public static void initialize(){
            bucket = 1;
            previousTS = 1;
        }
    }

    static Function<Point, Long> groupingFunction = (Point v) -> {

        if (v.getTs() >= Helper.previousTS + Helper.gap) {
            Helper.bucket++;
        }
        Helper.previousTS = v.getTs();
        return Helper.bucket;

    };

    public static List<List<Point>> getListOfPoints(Stream<Point> pointStream, int gap) {

        Helper.gap = gap;
        Helper.initialize();

        return pointStream.collect(Collectors.groupingBy(groupingFunction)).values().stream().collect(Collectors.toList());
    }

    public static Map<String, List<List<Point>>> getMapOfPoints(Stream<Point> pointStream, int gap) {

        Helper.gap = gap;
        Helper.initialize();

        return pointStream.collect(Collectors.groupingBy(Point::getColour)).entrySet().stream().collect(
                Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().stream().collect(Collectors.groupingBy(groupingFunction)).values().stream().collect(Collectors.toList())
                ));
    }

    public static void main(String[] args) {

        Point p1 = new Point(1, "blue");
        Point p2 = new Point(3, "red");
        Point p3 = new Point(6, "blue");
        Point p4 = new Point(7, "blue");
        Point p5 = new Point(8, "blue");
        Point p6 = new Point(12, "black");
        Point p7 = new Point(13, "black");


        Stream<Point> points = Stream.of(p1, p2, p3, p4, p5, p6, p7);

        //System.out.println(getListOfPoints(points, 3));
        System.out.println(getMapOfPoints(points, 3));

    }
}

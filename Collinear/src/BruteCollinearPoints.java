import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private List<LineSegment> list;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null)
            throw new NullPointerException();

        list = new ArrayList<LineSegment>();
        Point[] temp = points.clone();
        Arrays.sort(temp);

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == null)
                throw new NullPointerException();

            for (int j = i + 1; j < temp.length; j++) {
                if (temp[j] == null)
                    throw new NullPointerException();
                if (temp[i].equals(temp[j]))
                    throw new IllegalArgumentException();

                double s1 = temp[i].slopeTo(temp[j]);

                for (int k = j + 1; k < temp.length; k++) {
                    if (temp[k] == null)
                        throw new NullPointerException();
                    if (temp[j].equals(temp[k]))
                        throw new IllegalArgumentException();

                    double s2 = temp[i].slopeTo(temp[k]);

                    if (s1 == s2) {
                        for (int l = k + 1; l < temp.length; l++) {
                            if (temp[l] == null)
                                throw new NullPointerException();
                            if (temp[k].equals(temp[l]))
                                throw new IllegalArgumentException();
    
                            double s3 = temp[i].slopeTo(temp[l]);
                            if (s1 == s3) 
                                list.add(new LineSegment(temp[i], temp[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return list.size();
    }

    public LineSegment[] segments() {
        // the line segment
        LineSegment[] ret = new LineSegment[list.size()];
        return list.toArray(ret);
    }

    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}

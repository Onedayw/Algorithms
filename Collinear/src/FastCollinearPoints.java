import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private List<LineSegment> list;
    private List<Point> checkPre;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null)
            throw new NullPointerException();

        list = new ArrayList<LineSegment>();
        checkPre = new ArrayList<Point>();
        Point[] temp = points.clone();
        
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException();
            
            if (checkPre.contains(points[i]))
                throw new IllegalArgumentException();

            checkPre.add(points[i]);
            Arrays.sort(temp, points[i].slopeOrder());

            int j = 1;
            while (j < temp.length - 2) {
                double slope = points[i].slopeTo(temp[j]);
                
                if (slope == points[i].slopeTo(temp[j+1])) {              
                    if (slope == points[i].slopeTo(temp[j+2])) {
                        int count = 3;
                        Point max = points[i], min = points[i];
                        
                        while (j + count < temp.length && slope == points[i].slopeTo(temp[j+count])) {
                            count++;
                        }
                        
                        for (int k = 0; k < count; k++) {
                            if (max.compareTo(temp[j+k]) < 0)
                                max = temp[j+k];
                            
                            if (min.compareTo(temp[j+k]) > 0) {
                                min = temp[j+k];
                                break;
                            }
                        }

                        if (min == points[i])
                            list.add(new LineSegment(min, max));
                        
                        j = j + count - 2;
                    }
                    
                    j++;
                }
                
                j++;
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return list.size();
    }

    public LineSegment[] segments() {
        // the line segments
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
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenColor();
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println("Solution has " + collinear.numberOfSegments() + " non-null entries");
    }
}

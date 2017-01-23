/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author James
 */
public class ConvexHull {
    
    /**
     * Recursively find the convex hull of a set of points
     * @param points the set of points to be solved
     * @return the set of points in the hull
     */
    public ArrayList<Point> quickHull(ArrayList<Point> points){
        
        ArrayList<Point> convexHull = new ArrayList<>(); //new list of points, convexHull
        if (points.size() < 3) //if there are 3 or less points (so all the points are on the convex hull)
            return (ArrayList) points.clone(); //return a copy of the original array
 
        Point A = points.get(0); //point A is minimum point
        Point B = points.get(1); //point B is maximum point
        convexHull.add(A); //add A to hull
        convexHull.add(B); //add B to hull
        points.remove(A); //remove A from input array
        points.remove(B); //remove B from input array
 
        ArrayList<Point> leftSet = new ArrayList<>(); //new array leftSet
        ArrayList<Point> rightSet = new ArrayList<>(); //new array rightSet
 
        for (int i = 0; i < points.size(); i++){ //for all points left in input array
            Point p = points.get(i); //set temp value p equal to the current point in the loop
            if (pointLocation(A, B, p) == -1){ //if point location function returns -1
                leftSet.add(p); //add to left set
            }else if (pointLocation(A, B, p) == 1){ //else if point location function returns 1
                rightSet.add(p); //add to right set
            }
        }
        
        hullSet(A, B, rightSet, convexHull); //solve for the points on the right of AB
        hullSet(B, A, leftSet, convexHull); //solve for the points on the left of AB
 
        return convexHull; 
    }
 
    /**
     * Calculates the distance between line AB and point P
     * @param A first point of line AB
     * @param B second point of line AB
     * @param P point of which distance is determined 
     * @return absolute value of distance from line
     */
    public int distance(Point A, Point B, Point P){
        int ABx = B.x - A.x; //difference between the x coords
        int ABy = B.y - A.y; //difference between the y coords
        int num = ABx * (A.y - P.y) - ABy * (A.x - P.x); //distance from line AB to point P

        return Math.abs(num); //return absolute value of distance
    }
    
    /**
     * Decides if points are on hull. Calls self recursively to repeat for all points.
     * @param A first point of line AB
     * @param B second point of line AB
     * @param set set of unsorted points
     * @param hull convex hull
     */
    public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull){
        int insertPosition = hull.indexOf(B); //get inset pos
        if (set.isEmpty()){ //if set doesn't exist
            return; //return no value
        }
        
        if (set.size() == 1){ //if one point in set
            Point p = set.get(0); //get point
            set.remove(p); //remove point
            hull.add(insertPosition, p); //add point to hull
            return; //return no value
        }
        
        int oldDist = Integer.MIN_VALUE; //set int to -2147483648
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++){ //for all point in set
            Point p = set.get(i); //get point 
            int newDist = distance(A, B, p); //get distance between p and line AB
            if (newDist > oldDist){ //if new point is furthest point yet
                oldDist = newDist;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint); //set the furthest 
        set.remove(furthestPoint); //remove point from original set
        hull.add(insertPosition, P); //add to hull
 
        // Determine who's to the left of AP
        ArrayList<Point> leftSetAP = new ArrayList<>(); //set of points to the left of AP
        for (int i = 0; i < set.size(); i++){ //for all points
            Point M = set.get(i); //get nth point
            if (pointLocation(A, P, M) == 1){ //if on top side of line
                leftSetAP.add(M); //add to left set of AP
            }
        }
 
        // Determine who's to the left of PB
        ArrayList<Point> leftSetPB = new ArrayList<>(); //set of points to the left of PB
        for (int i = 0; i < set.size(); i++){ //for all points
            Point M = set.get(i); //get nth point
            if (pointLocation(P, B, M) == 1){ //if on top side of line
                leftSetPB.add(M); //add to left set of PB
            }
        }
        hullSet(A, P, leftSetAP, hull); //run algorithm again on AB
        hullSet(P, B, leftSetPB, hull); //run algorithm again on PB
 
    }
 
    /**
     * Decides if point P is above or below line AB
     * @param A first point of line AB
     * @param B second point of line AB
     * @param P point of which side is determined
     * @return 1 if above, 0 if on line, -1 if below
     */
    public int pointLocation(Point A, Point B, Point P){
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0){ //if above line
            return 1; 
        }else if (cp1 == 0){ //if on line
            return 0;
        }else { //if below line
            return -1;
        }
    }
}

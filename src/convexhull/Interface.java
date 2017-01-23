/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author James
 */
public class Interface {
    
    
    protected static ConvexHull convexHull = new ConvexHull();
    
    /**
     * Interface with user and display results
     * @param args 
     */
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        menuLoop:
        while(true){    
            System.out.println("\n-- Convex Hull Pathfinding --\n"
                    + "Choose an option:\n"
                    + "1) Single polygon pathfinding\n"
                    + "2) Multi-polygon pathfinding\n"
                    + "3) Help\n"
                    + "4) Exit\n"
                    + "-----------------------------");
            String input = sc.nextLine();

            switch(input.toLowerCase()){
                default:
                    System.out.println("Invalid option, try again");
                    break;
                    
                case "1":
                case "one": //single polygon
                    singleHull();                    
                    break;
                    
                case "2":
                case "two": //multi-polygon                  
                    multiHull();                    
                    break;
                    
                case "3":
                case "three": //about
                    System.out.println("Author: James Rhymes\n"
                            + "About: This program was created to solve the convex \nhull problem using a divide and conquer method. "
                            + "This \nis my submission for module CS2EA16, second year \nComputer Science degree.\n"
                            + "Rules:\n"
                            + " -A and B must be on the extremes on the polygon/s.\n"
                            + " -You cannot put two points in the same place.\n"
                            + " -Multi-polygon pathfinding has three points (A, B & C)\n  and up to two polygons between each point.");
                    break;
                    
                case "4":
                case "four":
                case "exit":
                case "close": //exit
                    System.out.println("\nExiting...\n");
                    break menuLoop;
                    
            }
        }
    }
    
    /**
     * Handles the setup for a single hull problem
     */
    public static void singleHull(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Point> points = new ArrayList<>();
        
        System.out.println("Enter the coordinates the start point: <x> <y>");
        int ax = sc.nextInt();
        int ay = sc.nextInt();
        Point a = new Point(ax, ay);
        points.add(0, a);
        
        System.out.println("Enter the coordinates the end point: <x> <y>");
        int bx = sc.nextInt();
        int by = sc.nextInt();
        Point b = new Point(bx, by);
        points.add(1, b);

        System.out.println("Enter the number of points in polygon P (excluding start and end)");
        int N = sc.nextInt();

        System.out.println("Enter the coordinates of each point in polygon P: <x> <y>");
        for (int i = 2; i < N+2; i++){ //add all other points to arraylist
            int x = sc.nextInt();
            int y = sc.nextInt();
            Point e = new Point(x, y);
            points.add(i, e);
        }

        ArrayList<Point> p = convexHull.quickHull(points); //run quickhull
        System.out.println("The points in the Convex hull are: ");
        Collections.sort(p, new PointCompare()); //sort from low x to high x
        for (int i = 0; i < p.size(); i++){ //print points
            System.out.println("(" + p.get(i).x + ", " + p.get(i).y + ")");
        }
    }
    
    /**
     * Handles the setup for a multiple hull problem
     */
    public static void multiHull(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the coordinates of point A: <x> <y>");
        int ax = sc.nextInt();
        int ay = sc.nextInt();
        Point a = new Point(ax, ay);
        System.out.println("Enter the coordinates of point B: <x> <y>");
        int bx = sc.nextInt();
        int by = sc.nextInt();
        Point b = new Point(bx, by);
        System.out.println("Enter the coordinates of point C: <x> <y>");
        int cx = sc.nextInt();
        int cy = sc.nextInt();
        Point c = new Point(cx, cy);
        
        System.out.println("Enter the number of polygons between A and B:");
        int abpoly = sc.nextInt();
        System.out.println("Enter the number of polygons between B and C:");
        int bcpoly = sc.nextInt();
        
        System.out.println("\nA -> B Polygon/s");
        if(abpoly != 0){ //if there is polygon to avoid
            for (int i = 1; i <= abpoly; i++) { //for all polygons
                ArrayList<Point> polygons = new ArrayList<>();
                System.out.println("Enter the number of points in polygon "+i+" (excluding start and end)");
                int N = sc.nextInt();
                System.out.println("Enter the coordinates of each point in polygon "+i+": <x> <y>");
                
                polygons.add(0, a);
                polygons.add(1, b);
                
                for (int j = 2; j < N+2; j++){ //add all other points to arraylist
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Point e = new Point(x, y);
                    polygons.add(j, e);
                }
                
                ArrayList<Point> p = convexHull.quickHull(polygons); //run quickhull
                System.out.println("The points in the Convex hull (A -> Polygon "+i+" -> B) are: ");
                Collections.sort(polygons, new PointCompare()); //sort from low x to high x
                for (int j = 0; j < p.size(); j++){ //print points
                    System.out.println("(" + p.get(j).x + ", " + p.get(j).y + ")");
                }
            }
        }else{ //if no polygon, hull is straight line
            ArrayList<Point> polygons = new ArrayList<>();
            polygons.add(0, a);
            polygons.add(1, b);
            Collections.sort(polygons, new PointCompare()); //sort from low x to high x
            for (int j = 0; j < polygons.size(); j++){ //print points
                System.out.println("(" + polygons.get(j).x + ", " + polygons.get(j).y + ")");
            }
        }
        
        System.out.println("\nB -> C Polygon/s");
        if(bcpoly != 0){ //if there is polygon to avoid
            for (int i = 1; i <= bcpoly; i++) { //for all polygons
                ArrayList<Point> polygons = new ArrayList<>();
                System.out.println("Enter the number of points in polygon "+i+" (excluding start and end)");
                int N = sc.nextInt();
                System.out.println("Enter the coordinates of each point in polygon "+i+": <x> <y>");
                
                polygons.add(0, b);
                polygons.add(1, c);
                
                for (int j = 2; j < N+2; j++){ //add all other points to arraylist
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Point e = new Point(x, y);
                    polygons.add(j, e);
                }
                
                ArrayList<Point> p = convexHull.quickHull(polygons); //run quickhull
                System.out.println("The points in the Convex hull (A -> Polygon "+i+" -> B) are: ");
                Collections.sort(polygons, new PointCompare()); //sort from low x to high x
                for (int j = 0; j < p.size(); j++){ //print points
                    System.out.println("(" + p.get(j).x + ", " + p.get(j).y + ")");
                }
            }
        }else{ //if no polygon, hull is straight line
            ArrayList<Point> polygons = new ArrayList<>();
            polygons.add(0, b);
            polygons.add(1, c);
            Collections.sort(polygons, new PointCompare()); //sort from low x to high x
            for (int j = 0; j < polygons.size(); j++){ //print points
                System.out.println("(" + polygons.get(j).x + ", " + polygons.get(j).y + ")");
            }
        }
        
        
    }
    /**
     * Used in singleHull() and multiHull() to sort points in order from left to right
     */
    public static class PointCompare implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            }
            else if (a.x > b.x) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}

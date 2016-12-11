package lab5;
import java.util.*;
import java.io.*;

/**
 * @author Trevor
 * @version 1.0 
 * @since 10/5/2016
 * @class C202 - Fall 2016
 * 
 * Description: This is a java program built to analyze ArrayLists by determining
 *              the best cost along a certain path that covers all numbers contained
 *              in the ArrayList at every index. (Traveling Salesman Problem)
 */
public class ProgrammingAssignment6 {

    private int CITI; //# of Cities
    private int[][] adj;// Matrix
    private int bestCost = Integer.MAX_VALUE; 
    private ArrayList<Integer> bestPath;

    /**
     * @param n 
     * 
     * Description: This is the class constructor for "Lab5" and initializes 
     *              most of the private variables above (bestCost has already 
     *              been initialized). It takes the integer variable n from the
     *              user as the number of cities contained in the text files.
     */
    public ProgrammingAssignment6(int n) {
        CITI = n;
        adj = new int[CITI][CITI];
        bestPath = new ArrayList<>();
    } 
    
    /**
     * @param fName 
     * 
     * Description: This method reads our text files for different cities and
     *              only requires that the user type in the file name WITHOUT 
     *              the file extension or number of cities associated with that
     *              file (To avoid redundancy). This method then populates the
     *              'adj' two-dimensional array (matrix) with values read from 
     *              the file. If the file doesn't exist, or the user doesn't type
     *              in the correct type of variable then the exception is caught.
     */
    public void populateMatrix(String fName) {
        try {
            File f = new File(fName + CITI + ".txt");
            Scanner input = new Scanner(f);
            int value, i, j;

            for (i = 0; i < CITI && input.hasNext(); i++) { //CITI is a constant  
                for (j = i; j < CITI && input.hasNext(); j++){ 
                    if (i == j) { 
                        adj[i][j] = 0;
                    } else {
                        value = input.nextInt();
                        adj[i][j] = value;
                        adj[j][i] = value;
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println("Sorry, that file doesn't exist!");
        }
    }
    
    /*
    create an empty stack, call it pathStack
    create an empty array of N elements, call it visitedCities // N == # of cities
    //assume start city is city 0
    set city 0 as visited in visitedCities array
    push city 0 to pathStack
    set closestCity to 0
    set minFlag to false
    Output start city
    
    while pathStack is not empty do
        set currentCity with top value of pathStack
        set min to Integer.MAX_VALUE //minimum distance
        for all the remaining cities starting city 1 to N do
            if (distance from currentCity to city i is not 0 AND city i is not visited)
                if (distance from currentCity to city i is less than min)
                    min = distance from currentCity to city i
                    closestCity = i
                    set minFlag to true
                endif
            endif
        endfor
        if(minflag)
            set closestCity in visitedCities as visited
            push closestCity to pathStack
            Output closestCity
            set minFlag to false
            continue
        endif
        pop the top element from pathStack
    endwhile
    
    Above Algorithm given is used to evaluate the Shortest Path Tree of our adjacency
    matrix.
    
    This method is void and only echoes out values so we dont have to waste our
    time in the main with System.out.println() statements later
    */
    
    public void Dfs() {
        Stack<Integer> pathStack = new Stack<>();
        boolean[] visitedCities = new boolean[CITI];
        visitedCities[0] = true;
        pathStack.push(adj[0][0]);
        int closestCity = 0;
        boolean minFlag = false;
        System.out.print(adj[0][0] + " ");
        int minVal = 0;
        
        while(!pathStack.isEmpty()) {
            int currentCity = pathStack.peek();
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < CITI; i++) {
                if(adj[currentCity][i] != 0 && visitedCities[i] != true) {
                    if(adj[currentCity][i] < min) {
                        min = adj[currentCity][i];
                        minVal += min;
                        closestCity = i;
                        minFlag = true;
                    }
                }
            }
            if(minFlag) {
                visitedCities[closestCity] = true;
                pathStack.push(closestCity);
                System.out.print(closestCity + " ");
                minFlag = false;
                continue;
            }
            pathStack.pop();
        }  
        System.out.println("");
        System.out.println("Cost: " + minVal);
        
    }
    
    
    
    
    
    /**
     * Description: This method is void and echoes out the information gathered
     *              after all the methods above it are run. This is a simple    
     *              method that produces the correct order for the ArrayList
     *              that costs the LEAST.
     */
    public void output() {
        System.out.print("Best path and cost: <");
        for(int i = 0; i < bestPath.size(); i++) {
            if(i == bestPath.size() - 1) {
                System.out.print(bestPath.get(i));
            } else {
                System.out.print(bestPath.get(i) + ", ");
            }
        }
        System.out.print(">");
        System.out.println("");
        System.out.println("Cost = " + bestCost);
    }
    
    /**
     * @param args 
     * 
     * Description: This is the main method used to run all methods above
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Help! I need a number of cities!");
        int a = input.nextInt();
        System.out.println("Type 'tsp': ");
        String b = input.next();
        ProgrammingAssignment6 x = new ProgrammingAssignment6(a);
        x.populateMatrix(b);
        long start = System.nanoTime();
        x.Dfs();
        long stop = System.nanoTime();
        System.out.println("Time taken to run: " + (stop - start) + " nanoseconds.");
    }
}
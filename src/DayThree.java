import java.math.BigInteger;
import java.util.ArrayList;
import java.io.*;

@SuppressWarnings("ALL")
public class DayThree {
    public static void partOne() throws Exception {
        // Read in the map as a grid
        ArrayList<ArrayList<Character>> treeGrid = new ArrayList<>();
        var infile = new BufferedReader(new FileReader("./data/day_three.txt"));
        var line = "";
        while((line = infile.readLine()) != null){
            ArrayList<Character> row = new ArrayList<>();
            for(char letter : line.toCharArray()){
                row.add(letter);
            }
            treeGrid.add(row);
        }
        System.out.printf("Tree count: %d", getCollisions(0, 0, 3, -1, treeGrid)); // Run the function to find all tree collisions
    }

    public static void partTwo() throws Exception{
        // Read in the map as a grid
        ArrayList<ArrayList<Character>> treeGrid = new ArrayList<>();
        var infile = new BufferedReader(new FileReader("./data/day_three.txt"));
        var line = "";
        while((line = infile.readLine()) != null){
            ArrayList<Character> row = new ArrayList<>();
            for(char letter : line.toCharArray()){
                row.add(letter);
            }
            treeGrid.add(row);
        }
        // Find the collisions for all the slopes
        int[] slopes = {
                1,-1,
                3, -1,
                5,-1,
                7,-1,
                1,-2
        };
        ArrayList<Integer> answers = new ArrayList<>();
        for(int i = 0; i < slopes.length-1; i+=2){
            var answer = getCollisions(0, 0, slopes[i], slopes[i+1], treeGrid);
            answers.add(answer);
            System.out.printf("In slope %3.2f collisions = %d%n", (double)slopes[i+1]/slopes[i], answer);
        }
        // The result is a number too big to fit into an integer, so we must use a special class
        BigInteger product = new BigInteger("1");
        for(var num : answers)
            product = product.multiply(new BigInteger(String.valueOf(num)));
        System.out.printf("Product of all slopes: %s%n", product);
    }

    // Counts how many collisions will occur if you start at a given point and go down the forest
    private static int getCollisions(int startX, int startY, int deltaX, int deltaY, ArrayList<ArrayList<Character>> treeGrid){
        int treeCount = 0;  // How many trees we have collided with
        int x = startX;     // stores our current x value. The Y value will be handled by our for-loop

        for(int y = startY; y < treeGrid.size(); y -= deltaY){          // Keep adjusting y until we reach the end of the grid...
            int realXValue = x % treeGrid.get(0).size();                // Remember, the pattern wraps around forever, so we can do modulus to simulate wrapping back around
            Character currentTile = treeGrid.get(y).get(realXValue);
            if(currentTile == '#'){
                treeCount += 1;
            }
            x += deltaX;
        }
        return treeCount;
    }
}
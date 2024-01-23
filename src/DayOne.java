import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class DayOne {
    public static void partOne() throws Exception{
        Integer numOne = null; // will hold the first of the 2 numbers
        Integer numTwo = null; // will hold the second of the 2 numbers
        ArrayList<Integer> numbers = new ArrayList<Integer>();// Stores the numbers in the file

        // Open the file
        var inFile = new BufferedReader(new FileReader("./data/day_one.txt"));
        String line = "";
        while((line = inFile.readLine()) != null){
            // For each line in the file, convert it to a number...
            var readNumber = Integer.parseInt(line);
            var compliment = 2020 - readNumber; // Identify the compliment of this number
            // If the compliment exists within our already read numbers, we are done.
            if(Collections.binarySearch(numbers, compliment) != -1) {
                numOne = readNumber;
                numTwo = compliment;
                break;
            }
            // Else, add it to our list
            numbers.add(readNumber);
            Collections.sort(numbers);
        }

        // Make sure that our loop found correct numbers...
        assert(numOne != null);
        assert(numTwo != null);
        assert((numOne + numTwo) == 2020);

        System.out.println("Found numbers " + numOne + " and " + numTwo + ". Product = " + numOne * numTwo);
    }

    public static void partTwo() throws Exception{
        ArrayList<Integer> numbers = new ArrayList<Integer>();// Stores the numbers in the file
        var inFile = new BufferedReader(new FileReader("./data/day_one.txt"));
        String line = "";

        // For each line...
        while((line = inFile.readLine()) != null){
            // Read the number from the line. Find the remainder of 2020-number
            int readNum = Integer.parseInt(line);
            int remainder = 2020 - readNum;

            // Find 2 digits in the list that add up to the remainder
            for(int i = 0; i < numbers.size(); i++) {
                for(int j = i + 1; j < numbers.size(); j++) {
                    int a = numbers.get(i);
                    int b = numbers.get(j);
                    // If we find the numbers, print them all out and then exit the program
                    if((a + b) == remainder) {
                        System.out.print("Found numbers: " + readNum + ", " + a + ", " + b+". Product = " + a * b * readNum);
                        return;
                    }
                }
            }
            // If we failed to find 2 numbers that work, add current number to our list
            numbers.add(readNum);
        }
        System.out.println("Failed to find 3 numbers that work");
    }
}

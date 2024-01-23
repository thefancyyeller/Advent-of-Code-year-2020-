import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class DayTwo {
    public static void partOne() throws Exception {
        var infile = new BufferedReader(new FileReader("./data/day_two.txt"));
        int numValidPasswords = 0;

        String line;
        while((line = infile.readLine()) != null){
            // Split the line-up by spaces. Parse all the relevant data.
            var parts = line.split(" ");
            int[] bounds = Arrays.stream(parts[0].split("-")).mapToInt(Integer::parseInt).toArray(); // lazy way of reading the upper and lower bound. You dont need to do this.
            char requiredLetter = parts[1].charAt(0);
            String password = parts[2];

            // Count the occurrences of required letters within password
            int count = 0;
            for(char letter : password.toCharArray()){
                if(letter == requiredLetter){
                    count += 1;
                }
            }

            // See if password is valid & is within bounds
            if((count >= bounds[0]) && (count <= bounds[1])){
                numValidPasswords += 1;
            }
        }

        System.out.println("Valid passwords: " + numValidPasswords);
    }

    public static void partTwo() throws Exception {
        var infile = new BufferedReader(new FileReader("./data/day_two.txt"));
        int numValidPasswords = 0;

        String line;
        while((line = infile.readLine()) != null){
            // Same line-reading code...
            var parts = line.split(" ");
            int[] searchIndexes = Arrays.stream(parts[0].split("-")).mapToInt(Integer::parseInt).toArray(); // Changed the variable name here due to the nature of the question
            char requiredLetter = parts[1].charAt(0);
            String password = parts[2];

            // Adjust the searchIndexes since they are not "base zero" (i.e. letter number 1 is actually going to be item 0 on the array)
            for(int i = 0; i < 2; i++) {
                searchIndexes[i] -= 1;
            }

            // Count the occurrences of the letters ONLY on the specified indexes
            int occurrences = 0;
            for(int i = 0; i < searchIndexes.length; i++){ // For each index that we are told to check...
                int desiredIndex = searchIndexes[i]; // IMPORTANT!! DONT FORGET THIS LINE
                char actualLetter = password.charAt(desiredIndex); // Get the actual letter located at that index
                if(actualLetter == requiredLetter){
                    occurrences += 1;
                }
            }

            // A password is valid if and only if it has the required letter ONCE
            if(occurrences == 1){
                numValidPasswords += 1;
            }
        }
        System.out.println("Valid passwords: " + numValidPasswords);
    }
}

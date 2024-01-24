import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class DayFour {
    public static void partOne() throws Exception{
        var data = readFile();
        int validCount = 0;

        // For each passport, check if all the fields exist
        for(var passport : data){
            if(hasFields(passport)){
                validCount += 1;
            }
        }
        System.out.printf("Num valid lines: %d%n", validCount);
    }
    public static void partTwo() throws Exception{
        var data = readFile();
        int validCount = 0;
        // We are gonna verify each part of the passport and call 'continue' if we deem it invalid
        for(var passport : data){
            // Check if the passport has required fields
            if(!hasFields(passport))
                continue;
            // Verify the fields that are years
            if(invalidNumberField(passport.get("byr"), 1920, 2002))
                continue;
            if(invalidNumberField(passport.get("iyr"), 2010, 2020))
                continue;
            if(invalidNumberField(passport.get("eyr"), 2020, 2030))
                continue;
            // Verify the height
            String heightField = passport.get("hgt");
            if(heightField.length() < 3)
                return;
            String heightUnit = heightField.substring(heightField.length()-2);
            Integer numPart = parseIntNullable(heightField.substring(0, heightField.length()-2));
            if(numPart == null)
                continue;
            int lowerBound = -1; // These will be given a value in the code below...
            int upperBound = -1;
            if(heightUnit.equals("cm")){
                lowerBound = 150;
                upperBound = 193;
            }
            else if(heightUnit.equals("in")){
                lowerBound = 59;
                upperBound = 76;
            }else{ // Else, if the units are invalid, continue.
                continue;
            }
            if(!inRange(numPart, lowerBound, upperBound))
                continue;

            // Validate hair color
            var hairField = passport.get("hcl");
            if(hairField.length() != 7)
                continue;
            if(hairField.charAt(0) != '#')
                continue;
            // Regex is a shortcut you do not need. Just verify it starts with a # then has 6 characters after that are 0-9 or a-f
            Pattern hairPattern = Pattern.compile("#[0-9a-f]{6}");
            if(!hairPattern.matcher(hairField).matches())
                continue;
            // Validate eye color
            var eyeField = passport.get("ecl");
            String[] allowedColors = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
            var eyeIsValid = false;
            for(var color : allowedColors){
                if (color.equals(eyeField)) {
                    eyeIsValid = true;
                    break;
                }
            }
            if(!eyeIsValid)
                continue;

            // Validate passport ID
            var passportId = passport.get("pid");
            var validPassport = true;
            if(passportId.length() != 9)
                continue;
            for(var c : passportId.toCharArray()){
                if(!Character.isDigit(c)){
                    validPassport = false;
                    break;
                }
            }
            if(!validPassport)
                break;

            // Finally, we have a valid passport.
            validCount += 1;
        }
        System.out.printf("Valid count: %d%n",validCount);
    }
    // Takes a string, returns true if it is either an invalid number or if it is outside the allowed bounds.
    private static boolean invalidNumberField(String field, int min, int max){
        Integer readNum = parseIntNullable(field);
        if(readNum == null)
            return true;
        return !inRange(readNum, min, max);
    }
    private static boolean hasFields(HashMap<String, String> passport){
        String[] requiredFields = {"byr","iyr","eyr","hgt","hcl","ecl","pid"};
        for(var field : requiredFields){
            if(!passport.containsKey(field)){
                return false;
            }
        }
        return true;
    }
    // Tries to convert string to integer. Returns null if it can't
    private static Integer parseIntNullable(String numString){
        try{
            return Integer.parseInt(numString);
        } catch(NumberFormatException err){
            return null;
        }
    }
    // Checks if number is in range.
    private static boolean inRange(int num, int min, int max){
        return (num >= min && num <= max);
    }
    // Reads the file. Turns each passport into a HashMap, which is simply a collection of attributes and values. google "what is a hash map"
    private static ArrayList<HashMap<String, String>> readFile() throws Exception{
        var passports = new ArrayList<HashMap<String, String>>();
        var infile = new BufferedReader(new FileReader("./data/day_four.txt"));
        var line = "";

        StringBuilder buffer = new StringBuilder(); // We will fill this with 1 passport's worth of data at a time.
        // Step 1, read 1 passport's worth of data
        while((line = infile.readLine())!=null){
            if(!line.isEmpty()){ // If the line isn't empty, add a space then write it to the buffer
                if(!buffer.isEmpty())
                    buffer.append(" ");
                buffer.append(line);
                continue;
            }
            // We reach here once the buffer contains 1 passport worth of data.
            HashMap<String, String> passportInfo = bufferToHashMap(buffer);
            // Add the passport info into output
            passports.add(passportInfo);
            buffer = new StringBuilder(); //reset the buffer
        }
        return passports;
    }
    private static HashMap<String, String> bufferToHashMap(StringBuilder buffer) {
        String[] entries = buffer.toString().split(" ");// Grab each entry
        HashMap<String, String> passportInfo = new HashMap<String, String>();
        for(var entry : entries){
            // Extract the attribute and its value. Add it to the passportInfo
            String[] split = entry.split(":");
            assert(split.length == 2);
            passportInfo.put(split[0], split[1]);
        }
        return passportInfo;
    }
}

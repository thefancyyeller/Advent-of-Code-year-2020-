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
        for(var passport : data){
            if(hasFields(passport) == false)
                continue;
            // Verify the years
            try {
                // Verify birth year
                int birthYear = Integer.parseInt(passport.get("byr"));
                if(birthYear < 1920 || birthYear > 2002){
                    continue;
                }
                // Verify issue year
                int issYear = Integer.parseInt(passport.get("iyr"));
                if(issYear < 2010 || issYear > 2020){
                    continue;
                }
                // Verify exp year
                int expYear = Integer.parseInt(passport.get("eyr"));
                if(expYear < 2020 || expYear > 2030){
                    continue;
                }
            } catch(NumberFormatException err){
                continue;// fires off when we get an entry that cannot be read as a number (invalid format)
            }
            // If the code reaches here, we have verified the years. Time for height
            String heightField = passport.get("hgt");
            if(heightField.length() < 3)
                return;
            String heightUnit = heightField.substring(heightField.length()-2);
            if(heightUnit.equals("cm")){
                try{
                    int numPart = Integer.parseInt(heightField.substring(0, heightField.length()-2));
                    if(numPart < 150 || numPart > 193){
                        continue;
                    }
                }catch(NumberFormatException e){
                    System.out.println("Invalid height " + heightField);
                    continue;
                }
            }
            else if(heightUnit.equals("in")){
                try{
                    int numPart = Integer.parseInt(heightField.substring(0, heightField.length()-2));
                    if(numPart < 59 || numPart > 76){
                        System.out.println("Invalid height " + heightField);
                        continue;
                    }
                }catch(NumberFormatException e){
                    System.out.println("Invalid height " + heightField);
                    continue;
                }
            }else{
                continue;
            }
            // Validate hair color
            var hairField = passport.get("hcl");
            if(hairField.length() != 7){
                continue;
            }
            if(hairField.charAt(0) != '#'){
                System.out.println("No # in field " + hairField);
                continue;
            }
            Pattern hairPattern = Pattern.compile("#[0-9a-f]{6}");
            if(!hairPattern.matcher(hairField).matches()){
                continue;
            }
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
            if(eyeIsValid == false)
                continue;

            // Validate passport ID
            var passportId = passport.get("pid");
            var validPassport = true;
            if(passportId.length() != 9)
                continue;
            for(var c : passportId.toCharArray()){
                if(Character.isDigit(c) == false){
                    validPassport = false;
                    break;
                }
            }
            if(validPassport == false)
                break;

            // Finally, we have a valid passport.
            validCount += 1;
        }
        System.out.printf("Valid count: %d%n",validCount);
    }
    private static boolean hasFields(HashMap<String, String> passport){
        String[] requiredFields = {"byr","iyr","eyr","hgt","hcl","ecl","pid"};
        for(var field : requiredFields){
            if(passport.containsKey(field) == false){
                return false;
            }
        }
        return true;
    }
    // Reads the file. Turns each passport into a HashMap, which is simply a collection of attributes and values. google "what is a hash map"
    private static ArrayList<HashMap<String, String>> readFile() throws Exception{
        var passports = new ArrayList<HashMap<String, String>>();
        var infile = new BufferedReader(new FileReader("./data/day_four.txt"));
        var line = "";

        String buffer = ""; // We will fill this with 1 passport's worth of data at a time.
        // Step 1, read 1 passport's worth of data
        while((line = infile.readLine())!=null){
            if(line.isEmpty() == false){ // If the line isnt empty, add a space then write it to the buffer
                if(buffer.isEmpty() == false)
                    buffer += " ";
                buffer += line;
                continue;
            }
            // We reach here once the buffer contains 1 passport worth of data.
            String[] entries = buffer.split(" ");// Grab each entry
            HashMap<String, String> passportInfo = new HashMap<String, String>();
            for(var entry : entries){
                // Extract the attribute and its value. Add it to the passportInfo
                String[] split = entry.split(":");
                assert(split.length == 2);
                passportInfo.put(split[0], split[1]);
            }
            // Add the passport info into output
            passports.add(passportInfo);
            buffer = ""; //reset the buffer
        }
        return passports;
    }
}

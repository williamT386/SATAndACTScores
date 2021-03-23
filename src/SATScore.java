import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SATScore {

    public static void main(String[] args) {
        SATScore score = new SATScore();
        score.run();
    }

    private void run() {
        Scanner scannerUser = new Scanner(System.in);
        int[] scores = new int[4];
        String[] questions = {"Reading", "Writing", "Math No Calculator",
                "Math With Calculator"};

        //get user input
        int index = 0;
        while(index < scores.length) {
            System.out.println("What is your " + questions[index] + " score?");

            String scoreString = scannerUser.nextLine();
            //check if is a valid int
            boolean isValid = true;
            for(int i = 0; i < scoreString.length(); i++) {
                if(!(Character.isDigit(scoreString.charAt(i)) ||
                        (scoreString.charAt(i) == '-' && scoreString.length() > 1))) {
                    isValid = false;
                    break;
                }
            }

            if(!isValid) {
                System.out.println("Not a valid number. Try again.");
            }
            else {
                int score = Integer.parseInt(scoreString);
                if(scoreString.charAt(0) == '-') {
                    if(index == 0)
                        score = 52 + score;
                    else if(index == 1)
                        score = 44 + score;
                    else if(index == 2)
                        score = 20 + score;
                    else
                        score = 38 + score;
                }
                scores[index] = score;
                index++;
            }
        }

        List<Integer> reading = new ArrayList<>();
        List<Integer> writing = new ArrayList<>();
        List<Integer> math = new ArrayList<>();
        List<String> testFileNames = new ArrayList<>();

        String listOfFileNames = "TestFileNames.txt";
        Scanner scannerFileNames = setFileScanner(listOfFileNames);
        while(scannerFileNames.hasNext()) {
            String currentFileName = scannerFileNames.nextLine();
            testFileNames.add(currentFileName);

            Scanner scannerTest = setFileScanner(currentFileName);
            while(scannerTest.hasNext()) {
                String lineIn = scannerTest.nextLine();
                if(Integer.parseInt(lineIn.substring(0, 2).trim()) == scores[0]) {
                    reading.add(Integer.parseInt(lineIn.substring(
                            lineIn.length() - 5, lineIn.length() - 3)));
                    break;
                }
            }
            scannerTest = setFileScanner(currentFileName);
            while(scannerTest.hasNext()) {
                String lineIn = scannerTest.nextLine();
                if(Integer.parseInt(lineIn.substring(0, 2).trim()) == scores[1]) {
                    writing.add(Integer.parseInt(lineIn.substring(
                            lineIn.length() - 2)));
                    break;
                }
            }
            scannerTest = setFileScanner(currentFileName);
            while(scannerTest.hasNext()) {
                String lineIn = scannerTest.nextLine();
                if(Integer.parseInt(lineIn.substring(0, 2).trim()) ==
                        scores[2] + scores[3]) {
                    math.add(Integer.parseInt(lineIn.substring(
                            lineIn.length() - 9, lineIn.length() - 6)));
                    break;
                }
            }
        }

        for(int i = 0; i < reading.size(); i++) {
            int englishScaledScore = (reading.get(i) + writing.get(i))*10;
            int mathScaledScore = math.get(i);
            int totalScaledScore = englishScaledScore + mathScaledScore;
            System.out.println(testFileNames.get(i) + ": " + englishScaledScore
                    + " + " + mathScaledScore + " = " + totalScaledScore);
        }

        int averageEnglish = 0;
        for(int i = 0; i < reading.size(); i++)
            averageEnglish += (reading.get(i) + writing.get(i))*10;
        averageEnglish /= reading.size();

        int averageMath = 0;
        for(int mathScore : math)
            averageMath += mathScore;
        averageMath /= reading.size();

        int averageTotal = 0;
        for(int i = 0; i < reading.size(); i++)
            averageTotal += (reading.get(i) + writing.get(i))*10 + math.get(i);
        averageTotal /= reading.size();

        System.out.print("Average: " + averageEnglish + " + " + averageMath +
                " = " +  averageTotal);
    }
    
    private Scanner setFileScanner(String fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println("scanner input file not found");
            System.exit(1);
        }

        return scanner;
    }
}

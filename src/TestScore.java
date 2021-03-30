import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestScore {
    private static final String[] testTypes = new String[]{"SAT", "ACT",
            "PSAT", "PSAT10"};
    public static int numSections;
    private int[] rawScores;
    private List<Integer>[] scaledScores;
    private List<Integer>[] finalScaledScores;
    private List<String> testFileNames;

    private Test test;
    
    public static void main(String[] args) {
        TestScore score = new TestScore();
        score.run();
    }

    private void run() {
        getTestType();
        getRawScores();

        if("ACT".equals(test.getTestType()))
            getScaledScoreACT();
        else
            getScaledScoreOthers();

        calculateFinalScaledScores();
        printScaledScores();
    }

    /**
     * Calculates the final scaled scores. For the tests that are not the
     * ACT, this is the English, Math, and Overall scores.
     */
    private void calculateFinalScaledScores() {
        if("ACT".equals(test.getTestType())) {
            //TODO
        }
        else {
            //used for storing english, math, and overall score
            finalScaledScores = new ArrayList[3];
            for(int i = 0; i < finalScaledScores.length; i++)
                finalScaledScores[i] = new ArrayList<>();

            for(int i = 0; i < scaledScores[0].size(); i++) {
                //get english score
                finalScaledScores[0].add(10 * (scaledScores[0].get(i) +
                        scaledScores[1].get(i)));

                //get math score
                finalScaledScores[1].add(scaledScores[2].get(i));

                //get overall score
                finalScaledScores[2].add(finalScaledScores[0].get(i) +
                        finalScaledScores[1].get(i));
            }
        }
    }

    /**
     * Print the overall scaled scores.
     */
    private void printScaledScores() {
        for(int i = 0; i < finalScaledScores[0].size(); i++) {
            //get the clean version to print
            String fullName = testFileNames.get(i);
            String fileNameClean = fullName.substring(
                            fullName.lastIndexOf("/") + 1,
                            fullName.lastIndexOf(".txt"));

            System.out.print(fileNameClean + ": ");
            if("ACT".equals(test.getTestType())) {
                //TODO
            }
            else {
                System.out.println(finalScaledScores[0].get(i) + " + " +
                        finalScaledScores[1].get(i) + " = " +
                        finalScaledScores[2].get(i));
            }

        }
    }

    //TODO - incomplete
    private void getScaledScoreACT() {
        Scanner fileScanner = setScanner(test.getTestFileNames());

    }

    /**
     * Gets the scaled score for the given test type if it is not the ACT.
     */
    private void getScaledScoreOthers() {
        scaledScores = new ArrayList[numSections];
        for(int i = 0; i < scaledScores.length; i++)
            scaledScores[i] = new ArrayList<>();

        testFileNames = new ArrayList<>();

        String listOfFileNames =
                "resources/" + test.getTestType() + "/TestFileNames" +
                test.getTestType() + ".txt";
        Scanner scannerFileNames = setScanner(listOfFileNames);
        //read in each line from the Scanner scannerFileNames and use that
        // for getting the scaled score from the file with the name of that
        // read in line
        while(scannerFileNames.hasNext()) {
            String currentFileName = "resources/" + test.getTestType() + "/" +
                    scannerFileNames.nextLine();
            testFileNames.add(currentFileName);

            for(int i = 0; i < numSections; i++)
                add1ScaledScore1Curve(currentFileName, i);
        }
    }

    /**
     * Gets a single scaled score for a single curve and add its to the
     * corresponding location within scaledScores.
     * @param currentFileName the file name to access for 1 curve
     * @param scoreIndex      the index of the score to find
     */
    private void add1ScaledScore1Curve(String currentFileName,
                                       int scoreIndex) {
        Scanner scannerTest = setScanner(currentFileName);
        while(scannerTest.hasNext()) {
            String lineIn = scannerTest.nextLine();
            String[] scoresIn = lineIn.split("\t");
            int valueToCheck = rawScores[scoreIndex];

            //for SAT, PSAT, and PSAT10, math curve is scored with Math No
            // Calc and Math Calc together
            if(!"ACT".equals(test.getTestType()) && scoreIndex == 2)
                valueToCheck = rawScores[2] + rawScores[3];

            if(!"XX".equals(scoresIn[0]) &&
                    Integer.parseInt(scoresIn[0]) == valueToCheck) {
                scaledScores[scoreIndex].add(Integer.parseInt(scoresIn[scoreIndex + 1]));
                break;
            }
        }
    }

    /**
     * Find out which test the user took and passes that to a new instance of 
     * Test.
     */
    private void getTestType() {
        Scanner scannerUser = new Scanner(System.in);

        while(true) {
            System.out.println("Are you checking scores for the " +
                    generateTypeTestPrompt() + "?");

            String nextLine = scannerUser.nextLine().trim();
            if(Arrays.asList(testTypes).contains(nextLine.toUpperCase())) {
                if("ACT".equals(nextLine))
                    numSections = 4;
                else
                    //3 sections for non ACT tests, because only 3 values to
                    // compare curves for, instead of 4, because math score is
                    // curved together
                    numSections = 3;

                test = new Test(nextLine.toUpperCase());
                break;
            }
            
            System.out.println("Enter a valid test type.");
        }
    }

    /**
     * Generate the prompt to ask for what type of test the user wants to
     * check scores for.
     * @return the prompt
     */
    private String generateTypeTestPrompt() {
        if(testTypes.length == 1)
            return testTypes[0];
        else if(testTypes.length == 2)
            return testTypes[0] + " or " + testTypes[1];
        else {
            String prompt = "";
            for(int i = 0; i < testTypes.length; i++) {
                //make sure is not the last one in the list
                if(i != testTypes.length - 1)
                    prompt += testTypes[i] + ", ";
                else
                    prompt += "or " + testTypes[i];
            }

            return prompt;
        }
    }

    /**
     * Figure out the number of correct answers for each section within that
     * test.
     */
    private void getRawScores() {
        //get the input from the user about their raw score
        Scanner scannerUser = new Scanner(System.in);
        int[] rawScores = new int[4];

        //get user input
        int index = 0;
        while(index < rawScores.length) {
            //prompt user
            System.out.println("What is your " + test.getQuestionTypes()[index] +
                    " score? Enter a positive number, like 15, for number " +
                    "of questions right, or enter a negative number, like " +
                    "-10, for number of questions missed.");
            String scoreString = scannerUser.nextLine();

            //check if the input String is an int. If so, parse it and save
            // it like how the prompt says.
            try{
                int score = Integer.parseInt(scoreString);

                //if scoreString starts with a negative, then this means the
                // user entered the number of missed questions, so convert to
                // number of correct answers
                if(scoreString.charAt(0) == '-')
                    score = test.getNumQuestions()[index] + score;

                rawScores[index] = score;
                index++;
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number. Try again.");
            }
        }

        this.rawScores = rawScores;
    }

    /**
     * Sets a Scanner to the given file and returns the Scanner.
     * @param fileName the name of the file to read
     * @return the Scanner set to the given file
     */
    public static Scanner setScanner(String fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Scanner input file not found. Consult" +
                    " William Tang (Monta Vista 2021-2022) for assistance.");
            System.exit(1);
        }

        return scanner;
    }
}

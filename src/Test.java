import java.util.Scanner;

public class Test {

    private static final String testInformationFile =
            "resources/TestInformation.txt";
    public static final String splitCharacter = ";";

    private String testType;
    private String testFileNames;
    private String[] questionTypes;
    private int[] numQuestions;

    public Test(String testTypeIn) {
        readInformationToSet(testTypeIn);
    }

    public String getTestType() {
        return testType;
    }

    public String getTestFileNames() {
        return testFileNames;
    }

    public String[] getQuestionTypes() {
        return questionTypes;
    }

    public int[] getNumQuestions() {
        return numQuestions;
    }

    /**
     * Sets the information about the given test.
     * @param testTypeIn the type of test this is
     */
    public void readInformationToSet(String testTypeIn) {
        Scanner scanner = TestScore.setScanner(testInformationFile);

        testType = testTypeIn;
        testFileNames =
                "resources/" + testTypeIn + "/TestFileNames" + testTypeIn +
                        ".txt";

        while(scanner.hasNext()) {
            String lineIn = scanner.nextLine();
            if(testType.equals(lineIn)) {
                questionTypes = scanner.nextLine().split(splitCharacter);

                //gets the number of questions for each question type and
                // parses each one into an int and stores it
                String[] numQuestionsString =
                        scanner.nextLine().split(splitCharacter);
                numQuestions = new int[4];
                for(int i = 0; i < numQuestionsString.length; i++)
                    numQuestions[i] = Integer.parseInt(numQuestionsString[i]);
            }
        }
    }

}

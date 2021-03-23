import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MakeTestCurve {

    private Scanner in, in2;
    private PrintWriter out;

    public static void main(String[] args) {
        MakeTestCurve score = new MakeTestCurve();
        score.run();
    }

    private void run() {
        String[] scannerFiles = {"Curves Reading.txt", "Curves Writing.txt",
                "Curves Math.txt"};

        int[][] reading = new int[53][13];
        int[][] writing = new int[45][13];
        int[][] math = new int[59][13];

        int[] rowMax = {52, 44, 58};

        //store all the values in the arrays
        for(int iteration = 0; iteration < scannerFiles.length; iteration++) {
            setScanner(scannerFiles[iteration]);
            for(int row = 0; row <= rowMax[iteration]; row++) {
                for(int col = 0; col < 13; col++) {
                    int nextNum = in.nextInt();
                    if(iteration == 0)
                        reading[row][col] = nextNum;
                    else if(iteration == 1)
                        writing[row][col] = nextNum;
                    else
                        math[row][col] = nextNum;
                }
            }
        }

        setScanner2("TestFileNamesCopy.txt");
        int col = 0;
        while(in2.hasNext()){
            String fileName = in2.nextLine();
            setPrintWriter(fileName);
            for(int row = 0; row <= rowMax[2]; row++) {
                out.print(row + " ");
                out.print(math[row][col] + " ");

                if(row > rowMax[0])
                    out.print("XX ");
                else
                    out.print(reading[row][col] + " ");

                if(row > rowMax[1])
                    out.print("XX");
                else
                    out.print(writing[row][col]);

                out.println();
            }

            col++;
            out.close();
        }
    }

    private void setScanner(String fileName) {
        //creates the Scanner to read in
        in = null;
        File fileIn = new File(fileName);
        try {
            in = new Scanner(fileIn);
        }
        catch(FileNotFoundException e) {
            System.out.println("INPUT file was not found.");
            System.exit(-1);
        }
    }

    private void setScanner2(String fileName) {
        //creates the Scanner to read in
        in2 = null;
        File fileIn = new File(fileName);
        try {
            in2 = new Scanner(fileIn);
        }
        catch(FileNotFoundException e) {
            System.out.println("INPUT file 2 was not found.");
            System.exit(-1);
        }
    }

    private void setPrintWriter(String fileName){
        //creates the PrintWriter to print out
        out = null;
        File fileOut = new File(fileName);
        try {
            out = new PrintWriter(fileOut);
        }
        catch(IOException e) {
            System.out.println("OUTPUT file not found.");
            System.exit(-2);
        }
    }
}

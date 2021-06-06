package pl.likonski;


import pl.likonski.extraction.DataExtractor;
import pl.likonski.extraction.DataWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Application {

    public static void main(String[] args) {

//        System.out.println("NOD, CD, BFN, TAP");
//        for(String arg : args){
//            System.out.println(new Calculator(new DataExtractor(arg)));
//
//        }

        System.out.println("start");

        try{
            PrintWriter printWriter = new PrintWriter(args[1] + "\\smellsResult.txt");
            printWriter.println("NOD, CD, BFN, TAP");
            String fullPath = args[0];

            File dirPath = new File(fullPath);
            String directories[] = dirPath.list();

            for(String dir : directories){
                Calculator calculator = new Calculator(new DataExtractor(fullPath + "\\" + dir + "\\"));
                DataWriter dataWriter = new DataWriter(calculator, args[1], dir);
                dataWriter.regressionWrite();
                System.out.println("created regression file for: " + dir);
                printWriter.println(dir + ", " + calculator);
            }
            printWriter.close();
            System.out.println("created smells file");

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println("end");

    }
}

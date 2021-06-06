package pl.likonski.extraction;

import pl.likonski.Calculator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DataWriter {

    private Calculator calculator;
    private String savePath;
    private String dir;

    public DataWriter(Calculator calculator, String savePath, String dir){
        this.calculator = calculator;
        this.savePath = savePath;
        this.dir = dir;
    }

    public void regressionWrite(){

        int[] dataLines = calculator.changedLinesCount();

        try {
            PrintWriter printWriter = new PrintWriter(savePath + "\\" + dir + "Regression.txt");

            printWriter.println("DAY,AMOUNT");

            for(int i=0; i<dataLines.length; i++){
                printWriter.println(i+1 + "," + dataLines[i]);
            }
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}

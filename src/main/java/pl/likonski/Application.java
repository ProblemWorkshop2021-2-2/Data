package pl.likonski;


import pl.likonski.extraction.DataExtractor;

public class Application {

    public static void main(String[] args) {

        System.out.println("NOD, CD, BFN, TAP");
        for(String arg : args){
            System.out.println(new Calculator(new DataExtractor(arg)));
        }

    }
}

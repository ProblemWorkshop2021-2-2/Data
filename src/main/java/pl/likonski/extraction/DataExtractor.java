package pl.likonski.extraction;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DataExtractor {

    private String dirPath;
    private Commit[] commits;

    public DataExtractor(String dirPath) {
        this.dirPath = dirPath;
        try {
            commits = Arrays.stream(readFromFile("commits.csv")).map(com -> new Commit(com[0], com[1], com[2], com[3],
                    com[4], com[5], com[6],
                    com[7])).toArray(Commit[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        for(int i = 0; i < commits.length; i++){
            if(!StringUtils.isNumeric(commits[i].author_timestamp)){
                System.out.println("row nr: " + i + 2 + "         " + commits[i]);
            }
        }

        //System.out.println(Arrays.toString(commits));


    }

    private String[][] readFromFile(String fileName){
        String[] rows;
        String[][] values = null;
        try {
            rows = Files.readString(Paths.get(dirPath + fileName)).split("[" + System.getProperty("line.separator") + "]");
            values = Arrays.stream(rows).skip(1).map(row -> row.split(",")).toArray(String[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }



}

package pl.likonski.extraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DataExtractor {

    private String dirPath;
    private Commit[] commits;
    private Contributor[] contributors;
    private Subscriber[] subscribers;
    private User[] users;

    public DataExtractor(String dirPath) {
        this.dirPath = dirPath;
        try {
            commits = Arrays.stream(readFromFile("commits.csv", "$")).map(com -> new Commit(com[0], com[1], com[2], com[3],
                    Integer.parseInt(com[4]), com[5], com[6],
                    Integer.parseInt(com[7]))).toArray(Commit[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(commits));


        try {
            contributors = Arrays.stream(readFromFile("contributors.csv", ","))
                    .map(com -> new Contributor(Integer.parseInt(com[0]), com[1], Integer.parseInt(com[2]), com[3],
                    com[4])).toArray(Contributor[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        //System.out.println(Arrays.toString(contributors));


        try {
            subscribers = Arrays.stream(readFromFile("subscribers.csv", ","))
                    .map(com -> new Subscriber(Integer.parseInt(com[0]), com[1], com[2], com[3]
                            )).toArray(Subscriber[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        //System.out.println(Arrays.toString(subscribers));


        try {
            users = Arrays.stream(readFromFile("users.csv", "$"))
                    .map(com -> new User(Integer.parseInt(com[0]), com[1], com[2], com[3],
                            com[4], com[5], com[6])).toArray(User[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        //System.out.println(Arrays.toString(users));


    }

    private String[][] readFromFile(String fileName, String separator){
        String[] rows;
        String[][] values = null;
        try {
            rows = Files.readString(Paths.get(dirPath + fileName)).split("[" + System.getProperty("line.separator") + "]");
            values = Arrays.stream(rows).skip(1).map(row -> row.split("[" + separator + "]")).toArray(String[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }



}

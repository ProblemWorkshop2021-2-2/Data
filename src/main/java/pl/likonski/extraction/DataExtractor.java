package pl.likonski.extraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class DataExtractor {

    private String dirPath;
    private Commit[] commits;
    private Contributor[] contributors;
    private Subscriber[] subscribers;
    private User[] users;
    private ChangedFile[] changedFiles;
    private InsertionsCommits[] insertionsCommits;
    private int numberOfFiles;

    public DataExtractor(String dirPath) {
        this.dirPath = dirPath;
        try {
            commits = Arrays.stream(readFromFile("commits.csv", "$")).map(com -> new Commit(com[0], com[1], com[2], com[3],
                    Integer.parseInt(com[4]), com[5], com[6],
                    Integer.parseInt(com[7]))).toArray(Commit[]::new);

            Arrays.sort(commits, new Comparator<Commit>() {
                @Override
                public int compare(Commit o1, Commit o2) {
                    return o1.compareTo(o2);
                }
            });

        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        try {
            contributors = Arrays.stream(readFromFile("contributors.csv", ","))
                    .map(com -> new Contributor(Integer.parseInt(com[0]), com[1], Integer.parseInt(com[2]), com[3],
                    com[4])).toArray(Contributor[]::new);

        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        try {
            subscribers = Arrays.stream(readFromFile("subscribers.csv", ","))
                    .map(com -> new Subscriber(Integer.parseInt(com[0]), com[1], com[2], com[3]
                            )).toArray(Subscriber[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        try {
            users = Arrays.stream(readFromFile("users.csv", "$"))
                    .map(com -> new User(Integer.parseInt(com[0]), com[1], com[2], com[3],
                            com[4], com[5], com[6])).toArray(User[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        try {
            changedFiles = Arrays.stream(readFromFile("changed_files.txt", ","))
                    .map(com -> new ChangedFile(com[0], com[1])).toArray(ChangedFile[]::new);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        try {
            numberOfFiles = Integer.parseInt(Files.readAllLines(Paths.get(dirPath + "files_in_repo.txt")).get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            insertionsCommits = Arrays.stream(readFromFile("insertions_commits.csv", ","))
                    .map(com -> new InsertionsCommits(com[0],
                            com[1],
                            (com.length>=5?Integer.parseInt(com[2].trim().split(" ")[0]):0),
                            (com.length>=5?Integer.parseInt(com[3].trim().split(" ")[0]):0),
                            (com.length>=5?Integer.parseInt(com[4].trim().split(" ")[0]):0))).toArray(InsertionsCommits[]::new);

        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public String[][] readFromFile(String fileName, String separator){
        String[] rows;
        String[][] values = null;
        try {
            rows = Files.readString(Paths.get(dirPath + fileName)).split("[" + System.getProperty("line.separator") + "]");
            if(fileName.equals("insertions_commits.csv")){
                values = Arrays.stream(rows).skip(2).map(row -> row.split("[" + separator + "]")).toArray(String[][]::new);
            }
            else {
                values = Arrays.stream(rows).skip(1).map(row -> row.split("[" + separator + "]")).toArray(String[][]::new);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    public String getDirPath() {
        return dirPath;
    }

    public Commit[] getCommits() {
        return commits;
    }

    public Contributor[] getContributors() {
        return contributors;
    }

    public Subscriber[] getSubscribers() {
        return subscribers;
    }

    public User[] getUsers() {
        return users;
    }

    public ChangedFile[] getChangedFiles() {
        return changedFiles;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public InsertionsCommits[] getInsertionsCommits() {
        return insertionsCommits;
    }
}

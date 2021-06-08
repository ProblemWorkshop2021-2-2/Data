package pl.likonski.extraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class DataExtractor {

    private String dirPath;
    private Commit[] commits;
    private Contributor[] contributors;
    private Subscriber[] subscribers;
    private User[] users;
    private ChangedFile[] changedFiles;
    private LinesOfCode[] linesOfCode;
    private InsertionsCommits[] insertionsCommits;
    private int numberOfFiles;
    private int NR = 0;
    private int FN = 0;

    public DataExtractor(String dirPath) {
        this.dirPath = dirPath;
        try {
            commits = Arrays.stream(readFromFile("commits.csv", "$", 1)).map(com -> new Commit(com[0], com[1], com[2], com[3],
                    Integer.parseInt(com[4]), com[5], com[6],
                    Integer.parseInt(com[7]))).toArray(Commit[]::new);

            Arrays.sort(commits, new Comparator<Commit>() {
                @Override
                public int compare(Commit o1, Commit o2) {
                    return o1.compareTo(o2);
                }
            });

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            contributors = Arrays.stream(readFromFile("contributors.csv", ",", 1))
                    .map(com -> new Contributor(Integer.parseInt(com[0]), com[1], Integer.parseInt(com[2]), com[3],
                            com[4])).toArray(Contributor[]::new);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            subscribers = Arrays.stream(readFromFile("subscribers.csv", ",", 1))
                    .map(com -> new Subscriber(Integer.parseInt(com[0]), com[1], com[2], com[3]
                    )).toArray(Subscriber[]::new);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            users = Arrays.stream(readFromFile("users.csv", "$", 1))
                    .map(com -> new User(Integer.parseInt(com[0]), com[1], com[2], com[3],
                            com[4], com[5], com[6])).toArray(User[]::new);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            changedFiles = Arrays.stream(readFromFile("changed_files.txt", ",", 1))
                    .map(com -> new ChangedFile(com[0], com[1])).toArray(ChangedFile[]::new);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (Files.exists(Path.of(dirPath + "lines_of_code.txt"))) {
            try {
                linesOfCode = Arrays.stream(readFromFile("lines_of_code.txt", " ", 0)).filter(com -> com.length >= 2)
                        .map(com -> new LinesOfCode(Integer.parseInt(com[0]), com[1])).toArray(LinesOfCode[]::new);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try {
            numberOfFiles = Integer.parseInt(Files.readAllLines(Paths.get(dirPath + "files_in_repo.txt")).get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Files.exists(Path.of(dirPath + "releases.csv"))) {
            try {
                NR = Integer.parseInt(Files.readAllLines(Paths.get(dirPath + "releases.csv")).get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (Files.exists(Path.of(dirPath + "milestones.csv"))) {
            try {
                FN = (int) Files.lines(Path.of(dirPath + "milestones.csv")).count() - 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            insertionsCommits = Arrays.stream(readFromFile("insertions_commits.csv", ",", 2))
                    .map(com -> new InsertionsCommits(com[0],
                            com[1],
                            trimInsertionsCommits(new String[]{(com.length >= 3) ? com[2] : "", (com.length >= 4) ? com[3] : "", (com.length >= 5) ? com[4] : ""}, InsertionType.files),
                            trimInsertionsCommits(new String[]{(com.length >= 3) ? com[2] : "", (com.length >= 4) ? com[3] : "", (com.length >= 5) ? com[4] : ""}, InsertionType.insertions),
                            trimInsertionsCommits(new String[]{(com.length >= 3) ? com[2] : "", (com.length >= 4) ? com[3] : "", (com.length >= 5) ? com[4] : ""}, InsertionType.deletions)))
                    .toArray(InsertionsCommits[]::new);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public enum InsertionType {
        files("files changed"),
        insertions("insertions(+)"),
        deletions("deletions(-)");

        public final String name;

        InsertionType(String name) {
            this.name = name;
        }
    }

    public int trimInsertionsCommits(String[] input, InsertionType type){
        for (String s : input) {
            if(s.contains(type.name)) return Integer.parseInt(s.trim().split(" ")[0]);
        }
        return 0;
    }

    public String[][] readFromFile(String fileName, String separator, int skip) {
        String[] rows;
        String[][] values = null;
        try {
            rows = Files.readString(Paths.get(dirPath + fileName)).split("[" + System.getProperty("line.separator") + "]");
            values = Arrays.stream(rows).skip(skip).map(row -> row.trim().split("[" + separator + "]")).toArray(String[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0][0];
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

    public LinesOfCode[] getLinesOfCode() {
        return linesOfCode;
    }

    public InsertionsCommits[] getInsertionsCommits() {
        return insertionsCommits;
    }

    public int getNR() {
        return NR;
    }

    public int getFN() {
        return FN;
    }
}

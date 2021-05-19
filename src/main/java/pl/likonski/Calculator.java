package pl.likonski;

import pl.likonski.extraction.*;

import java.util.Arrays;
import java.util.HashMap;

public class Calculator {
    DataExtractor dataExtractor;
    private double avgContributions;
    //private HashMap<String, User> coreUsersMap = new HashMap<>();
    private HashMap<String, Contributor> coreContributorsMap = new HashMap<>();
    private HashMap<String, Boolean> files = new HashMap<>();
    private int NOD;
    private int CD;
    private double BFN;
    private double TAP;
    //private double TFC;

    public Calculator(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;

        for (Contributor c : dataExtractor.getContributors()) {
            avgContributions += c.contributions;
        }
        NOD = dataExtractor.getContributors().length;
        avgContributions = avgContributions / NOD;

        CD = 0;

        for (Contributor c : dataExtractor.getContributors()) {
            if (c.contributions > avgContributions) {
                c.isCore = true;
                CD++;
                coreContributorsMap.put(c.login, c);
            }
        }

        BFN = (double) CD / NOD;



        Arrays.stream(dataExtractor.getContributors()).forEach(contributor -> {
            for(Commit commit : dataExtractor.getCommits()){
                if(commit.committer_timestamp > contributor.lastDate)contributor.lastDate = commit.committer_timestamp;
                if(commit.committer_timestamp < contributor.firstDate)contributor.firstDate = commit.committer_timestamp;
            }
        });

        TAP = 0;
        int num = 0;
        for(Contributor contributor : dataExtractor.getContributors()){
            if(contributor.lastDate != -1 && contributor.firstDate != Integer.MAX_VALUE) {
                TAP += contributor.lastDate - contributor.firstDate;
                num++;
            }
        }

        TAP /= num;

//        Arrays.stream(dataExtractor.getUsers()).forEach(user -> {
//            if (coreContributorsMap.containsKey(user.login)) {
//                user.isCore = true;
//                coreUsersMap.put(user.name, user);
//            }
//        });
//
//        for (ChangedFile change : dataExtractor.getChangedFiles()) {
//            Boolean file = files.putIfAbsent(change.file, false);
//            if (coreUsersMap.containsKey(change.changer)) {
//                file = true;
//            }
//        }
//
//        TFC = 0;
//
//        files.forEach((filePath, isCore) -> {
//            if (isCore) TFC++;
//        });
//
//        TFC /= files.size();
    }

    @Override
    public String toString() {
        return
                NOD + ", " + CD + ", " + BFN + ", " + TAP;// + ", " + TFC;
    }
}

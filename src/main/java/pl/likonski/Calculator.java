package pl.likonski;

import pl.likonski.extraction.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Calculator {
    DataExtractor dataExtractor;
    private double avgContributions;
    private HashMap<String, Contributor> coreContributorsMap = new HashMap<>();
    private HashMap<String, Boolean> files = new HashMap<>();
    private HashMap<String, HashSet<String>> filesAndChangers = new HashMap<>();
    private HashMap<String, Commiter> commiters = new HashMap<>();
    private int numberOfContributors;
    private int CD;
    private double RCD;
    private double BFN;
    private int NOD;
    private double TAP;
    private double LCP;
    private double DC;
    //private double TFC;
    private HashMap<String, Integer> insertionsMap = new HashMap<>();

    public Calculator(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;

        for (Contributor c : dataExtractor.getContributors()) {
            avgContributions += c.contributions;
        }
        numberOfContributors = dataExtractor.getContributors().length;
        avgContributions = avgContributions / numberOfContributors;

        CD = 0;

        for (Contributor c : dataExtractor.getContributors()) {
            if (c.contributions > avgContributions) {
                c.isCore = true;
                CD++;
                coreContributorsMap.put(c.login, c);
            }
        }

        RCD = (double) CD / numberOfContributors;
        BFN = 1 - RCD;


        for (Commit commit : dataExtractor.getCommits()) {
            if (!commiters.containsKey(commit.committer_name)) {
                commiters.put(commit.committer_name, new Commiter(commit.committer_name));
            }
            if (!commiters.get(commit.committer_name).days.contains(commit.committer_timestamp / 86400)) {
                commiters.get(commit.committer_name).days.add(commit.committer_timestamp / 86400);
            }
        }

        commiters.forEach((name, commiter) -> {
            for (Integer day : commiter.days) {
                if (day > commiter.lastDate) commiter.lastDate = day;
                if (day < commiter.firstDate) commiter.firstDate = day;
            }
            commiter.active = (double) commiter.days.size() / (commiter.lastDate - commiter.firstDate + 1);
        });


        TAP = 0;
        NOD = commiters.size();
        for(Commiter commiter : commiters.values()) {
            TAP += commiter.active;
        }
        TAP /= NOD;

        LCP = 0;

        if(dataExtractor.getLinesOfCode() != null) {
            for (InsertionsCommits insertions : dataExtractor.getInsertionsCommits()) {
                LCP += insertions.insert;
                LCP += insertions.delete;
            }

            int linesNum = 0;


            for (LinesOfCode lines : dataExtractor.getLinesOfCode()) {
                if(lines.name.contains("total"))
                    linesNum += lines.number;
            }

            LCP /= linesNum;
        }

        DC = 0;

       for(ChangedFile file : dataExtractor.getChangedFiles()) {
           if (!filesAndChangers.containsKey(file.file)) filesAndChangers.put(file.file, new HashSet<>(Arrays.asList(file.changer)));
           else filesAndChangers.get(file.file).add(file.changer);
       }
        for(HashSet<String> changers : filesAndChangers.values()){
            DC += changers.size() * (changers.size() - 1);
        }
        DC /= NOD;

//        Arrays.stream(dataExtractor.getContributors()).forEach(contributor -> {
//            for(Commit commit : dataExtractor.getCommits()){
//                if(commit.committer_timestamp > contributor.lastDate)contributor.lastDate = commit.committer_timestamp;
//                if(commit.committer_timestamp < contributor.firstDate)contributor.firstDate = commit.committer_timestamp;
//            }
//        });
//
//        TAP = 0;
//        int num = 0;
//        for(Contributor contributor : dataExtractor.getContributors()){
//            if(contributor.lastDate != -1 && contributor.firstDate != Integer.MAX_VALUE) {
//                TAP += contributor.lastDate - contributor.firstDate;
//                num++;
//            }
//        }
//
//        TAP /= num;

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

        for(InsertionsCommits insertionsCommits : dataExtractor.getInsertionsCommits()){
            if(insertionsMap.containsKey(insertionsCommits.commith_hash)){
                insertionsMap.put(insertionsCommits.commith_hash, insertionsMap.get(insertionsCommits.commith_hash)+insertionsCommits.insert);
            }
            else{
                insertionsMap.put(insertionsCommits.commith_hash,insertionsCommits.insert);
            }
        }
    }

    public int[] changedLinesCount(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp beginDate = new Timestamp(dataExtractor.getCommits()[dataExtractor.getCommits().length-1].committer_timestamp*1000L);
        Timestamp endDate = new Timestamp(dataExtractor.getCommits()[0].committer_timestamp*1000L);
        Long daysAmount = (endDate.getTime()-beginDate.getTime())/(1000*60*60*24);
        Calendar comparedDate = Calendar.getInstance();
        comparedDate.setTime(beginDate);
        int iterator = 0;
        int[] changedLines = new int[daysAmount.intValue()+10];
        Arrays.fill(changedLines,0);

        int i = dataExtractor.getCommits().length-1;
        while(i>=0){

            if(!insertionsMap.containsKey(dataExtractor.getCommits()[i].commit_hash)){
                i--;
            }
            else {

                if (dateFormat.format(dataExtractor.getCommits()[i].committer_timestamp * 1000L)
                        .equals(dateFormat.format(comparedDate.getTime()))) {

                    changedLines[iterator] += insertionsMap.get(dataExtractor.getCommits()[i].commit_hash);
                    i--;

                } else {
                    comparedDate.add(Calendar.DATE, 1);
                    iterator++;
                    if (iterator > daysAmount.intValue()+10) {
                        break;
                    }
                }
            }
        }

        return changedLines;
    }


    @Override
    public String toString() {
                //NOD, CD, RCD, BFN, TAP, LCP, DC, NR, FN
        return NOD + ", " + CD + ", " + RCD + ", " + BFN + ", " + TAP + ", " + LCP + ", " + DC + ", " + dataExtractor.getNR() + ", " + dataExtractor.getFN();
    }
}

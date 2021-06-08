package pl.likonski.extraction;

import java.util.ArrayList;

public class Commiter {
    public String name;
    public ArrayList<Integer> days;
    public int firstDate = Integer.MAX_VALUE;
    public int lastDate = -1;
    public double active;

    public Commiter(String name) {
        this.name = name;
        days = new ArrayList<>();
    }
}

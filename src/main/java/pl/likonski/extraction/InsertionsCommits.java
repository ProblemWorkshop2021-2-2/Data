package pl.likonski.extraction;

public class InsertionsCommits {

    public String tree_hash;
    public String commith_hash;
    public int changed;
    public int insert;
    public int delete;

    public InsertionsCommits(String tree_hash, String commith_hash, int changed, int insert, int delete) {
        this.tree_hash = tree_hash;
        this.commith_hash = commith_hash;
        this.changed = changed;
        this.insert = insert;
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "InsertionsCommits{" +
                "tree_hash='" + tree_hash + '\'' +
                ", commith_hash='" + commith_hash + '\'' +
                ", changed=" + changed +
                ", insert=" + insert +
                ", delete=" + delete +
                '}';
    }
}

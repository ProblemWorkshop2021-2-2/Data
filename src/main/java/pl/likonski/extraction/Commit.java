package pl.likonski.extraction;

public class Commit {
    public String tree_hash;
    public String commit_hash;
    public String author_name;
    public String author_email;
    public int author_timestamp;
    public String committer_name;
    public String committer_email;
    public int committer_timestamp;

    public Commit(String tree_hash, String commit_hash, String author_name, String author_email,
                  int author_timestamp, String committer_name, String committer_email, int committer_timestamp) {
        this.tree_hash = tree_hash;
        this.commit_hash = commit_hash;
        this.author_name = author_name;
        this.author_email = author_email;
        this.author_timestamp = author_timestamp;
        this.committer_name = committer_name;
        this.committer_email = committer_email;
        this.committer_timestamp = committer_timestamp;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "tree_hash='" + tree_hash + '\'' +
                ", commit_hash='" + commit_hash + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_email='" + author_email + '\'' +
                ", author_timestamp=" + author_timestamp +
                ", committer_name='" + committer_name + '\'' +
                ", committer_email='" + committer_email + '\'' +
                ", committer_timestamp=" + committer_timestamp +
                '}' + '\n';
    }
}

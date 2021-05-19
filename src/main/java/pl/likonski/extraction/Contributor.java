package pl.likonski.extraction;

public class Contributor {
    public int id;
    public String login;
    public int contributions;
    public String site_admin;
    public String type;
    public boolean isCore = false;
    public int firstDate = Integer.MAX_VALUE;
    public int lastDate = -1;

    public Contributor(int id, String login, int contributions, String site_admin, String type) {
        this.id = id;
        this.login = login;
        this.contributions = contributions;
        this.site_admin = site_admin;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", contributions='" + contributions + '\'' +
                ", site_admin='" + site_admin + '\'' +
                ", type='" + type + '\'' +
                '}' + '\n';
    }
}

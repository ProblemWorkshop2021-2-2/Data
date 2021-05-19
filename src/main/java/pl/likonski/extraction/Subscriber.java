package pl.likonski.extraction;

public class Subscriber {
    public int id;
    public String login;
    public String site_admin;
    public String type;

    public Subscriber(int id, String login, String site_admin, String type) {
        this.id = id;
        this.login = login;
        this.site_admin = site_admin;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", site_admin='" + site_admin + '\'' +
                ", type='" + type + '\'' +
                '}' + '\n';
    }
}

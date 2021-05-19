package pl.likonski.extraction;

public class User {
    public int id;
    public String login;
    public String site_admin;
    public String type;
    public String name;
    public String email;
    public String hireable;

    public User(int id, String login, String site_admin, String type, String name, String email, String hireable) {
        this.id = id;
        this.login = login;
        this.site_admin = site_admin;
        this.type = type;
        this.name = name;
        this.email = email;
        this.hireable = hireable;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", site_admin='" + site_admin + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hireable='" + hireable + '\'' +
                '}' + '\n';
    }
}

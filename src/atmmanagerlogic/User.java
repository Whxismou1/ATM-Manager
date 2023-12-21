package atmmanagerlogic;

public class User {
    private int id;
    private String fullName;
    private String dni;
    private String email;
    private String username;
    private String password;

    public User(int id, String name, String dni, String email, String username, String password) {
        this.id = id;
        this.fullName = name;
        this.dni = dni;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected int getId() {
        return this.id;
    }

    protected String getFullName() {
        return this.fullName;
    }

    protected String getDni() {
        return this.dni;
    }

    protected String getEmail() {
        return this.email;
    }

    protected String getUsername() {
        return this.username;
    }

    protected String getPassword() {
        return this.password;
    }

}

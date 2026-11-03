package mobility;
public class User {
    private final String id;
    private final String name;
    public User(String id, String name) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) throw new IllegalArgumentException("Invalid user");
        this.id = id;
        this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }
}



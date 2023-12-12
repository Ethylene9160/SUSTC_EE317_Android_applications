package my_music;


public class User {
    public static final int DEFAULT_AVAR = 0;

    public static final String DEFAULT_KEY = "666666";
    private String name;
    private String id, key;
    private int avar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvar() {
        return avar;
    }

    public void setAvar(int avar) {
        this.avar = avar;
    }

    public User(String name, String id, int avar) {
        this(name, id, DEFAULT_KEY, avar);
    }

    public User(String name, String id, String key, int avar){
        this.name = name;
        this.id = id;
        this.key = key;
        this.avar = (avar == 0? DEFAULT_AVAR : avar);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(String name, String id, String key){
        this(name, id, key, DEFAULT_AVAR);
    }

    public User(String name, String id){
        this(name, id, DEFAULT_AVAR);
    }

    public String getGeneralInfo(){
        return String.format("%s&%s", this.id, this.name);
    }
}

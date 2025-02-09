package pl.lis.demo.model;


public class EmojiData {
    private String id;
    private String name;
    private boolean isCustom;
    public EmojiData(String id, boolean isCustom, String name) {
        this.id = id;
        this.isCustom = isCustom;
        this.name = name;
    }

    @Override
    public String toString() {
        return !isCustom? "unicode: " + id : "<:" + name +":"+ id + ">";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCustom() {
        return isCustom;
    }
}
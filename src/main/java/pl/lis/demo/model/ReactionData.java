package pl.lis.demo.model;

public class ReactionData extends EmojiData{
    private int numberOfReactions;

    public ReactionData(String id, String name, int numberOfReactions, boolean isCustom) {
        super(id, isCustom, name);
        this.numberOfReactions = numberOfReactions;
    }

    public int getNumberOfReactions() {
        return numberOfReactions;
    }

    public void setNumberOfReactions(int numberOfReactions) {
        this.numberOfReactions = numberOfReactions;
    }

    @Override
    public String toString() {
        return "numberOfReactions: " + " " + "\n" + super.toString();
    }

}

package comp208.mzaber;

public class Card {
    int col;
    int row;
    int resourceId;

    public Card(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}

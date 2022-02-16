package co.tiagoaguiar.codelab.myapplication;

public class MainItem {
    private int id;
    private int drawableId;
    private int textStringId;
    private int color;

    public MainItem(int id, int drawableId, int textStringId, int color) {
        this.id = id;
        this.drawableId = drawableId;
        this.textStringId = textStringId;
        this.color = color;
    }

    private void setColor(int color) {
        this.color = color;
    }

    private void setDrawableid(int drawableId) {
        this.drawableId = drawableId;
    }

    private void setTextStringId(int textStringId) {
        this.textStringId = textStringId;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getTextStringId() {
        return textStringId;
    }


}

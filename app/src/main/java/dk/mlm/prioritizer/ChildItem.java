package dk.mlm.prioritizer;

import java.io.Serializable;

/**
 * Created by ml on 24/07/15.
 */
public class ChildItem implements Serializable{
    private String name;
    private String listName;
    private int priority;

    public ChildItem(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

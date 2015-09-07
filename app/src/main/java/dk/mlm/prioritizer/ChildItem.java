package dk.mlm.prioritizer;

import java.io.Serializable;

/**
 * Created by ml on 24/07/15.
 */
public class ChildItem implements Serializable {
    private int id;
    private String name;
    private String listName;
    private int priority;

    public ChildItem() {
    }

    public ChildItem(String name) {
        this.name = name;
    }

    public ChildItem(int id, String name, String listName, int priority) {
        this.id = id;
        this.name = name;
        this.listName = listName;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "ChildItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listName='" + listName + '\'' +
                ", priority=" + priority +
                '}';
    }
}

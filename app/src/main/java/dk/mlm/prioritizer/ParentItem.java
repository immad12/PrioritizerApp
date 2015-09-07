package dk.mlm.prioritizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ml on 24/07/15.
 */
public class ParentItem implements Serializable {
    private int id;
    private String name;
    private List<ChildItem> childItems;

    public ParentItem() {
        childItems = new ArrayList<>();
    }

    public ParentItem(String name) {
        this.name = name;
        childItems = new ArrayList<>();
    }

    public ParentItem(int id, String name, List<ChildItem> childItems) {
        this.id = id;
        this.name = name;
        this.childItems = childItems;
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

    public void addChildItem(ChildItem childItem) {
        childItems.add(childItem);
    }

    public void setChildItems(List<ChildItem> childItems) {
        this.childItems = childItems;
    }

    public List<ChildItem> getChildItems() {
        return childItems;
    }

    @Override
    public String toString() {
        return "ParentItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childItems=" + childItems +
                '}';
    }
}

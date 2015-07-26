package dk.mlm.prioritizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ml on 24/07/15.
 */
public class ParentItem implements Serializable
{
    private String name;
    private List<ChildItem> childItems;

    public ParentItem(String name){
        this.name = name;
        childItems = new ArrayList<>();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void addChildItem(ChildItem childItem)
    {
        childItems.add(childItem);
    }

    public List<ChildItem> getChildItems()
    {
        return childItems;
    }
}

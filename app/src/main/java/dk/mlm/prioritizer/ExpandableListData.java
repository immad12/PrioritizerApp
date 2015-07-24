package dk.mlm.prioritizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ml on 24/07/15.
 * Test data
 */
public class ExpandableListData {
    public static Map<ParentItem, List<String>> getData()
    {
        Map<ParentItem, List<String>> expandableListDetail = new HashMap<>();

        ParentItem p1 = new ParentItem("TECHNOLOGY NEWS");
        ParentItem p2 = new ParentItem("ENTERTAINMENT NEWS");
        ParentItem p3 = new ParentItem("SCIENCE & ENVIRONMENT NEWS");


        List<String> technology = new ArrayList<String>();
        technology.add("Beats sued for noise-cancelling tech");
        technology.add("Wikipedia blocks US Congress edits");
        technology.add("Google quizzed over deleted links");
        technology.add("Nasa seeks aid with Earth-Mars links");
        technology.add("The Good, the Bad and the Ugly");

        List<String> entertainment = new ArrayList<String>();
        entertainment.add("Goldfinch novel set for big screen");
        entertainment.add("Anderson stellar in Streetcar");
        entertainment.add("Ronstadt receives White House honour");
        entertainment.add("Toronto to open with The Judge");
        entertainment.add("British dancer return from Russia");

        List<String> science = new ArrayList<String>();
//        science.add("Brain hub predicts negative events");
//        science.add("California hit by raging wildfires");
//        science.add("Rosetta's comet seen in close-up");
//        science.add("Secret of sandstone shapes revealed");

        expandableListDetail.put(p1, technology);
        expandableListDetail.put(p2, entertainment);
        expandableListDetail.put(p3, science);
        return expandableListDetail;
    }
}

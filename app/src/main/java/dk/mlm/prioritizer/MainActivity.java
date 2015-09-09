package dk.mlm.prioritizer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    // For saving and retrieving data
    private DatabaseHelper dbHelper;
    private List<ParentItem> savedDataLists;

    // Open activity to create a task for dobbelt clicking a list
    private boolean isDoubleTap = false;
    private int previousGroupPosition = -1;

    // To handle the ExpandableListView
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<ParentItem> expandableListTitle;
    private Map<ParentItem, List<ChildItem>> expandableListDetail = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For storing and retrieving data
        dbHelper = new DatabaseHelper(this);

        // Retrieve data from storage
        savedDataLists = dbHelper.getAllLists();
        if (savedDataLists != null) {
            for (ParentItem list : savedDataLists) {
                expandableListDetail.put(list, list.getChildItems());
            }
        }

        ImageButton ibtnAddList = (ImageButton) findViewById(R.id.imageAddList);
        ibtnAddList.setOnClickListener(this);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    }

    @Override
    public void onResume() {
        super.onResume();

        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        final Intent taskIntent = new Intent(this, AddTaskActivity.class);
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (previousGroupPosition == groupPosition && isDoubleTap) {
                    isDoubleTap = false;
                    previousGroupPosition = -1;

                    taskIntent.putExtra("ListName", expandableListTitle.get(groupPosition).getName());
                    taskIntent.putExtra("groupPosition", groupPosition);
                    taskIntent.putExtra("listSize", expandableListTitle.get(groupPosition).getChildItems().size());
                    startActivityForResult(taskIntent, 2);

                    return true;
                } else {
                    isDoubleTap = true;
                    previousGroupPosition = groupPosition;

                    return false;
                }
            }
        });

        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Implement functionality for viewing and editing a task
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition).getName()
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition).getName(), Toast.LENGTH_SHORT
                )
                        .show();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddListActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ParentItem parentResult = null;

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                parentResult = (ParentItem) data.getSerializableExtra("parentList");
                // Toast.makeText(getApplicationContext(), "Create List Worked", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                ChildItem childResult = (ChildItem) data.getSerializableExtra("childTask");
                int position = data.getIntExtra("position", 0);
                String listTitle = data.getStringExtra("listTitle");

                if (listTitle.equals(expandableListTitle.get(position).getName())) {
                    parentResult = expandableListTitle.get(position);
                    parentResult.addChildItem(childResult);

                    expandableListTitle.remove(position);
                }
            }
        }
        if (parentResult != null) {
            expandableListDetail.put(parentResult, parentResult.getChildItems());
        }

        expandableListTitle.add(parentResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package dk.mlm.prioritizer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskActivity extends ActionBarActivity implements View.OnClickListener {
    private DatabaseHelper dbHelper;

    private EditText txtName;
    private TextView taskListTitle;
    private int groupPosition;
    private int listSizeToPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DatabaseHelper(this);

        // Information for the task
        Intent intent = getIntent();
        taskListTitle = (TextView) findViewById(R.id.txtTaskListTitle);
        taskListTitle.setText(intent.getStringExtra("ListName"));
        groupPosition = intent.getIntExtra("groupPosition", 0);
        listSizeToPriority = intent.getIntExtra("listSize", 0);

        txtName = (EditText) findViewById(R.id.txtNameTask);

        Button btnCreate = (Button) findViewById(R.id.buttonCreateTask);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ChildItem task = new ChildItem(txtName.getText().toString());
        task.setPriority(++listSizeToPriority);
        task.setListName(taskListTitle.getText().toString());
        task = dbHelper.insertTask(task);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("childTask", task);
        returnIntent.putExtra("listTitle", taskListTitle.getText());
        returnIntent.putExtra("position", groupPosition);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
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

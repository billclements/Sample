package de.vogella.android.todos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import de.vogella.android.todos.contentprovider.MyTodoContentProvider;
import de.vogella.android.todos.database.TodoTable;

/*
 * TodoDetailActivity allows to enter a new todo item 
 * or to change an existing
 */
public class TodoDetailActivity extends Activity {
  private Spinner mCategory;
  private EditText mBrandText;
  private EditText mTypeText;
  private EditText mWrapperText;
  private EditText mVitolaText;
  private EditText mQuantityText;

  private Uri todoUri;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.todo_edit);

    mCategory = (Spinner) findViewById(R.id.category);
    mBrandText = (EditText) findViewById(R.id.todo_edit_brand);
    mTypeText = (EditText) findViewById(R.id.todo_edit_type);
    mWrapperText = (EditText) findViewById(R.id.todo_edit_wrapper);
    mVitolaText = (EditText) findViewById(R.id.todo_edit_vitola);
    mQuantityText = (EditText) findViewById(R.id.todo_edit_quantity);
    Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

    Bundle extras = getIntent().getExtras();

    // Check from the saved Instance
    todoUri = (bundle == null) ? null : (Uri) bundle
        .getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);

    // Or passed from the other activity
    if (extras != null) {
      todoUri = extras
          .getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);

      fillData(todoUri);
    }

    confirmButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (TextUtils.isEmpty(mBrandText.getText().toString())) {
          makeToast();
        } else {
          setResult(RESULT_OK);
          finish();
        }
      }

    });
  }

  private void fillData(Uri uri) {
    String[] projection = { TodoTable.COLUMN_BRAND,
        TodoTable.COLUMN_TYPE, TodoTable.COLUMN_WRAPPER,
        TodoTable.COLUMN_VITOLA, TodoTable.COLUMN_QUANTITY, TodoTable.COLUMN_CATEGORY };
    Cursor cursor = getContentResolver().query(uri, projection, null, null,
        null);
    if (cursor != null) {
      cursor.moveToFirst();
      String category = cursor.getString(cursor
          .getColumnIndexOrThrow(TodoTable.COLUMN_CATEGORY));

      for (int i = 0; i < mCategory.getCount(); i++) {

        String s = (String) mCategory.getItemAtPosition(i);
        if (s.equalsIgnoreCase(category)) {
          mCategory.setSelection(i);
        }
      }

      mBrandText.setText(cursor.getString(cursor
          .getColumnIndexOrThrow(TodoTable.COLUMN_BRAND)));
      mTypeText.setText(cursor.getString(cursor
          .getColumnIndexOrThrow(TodoTable.COLUMN_TYPE)));
      mWrapperText.setText(cursor.getString(cursor
              .getColumnIndexOrThrow(TodoTable.COLUMN_WRAPPER)));
      mVitolaText.setText(cursor.getString(cursor
              .getColumnIndexOrThrow(TodoTable.COLUMN_VITOLA)));
      mQuantityText.setText(cursor.getString(cursor
              .getColumnIndexOrThrow(TodoTable.COLUMN_QUANTITY)));

      // Always close the cursor
      cursor.close();
    }
  }

  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    saveState();
    outState.putParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);
  }

  @Override
  protected void onPause() {
    super.onPause();
    saveState();
  }

  private void saveState() {
    String category = (String) mCategory.getSelectedItem();
    String brand = mBrandText.getText().toString();
    String type = mTypeText.getText().toString();
    String wrapper = mWrapperText.getText().toString();
    String vitola = mVitolaText.getText().toString();
    String quantity = mQuantityText.getText().toString();

    // Only save if either brand or type
    // is available

    if (brand.length() == 0 && type.length() == 0) {
      return;
    }

    ContentValues values = new ContentValues();
    values.put(TodoTable.COLUMN_CATEGORY, category);
    values.put(TodoTable.COLUMN_BRAND, brand);
    values.put(TodoTable.COLUMN_TYPE, type);
    values.put(TodoTable.COLUMN_WRAPPER, wrapper);
    values.put(TodoTable.COLUMN_VITOLA, vitola);
    values.put(TodoTable.COLUMN_QUANTITY, quantity);
    

    if (todoUri == null) {
      // New todo
      todoUri = getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
    } else {
      // Update todo
      getContentResolver().update(todoUri, values, null, null);
    }
  }

  private void makeToast() {
    Toast.makeText(TodoDetailActivity.this, "Please maintain a summary",
        Toast.LENGTH_LONG).show();
  }
} 

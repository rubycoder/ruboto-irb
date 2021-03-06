package org.ruboto.irb;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShortcutBuilder extends Activity implements OnItemClickListener {
    public static final String SCRIPT_ACTION   = "org.ruboto.intent.action.LAUNCH_SCRIPT";
    public static final String SCRIPT_CATEGORY = "android.intent.category.DEFAULT";
    public static final String SCRIPT_NAME     = "org.ruboto.extra.SCRIPT_NAME";

    public static final String TAG = "Ruboto-IRB";
    
    private ArrayAdapter<String> adapter;
    private List<String> scripts;
    
    public static Intent intentForScript(String name) {
      Intent sc_intent = new Intent();
      sc_intent.setAction(SCRIPT_ACTION);
      sc_intent.addCategory(SCRIPT_CATEGORY);
      sc_intent.putExtra(SCRIPT_NAME, name);
      return sc_intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IRBScript.setDir(IRB.scriptsDirName(this));
        
        ListView scriptsList = new ListView(this);
        setContentView(scriptsList);
        setTitle("Select a Ruboto Script");

        scripts = IRBScript.list();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scripts);
        scriptsList.setAdapter(adapter);
        scriptsList.setOnItemClickListener(this);

        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.No_scripts);
        scriptsList.setEmptyView(emptyView);
    }

    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
	Intent intent = new Intent();
	intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, 
	                ShortcutBuilder.intentForScript(scripts.get(pos)));
	intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, scripts.get(pos));
	intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, 
			Intent.ShortcutIconResource.fromContext(this, R.drawable.icon));
	
	setResult((intent != null) ? RESULT_OK : RESULT_CANCELED, intent);
	finish();
    }
}   

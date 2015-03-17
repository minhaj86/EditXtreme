package com.DonutAppLab.EditXtreme;

//package com.AndroidExplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;



public class Explorer extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root="/";
//	private TextView myPath;
	private Button saveFileButton;
	private EditText fileNameText;
	private String filePath="";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer);
//        myPath = (TextView)findViewById(R.id.path);
        saveFileButton=(Button)findViewById(R.id.savefile);
        fileNameText=(EditText)findViewById(R.id.filename);
        
        
		if(SharedData.getFileOperation()==SharedData.FileOpType.OPEN){
			fileNameText.setClickable(false);
			fileNameText.setEnabled(false);
		}
		else if(SharedData.getFileOperation()==SharedData.FileOpType.SAVE){
			fileNameText.setClickable(true);
			fileNameText.setEnabled(true);
		}

        
        
        
        saveFileButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				filePath=filePath+"/"+fileNameText.getText();
				SharedData.setFilePathName(filePath.toString() );
				SharedData.setFileName(fileNameText.getText().toString());
				finish();	
			}        	
        });
        
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
//    	myPath.setText("Location: " + dirPath);
    	
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	
    	
    	if(!dirPath.equals(root))
    	{

    		item.add(root);
    		path.add(root);
    		
    		item.add("../");
    		path.add(f.getParent());
            
    	}
    	
    	for(int i=0; i < files.length; i++)
    	{
    			File file = files[i];
    			path.add(file.getPath());
    			if(file.isDirectory())
    				item.add(file.getName() + "/");
    			else
    				item.add(file.getName());
    	}

    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.row, item);
    	setListAdapter(fileList);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
		
		File file = new File(path.get(position));
		
		filePath=file.getPath();//file.getParent();
		
		
		if(filePath.compareTo("/")==0){
			filePath="";
		}

		//Toast.makeText((Activity)this, file.getAbsolutePath(), 10).show();
		
		
		if (file.isDirectory())
		{
			SharedData.setLoadFilePathName(file.getAbsolutePath());
			
			SharedData.setFileDirectory(file.getAbsolutePath());
			
			fileNameText.setText("");
			if(file.canRead()){
//				Toast.makeText(this, "FILE PATH"+file.getPath(), Toast.LENGTH_SHORT);
				getDir(path.get(position));
			}
			else
			{
/*				new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("[" + file.getName() + "] folder can't be read! &&  "+file.getPath()+"")
				.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).show();
*/			}
		}
		else if(file.isFile()){
			fileNameText.setText(((TextView)v).getText());
//			SharedData.setFileName(((TextView)v).getText().toString());
		}
		else
		{
/*			new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("[" + file.getName() + "] &&  "+file.getPath()+"")
				.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).show();
*/		}
	}
}
package com.DonutAppLab.EditXtreme;

//import com.cookbook.activity_lifecycle.Explorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

import com.DonutAppLab.EditXtreme.SharedData.FileOpType;


public class EditXtreme extends Activity {
	
	public enum status{EDITOR,SAVE,LOAD};
	
	public static final int MENU_SAVE = Menu.FIRST+1;
	public static final int MENU_OPEN = Menu.FIRST+2;
	public static final int MENU_HELP = Menu.FIRST+3;
	public static final int MENU_REMOVE = Menu.FIRST+4;
	
	private status AppStat;
	
	private EditText maintext;
	private String mFilePath;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.AppStat=status.EDITOR;
        
        
        setContentView(R.layout.main);
        maintext=(EditText)findViewById(R.id.editText1);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(Menu.NONE, MENU_SAVE, Menu.NONE, "Save").setIcon(R.drawable.save);
		menu.add(Menu.NONE, MENU_OPEN, Menu.NONE, "Open").setIcon(R.drawable.open);
		menu.add(Menu.NONE, MENU_HELP, Menu.NONE, "Help").setIcon(R.drawable.help);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem menuitem) {
		// TODO Auto-generated method stub
		switch(menuitem.getItemId()){
		case MENU_SAVE:

			SharedData.setFileOperation(FileOpType.SAVE);
			AppStat=status.SAVE;
			/////////////////////////////////////////////////
			startExplorer();
			/////////////////////////////////////////////////
			//Toast.makeText(this, "ON MENU SAVE"+SharedData.getFilePathName(),Toast.LENGTH_SHORT).show();
			break;
		case MENU_OPEN:
			
			SharedData.setFileOperation(FileOpType.OPEN);
			AppStat=status.LOAD;
			
	        //Toast.makeText(this, "ON MENU LOAD"+SharedData.getFilePathName(),Toast.LENGTH_SHORT).show();
			startExplorer();
			break;
		case MENU_HELP:
			
			startHelp();
			break;
	}
		return super.onMenuItemSelected(featureId, menuitem);
	}
    private void startHelp() {
		Intent launchhelp=new Intent(this,Help.class);
		startActivity(launchhelp);
	}

	public void startExplorer(){
		Intent launchexplorer=new Intent(this,Explorer.class);
		startActivity(launchexplorer);
    	
    }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
		if(SharedData.getFileDirectory()==null||SharedData.getFileDirectory()==""||
				SharedData.getFileName()==null||SharedData.getFileName()==""){
			return;
		}
		
		mFilePath=SharedData.getFileDirectory()+"/"+SharedData.getFileName();
		
		
		Toast.makeText(this, 
/*		"FilePathName: "+SharedData.getFilePathName()+
		"  ||  LoadFilePathName:"+SharedData.getLoadFilePathName()*/
		"Directory: "+SharedData.getFileDirectory()+
		"  ||  FileName: "+SharedData.getFileName()+
		"  ||  FilePath:  "+mFilePath
		, Toast.LENGTH_LONG).show();
		
		
		
		
		switch(AppStat){
		case EDITOR:
			// do nothing
			break;
			
		case SAVE:
	        //Toast.makeText(this, "onSave"+SharedData.getFilePathName().replaceAll("/", "/"),Toast.LENGTH_LONG).show();
			OutputStream os;
			try {
				File saveFile=new File(mFilePath);
				boolean saveFileCreatedFlag=false;
//				if(saveFile.exists())
				saveFileCreatedFlag=saveFile.createNewFile();
					
				
//				if(saveFileCreatedFlag==true){
					
					
				  BufferedWriter output = new BufferedWriter(new FileWriter(saveFile));
				  output.write(maintext.getText().toString().replaceAll("\n", "\r\n"));
				  
				  if(maintext.getText().toString().contains("\r")==true)
				  {
						Toast.makeText(this, "it is slash r",Toast.LENGTH_SHORT).show();
					  
				  }
				  if(maintext.getText().toString().contains("\n")==true)
				  {
						Toast.makeText(this, "it is slash n",Toast.LENGTH_SHORT).show();
					  
				  }
				  
				  
				  output.close();
//				}
				
//				else{
				//	Toast.makeText(this, "File Creation Failed",Toast.LENGTH_SHORT).show();
					
//				}
/*				os =  ().;// openFileOutput(SharedData.getFilePathName().replaceAll("/", "/"),0);
				OutputStreamWriter or=new OutputStreamWriter(os);//
				or.write(maintext.getText().toString());
				or.close();
*/			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
			} 
			catch(Exception e){
				e.printStackTrace();
				SharedData.Excp=e.toString();
				
				maintext.setText(SharedData.Excp);
				Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
				
				
			}

			AppStat=status.EDITOR;
	        break;
			
		case LOAD:
//			SharedData.getFilePathName().replaceAll("/", "//");
	        //Toast.makeText(this, "onLoad"+SharedData.getLoadFilePathName(),Toast.LENGTH_SHORT).show();
	        File file = new File(mFilePath);
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        DataInputStream dis = null;
			StringBuffer buf=new StringBuffer();

	        try {
	          fis = new FileInputStream(file);

	          // Here BufferedInputStream is added for fast reading.
	          bis = new BufferedInputStream(fis);
	          dis = new DataInputStream(bis);

	          // dis.available() returns 0 if the file does not have more lines.
	          while (dis.available() != 0) {

	          // this statement reads the line from the file and print it to
	            // the console.
	            
	            buf.append(dis.readLine());
	            buf.append("\n");
	          }

	          
	          maintext.setText(buf.toString().replaceAll("\r", "\r\n"));
	          
	          
	          // dispose all the resources after using them.
	          fis.close();
	          bis.close();
	          dis.close();

	        } catch (FileNotFoundException e) {
	          e.printStackTrace();
				Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
	          
	        } catch (IOException e) {
	          e.printStackTrace();
				Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
	        }			
	        
	        break;
			
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
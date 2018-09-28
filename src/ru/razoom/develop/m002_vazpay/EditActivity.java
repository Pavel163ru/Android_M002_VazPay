package ru.razoom.develop.m002_vazpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {
	
	EditText etValue;
	TextView tvEdit;
	
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_value);
		
		etValue = (EditText) findViewById(R.id.etValue);
		tvEdit = (TextView) findViewById(R.id.tvEdit);
		
		Intent intent = getIntent();
		String text = intent.getStringExtra("text");
		
		tvEdit.setText(text);
		
	}
	
	public void onClickStart(View viewClick){
		
		try{
			//Преобразуем из String в float
			float value = Float.parseFloat(etValue.getText().toString());
			
			Intent intent = new Intent();
			intent.putExtra("value",value);
			setResult(RESULT_OK, intent);
			
		}catch(Exception e){
			Log.d("myLog", "error = " + e.toString());
			Toast.makeText(this, "Отмена редактирования", Toast.LENGTH_SHORT).show();
		}finally{
			finish();
		}
		
	}

}

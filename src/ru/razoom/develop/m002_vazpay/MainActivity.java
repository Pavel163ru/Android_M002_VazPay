package ru.razoom.develop.m002_vazpay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//�������� TextViews 
	TextView tvTr;
	TextView tvFt;
	TextView tvPc;
	TextView tvPv;
	TextView tvPp;
	TextView tvRp;
	
	TextView tvZp;	
	TextView tvTarif;
	TextView tvNalog;
	TextView tvNach;
	
	
	float valueTr;//�������� ������
	float valueFt;//���� �������
	float valuePc;//������ �������
	float valuePv;//������ �� ���������
	float valuePp;//������ �� ����������
	float valueRp;//������� �� �������
	
	
	float valTarif;//�������� �� ������
	float valNalog;//���������� �����
	float valNach;//�������� � ������ ������
	
	float valueZp;//�������� � ������� ������ � �������� �� �������
	
	final int POSITION_TR = 1;
	final int POSITION_FT = 2;
	final int POSITION_PC = 3;
	final int POSITION_PV = 4;
	final int POSITION_PP = 5;
	final int POSITION_RP = 6;
	
	SharedPreferences spMemory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//������� �������� View �� Id
		tvTr = (TextView) findViewById(R.id.tvTr);
		tvFt = (TextView) findViewById(R.id.tvFt);
		tvPc = (TextView) findViewById(R.id.tvPc);
		tvPv = (TextView) findViewById(R.id.tvPv);
		tvPp = (TextView) findViewById(R.id.tvPp);
		tvRp = (TextView) findViewById(R.id.tvRp);
		
		tvZp = (TextView) findViewById(R.id.tvZp);
		
		tvTarif = (TextView) findViewById(R.id.tvTarif);
		tvNalog = (TextView) findViewById(R.id.tvNalog);
		tvNach = (TextView) findViewById(R.id.tvNach);
		
		spLoad();
		payCalculation();
		updateView();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()){
		case R.id.save:
			spSave();
			break;
		case R.id.load:
			spLoad();
			payCalculation();
			updateView();
			break;
		case R.id.exit:
			finish();
			break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickStart(View viewClick){
		//������� ����������� ����� Click
		Toast.makeText(this, "��������������", Toast.LENGTH_SHORT).show();
		
		//���������� �� ����� ������� ����� ������������
		switch (viewClick.getId()){
		case R.id.tvTr:
			startEditor(POSITION_TR, "������� �������� ������");
			break;
		case R.id.tvFt:
			startEditor(POSITION_FT, "������� ���� �������� �������");
			break;
		case R.id.tvPc:
			startEditor(POSITION_PC, "������� ������ ������� ������");
			break;
		case R.id.tvPv:
			startEditor(POSITION_PV, "������� ������ ������ �� ���������");
			break;
		case R.id.tvPp:
			startEditor(POSITION_PP, "������� ������ ������ �� ����.����������");
			break;
		case R.id.tvRp:
			startEditor(POSITION_RP, "������� ������� �� �������");
			break;
		
		default:
			break;	
		}

	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    //�������� ����������, ���� ������ ���������� ������
		if (resultCode == RESULT_CANCELED){
	    	Log.d("myLog", "Result canceled");
	    	return;
	    }
	    
	    float valueEdit = data.getFloatExtra("value", 0);

	    switch (requestCode){
	    case POSITION_TR:	    	
	    	valueTr = valueEdit;
	    	break;
	    case POSITION_FT:	    	
	    	valueFt = valueEdit;
	    	break;
	    case POSITION_PC:	    	
	    	valuePc = valueEdit;
	    	break;
	    case POSITION_PV:	    	
	    	valuePv = valueEdit;
	    	break;
	    case POSITION_PP:	    	
	    	valuePp = valueEdit;
	    	break;
	    case POSITION_RP:	    	
	    	valueRp = valueEdit;
	    	break;
	    	
	    default:
	    	break;
	    }
	    
	    payCalculation();
	    updateView();
	    
	}
	
	//����� ����������� �������� ��� ��������������
	void startEditor(int position, String text){
		Intent intent = new Intent(this,EditActivity.class);
		//�������� ����� ������ �������� ��� ���������
		intent.putExtra("text",text);
		//��������� ��������, �������� ����� ��� �������� ����������
		startActivityForResult(intent, position);
		
	}
	
	//������ �������� � �������
	void payCalculation(){
		valTarif = valueTr*valueFt;
		float valPrem = valTarif*valuePv/100+valTarif*valuePc/100+valTarif*valuePp/100;
		valNach = valTarif+valPrem;
		valNalog = valNach*13/100;
		valueZp = valNach - valNalog - valueRp;		
	}
	
	//���������� ������
	void updateView (){
		tvTr.setText("�������� ������: "+valueTr+"���.");
		tvFt.setText("���� �������: "+valueFt+"�.");
		tvPc.setText("�������: "+valuePc+"%");
		tvPv.setText("���������: "+valuePv+"%");
		tvPp.setText("����.����������: "+valuePp+"%");
		tvRp.setText("�������: "+valueRp+"���.");
		
		
		tvTarif.setText("�� ������: "+valTarif+"���.");
		tvNach.setText("����������: "+valNach+"���.");
		tvNalog.setText("�����: "+valNalog+"���.");
		
		tvZp.setText("��������: "+valueZp+"���.");
		
	}
	
	//���������� ������ � �������������� Preferences
	void spSave(){
		spMemory = getPreferences(MODE_PRIVATE);
		Editor ed = spMemory.edit();
		ed.putFloat("valueTr", valueTr);
		ed.putFloat("valueFt", valueFt);
		ed.putFloat("valuePc", valuePc);
		ed.putFloat("valuePv", valuePv);
		ed.putFloat("valuePp", valuePp);
		ed.putFloat("valueRp", valueRp);
		ed.commit();
		Toast.makeText(this, "���������", Toast.LENGTH_SHORT).show();
	}
	
	//�������� ������
	void spLoad(){
		spMemory = getPreferences(MODE_PRIVATE);
		valueTr = spMemory.getFloat("valueTr", 0);		
		valueFt = spMemory.getFloat("valueFt", 0);
		valuePc = spMemory.getFloat("valuePc", 0);
		valuePv = spMemory.getFloat("valuePv", 0);
		valuePp = spMemory.getFloat("valuePp", 0);
		valueRp = spMemory.getFloat("valueRp", 0);
		Toast.makeText(this, "���������", Toast.LENGTH_SHORT).show();
		
	}

}

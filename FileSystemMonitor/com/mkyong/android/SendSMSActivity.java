package com.mkyong.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SendSMSActivity extends Activity {

	Button buttonSend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		buttonSend = (Button) findViewById(R.id.buttonSend);

		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					
					 Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				     sendIntent.putExtra("sms_body", "default content"); 
				     sendIntent.setType("vnd.android-dir/mms-sms");
				     startActivity(sendIntent);
				        
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS faild, please try again later!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});

	}
}
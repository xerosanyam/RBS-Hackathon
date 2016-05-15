package com.example.sanyam.rbs;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView=null;
    ArrayList<InfoInt> allInfoInt= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView=(TextView)findViewById(R.id.call_log);
        postMyDetails(getApplicationContext());
    }
    private void postMyDetails(Context context){
        StringBuffer sb= new StringBuffer();
        if(allInfoInt!=null){
            allInfoInt.clear();
        }else{
            allInfoInt= new ArrayList<InfoInt>();
        }
        /* Query the CallLog Content Provider */
        Cursor managedCursor =context.getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE + " DESC");
        int number=managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type=managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date=managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration=managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log:");
        while (managedCursor.moveToNext()) {
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strCallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strCallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }

            // Retrieve a reference to an instance of TelephonyManager
            String mcc="No",mnc="No";
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
            String cid = String.valueOf(cellLocation.getCid());
            String lac = String.valueOf(cellLocation.getLac());

            String operator = telephonyManager.getSimOperator(); // this returns the MCC+MNC
            if (TextUtils.isEmpty(operator) == false) {
                mcc = operator.substring(0, 3);
                mnc = (operator.substring(3));
            }

            InfoInt infoInt=new InfoInt("8951262709",phNum,callType,Integer.parseInt(callDuration),50,Integer.parseInt(mcc),Integer.parseInt(mnc),Integer.parseInt(cid),Integer.parseInt(lac),callDate);
            allInfoInt.add(infoInt);
            sb.append("\nPhone Number: " + phNum +
                    "\nCallType: " + callType +
                    "\nCall Date: " + callDate +
                    "\nCall Duration: " + callDuration);
            sb.append("\nMCC: " + String.valueOf(mcc) +
                    "\nMNC: " + String.valueOf(mnc) +
                    "\nGsm Cell Id: " + String.valueOf(cid) +
                    "\nGsm Location Area Code: " + String.valueOf(lac)+
                    "\n---------------------------------------------------------------------" );
        }
        managedCursor.close();
        textView.setText(sb);
    }
    public void sendData(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.221.225.163:6000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi service = retrofit.create(RestApi.class);

        // Prepare the Request
        Call<HttpBinResponse> call = service.setInfo("8951262709",allInfoInt);

        //Asynchronously execute Request
        call.enqueue(new Callback<HttpBinResponse>() {
            @Override
            public void onResponse(Call<HttpBinResponse> call, Response<HttpBinResponse> response) {
                System.out.println("Response Status Code: " + response.code());
                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccess()) {
                    // print response body if unsuccessful
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        // do nothing
                    }
                    return;
                }

                // if parsing the JSON body failed, `response.body()` returns null
                HttpBinResponse decodedResponse = response.body();
                if (decodedResponse == null) return;

                // at this point the JSON body has been successfully parsed
                System.out.println("Response (contains request infos):");
                System.out.println("- url:         " + decodedResponse.getUrl());
                System.out.println("- ip:          " + decodedResponse.getOrigin());
                System.out.println("- headers:     " + decodedResponse.getHeaders());
                System.out.println("- args:        " + decodedResponse.getArgs());
                System.out.println("- form params: " + decodedResponse.getForm());
                System.out.println("- json params: " + decodedResponse.getJson());
            }

            @Override
            public void onFailure(Call<HttpBinResponse> call, Throwable t) {
                System.out.println("On Failure");
                System.out.println(t.getMessage());
            }
        });
    }
}

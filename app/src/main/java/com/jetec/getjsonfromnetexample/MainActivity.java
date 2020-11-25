package com.jetec.getjsonfromnetexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private WindowManager windowManager;
    //private ProgressDialog dialog=null;

//
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //必须先设置LayoutManager
        RecyclerView recyclerView;
        MyAdapter myAdapter;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
//*********************************************************************************************************
        //鎖定螢幕方向為豎屏設定
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//**********************************************************************************************************
        //catchData();




//***********************************************************************************************************

        //testChineseIndex(); //測試中文索引
//123212313213
        //132123
//789798798
        //Try to github
        //Try Push To Github By Souretree
    }

    private void testChineseIndex() {
//        //橫屏設定
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        //豎屏設定
//          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        //預設設定
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        String catchData = "https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10";

        //https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10
        //"https://datacenter.taichung.gov.tw/swagger/yaml/387290000H";
        new Thread(()->{
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }
                JSONArray jsonArray= new JSONArray(String.valueOf(json));
                Log.d(TAG, "onCreate: "+jsonArray);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String data = jsonObject.getString("RptName");//"路口");
                    Log.d(TAG, "onCreate: RPT名稱"+data);
                }

            }catch (Exception x){
                Log.d(TAG, "onCreate: "+x);
            }

        }).start();
    }

    private void catchData(){
        String catchData = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-E7D4764E-1727-4CE0-93C7-B6CA2298EF6D&format=JSON&locationName=%E5%BD%B0%E5%8C%96%E7%B8%A3&sort=time&startTime=";

        //台中方包案資料
        //https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10
        //中央氣象局的砸碎!!
        //https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-E7D4764E-1727-4CE0-93C7-B6CA2298EF6D&format=JSON&locationName=%E5%BD%B0%E5%8C%96%E7%B8%A3&sort=time&startTime=

                //"https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10";
        // https://opendata.taichung.gov.tw/dataset/954b9a7e-59d8-4f68-962f-f6bd222c9e1c/resource/b07b249e-e2f5-4dd7-b2e4-4f1aaa65d5d9/view/f43b70f6-b9ef-437c-a8a6-cabc205dc982";
        ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
                ,"請稍候",true);
        //真煩的轉轉轉!!!!!!!!!!!!!!!!!!!
        new Thread(()->{
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();    //URL連接物件
                InputStream is = connection.getInputStream();                               //輸入串流
                BufferedReader in = new BufferedReader(new InputStreamReader(is));          //緩衝讀取器
                String line = in.readLine();                                                //一次讀一行
                StringBuffer json = new StringBuffer();                                     //字串緩衝器
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }


                try {
                    JSONObject object = (JSONObject) new JSONTokener(String.valueOf(json)).nextValue();
                    JSONObject records = object.getJSONObject("records");
                    JSONArray array1= records.getJSONArray("location");

                    for (int i =0;i<array1.length();i++) {
                        JSONObject locationArray = array1.getJSONObject(i);
                        String locationName = locationArray.getString("locationName");
                        Log.e("locationName", locationName);
                        String weatherElement = locationArray.getString("weatherElement");
                        Log.e("weatherElement", weatherElement);
                        JSONArray weatherElementArray = new JSONArray(String.valueOf(weatherElement));

                        for (int j = 0; j < weatherElementArray.length(); j++) {
                            JSONObject weatherElementObject = weatherElementArray.getJSONObject(j);
                            String elementName = weatherElementObject.getString("elementName");
                            Log.e("elementName", elementName);
                            String time = weatherElementObject.getString("time");
                            Log.e("time", time);
                            JSONArray timeArray = new JSONArray(String.valueOf(time));

                            for (int k = 0; k < timeArray.length(); k++) {
                                JSONObject timeObject = timeArray.getJSONObject(k);
                                String startTime = timeObject.getString("startTime");
                                Log.e("startTime", startTime);
                                String endTime = timeObject.getString("endTime");
                                Log.e("endTime", endTime);
                                String parameter = timeObject.getString("parameter");

                                JSONObject parameterObject = (JSONObject) new JSONTokener(String.valueOf(parameter)).nextValue();
                                String parameterName = parameterObject.getString("parameterName");
                                Log.e("parameterName", parameterName);
                                String parameterValue = parameterObject.getString("parameterValue");
                                Log.e("parameterValue", parameterValue);



                                //記得這也要新增近去HashMap中
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("locationName",locationName);
                                hashMap.put("elementName",elementName);
                                hashMap.put("startTime",startTime);
                                hashMap.put("endTime",endTime);
                                hashMap.put("parameterName",parameterName);
                                hashMap.put("parameterValue",parameterValue);
                                //*****************************
                                hashMap.put("weatherElement",weatherElement);
                                //**********************************
                                arrayList.add(hashMap);
                            }
                        }
                    }


//                        JSONObject StringToObject = (JSONObject) new JSONTokener(String.valueOf(weatherElement)).nextValue();
//                        String parameterName = StringToObject.getString("parameterName");
//                        String parameterValue = StringToObject.getString("parameterValue");
//                        Log.e("parameterName",parameterName);
//                        Log.e("parameterValue",parameterValue);




                        //JSONArray weatherElement= records.getJSONArray("weatherElement");




                    //Log.e("array1",array1.getJSONArray(0).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //JSONArray array = jsonObject_try2.getJSONArray("info");


                //JSONArray jsonArray = JSONArray.fromObject(name);



//                JSONArray jsonArray= new JSONArray(String.valueOf(json));                   //Json陣列物件
//                for (int i =0;i<jsonArray.length();i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);                     //Json物件的物件
//                    String RptNo = jsonObject.getString("RptNo");
//                    String RptName = jsonObject.getString("RptName");
//                    String StatCourseNo = jsonObject.getString("StatCourseNo");
//                    String StatCourseName = jsonObject.getString("StatCourseName");
//                    String DataDate = jsonObject.getString("DataDate");
//                    String PlaceNo = jsonObject.getString("PlaceNo");
//                    String PlaceName = jsonObject.getString("PlaceName");
//                    String PeriodNo = jsonObject.getString("PeriodNo");
//                    String PeriodName = jsonObject.getString("PeriodName");
//                    String Complex1 = jsonObject.getString("Complex1");
//                    String ComplexName = jsonObject.getString("ComplexName");
//                    String Complex2 = jsonObject.getString("Complex2");
//                    String Complex2Name = jsonObject.getString("Complex2Name");
//                    String Complex3 = jsonObject.getString("Complex3");
//                    String Complex3Name = jsonObject.getString("Complex3Name");
//                    String Complex4 = jsonObject.getString("Complex4");
//                    String Complex4Name = jsonObject.getString("Complex4Name");
//                    String Complex5 = jsonObject.getString("Complex5");
//                    String Complex5Name = jsonObject.getString("Complex5Name");
//                    String DeriveNo = jsonObject.getString("DeriveNo");
//                    String DeriveExplain = jsonObject.getString("DeriveExplain");
//                    String FValue = jsonObject.getString("FValue");
//                    String SValue = jsonObject.getString("SValue");
//                    String RptDeptNo = jsonObject.getString("RptDeptNo");
//                    String RptDeptName = jsonObject.getString("RptDeptName");
//                    String CreateTime = jsonObject.getString("CreateTime");
//                    String ModifyTime = jsonObject.getString("ModifyTime");
//
//
//                    //記得這也要新增近去HashMap中
//                    HashMap<String,String> hashMap = new HashMap<>();
//                    hashMap.put("PlaceName",PlaceName);
//                    hashMap.put("DataDate",DataDate);
//                    hashMap.put("Car",ComplexName);
//                    hashMap.put("Type",Complex2Name);
//                    hashMap.put("Price",Complex3Name);
//                    hashMap.put("StatCourseName",StatCourseName);
//                    //*****************************
//                    hashMap.put("CreateTime",CreateTime);
//                    //**********************************
//                    arrayList.add(hashMap);
//                }
                Log.d(TAG, "catchData: "+arrayList);

                runOnUiThread(()->{
                    dialog.dismiss();
                    RecyclerView recyclerView;
                    MyAdapter myAdapter;
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    myAdapter = new MyAdapter();
                    recyclerView.setAdapter(myAdapter);
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        //螢幕旋轉預設設定
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvPos,tvType,tvPrice,tvCar,tvDateTime,tvStatCourseName,tvCreateTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvPos = itemView.findViewById(R.id.textView_pos);
                tvType = itemView.findViewById(R.id.textView_type);
                tvPrice = itemView.findViewById(R.id.textView_price);
                tvCar = itemView.findViewById(R.id.textView_car);
                tvDateTime = itemView.findViewById(R.id.textView_time);
                tvStatCourseName = itemView.findViewById(R.id.textView_StatCourseName);
                tvCreateTime = itemView.findViewById(R.id.textView_CreateTime);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(view);
        }

        //垃圾中央氣象局
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvPos.setText(arrayList.get(position).get("PlaceName"));
            holder.tvType.setText("locationName："+arrayList.get(position).get("locationName"));
            holder.tvPrice.setText("elementName："+arrayList.get(position).get("elementName"));
            holder.tvCar.setText("startTime："+arrayList.get(position).get("startTime"));
            holder.tvDateTime.setText("endTime："+arrayList.get(position).get("endTime"));
            holder.tvStatCourseName.setText("parameterName:："+arrayList.get(position).get("parameterName"));
            holder.tvCreateTime.setText("parameterValue:："+arrayList.get(position).get("parameterValue"));
        }

        //垃圾中央氣象局
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            holder.tvPos.setText(arrayList.get(position).get("PlaceName"));
//            holder.tvType.setText("類型："+arrayList.get(position).get("Type"));
//            holder.tvPrice.setText("收費與否："+arrayList.get(position).get("Price"));
//            holder.tvCar.setText("停放種類："+arrayList.get(position).get("Car"));
//            holder.tvDateTime.setText("新增資料時間："+arrayList.get(position).get("DataDate"));
//            holder.tvStatCourseName.setText("宣導事項:："+arrayList.get(position).get("StatCourseName"));
//            holder.tvCreateTime.setText("創建時間:："+arrayList.get(position).get("CreateTime"));
//        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

//        if (windowManager != null) {
//            //mWindowManager.removeViewImmediate(mDialogText);
//            windowManager.removeViewImmediate( this.dialog);
//        }

//        runOnUiThread(()->{
//            dialog.dismiss();
//            RecyclerView recyclerView;
//            MyAdapter myAdapter;
//            recyclerView = findViewById(R.id.recyclerView);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//            myAdapter = new MyAdapter();
//            recyclerView.setAdapter(myAdapter);
//        });
        catchData();
    }


    protected void onPause() {
        super.onPause();

//        if(null != dialog && dialog.isShowing()){
//            Log.d("TAG", "onPause: dialog showing, dismiss it 關閉dialog");
//            dialog.dismiss();
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什麼都不用寫
            //12313
        }
        else {
            // 什麼都不用寫
        }
    }

}

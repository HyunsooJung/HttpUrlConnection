package com.hyunsu.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hyunsu.http.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String address = "https://dl.dropboxusercontent.com/s/460oyzrb1p59rlk/";
    String data ="data.json";
    String result= "";
    private Member member = new Member();
    private ArrayList<Member> members = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRead();
            }
        });

    }

    public void getRead(){
        Toast.makeText(this,"get json방식으로 호출",Toast.LENGTH_LONG).show();

        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(address+data);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET"); // 전송방식
                    connection.setDoOutput(true); //데이터를 쓸지 설정
                    connection.setDoInput(true); //데이터를 읽어올지 설정

                    InputStream inputStream = connection.getInputStream();
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));


                    while( (result = br.readLine()) != null ){
                        stringBuilder.append(result+'\n');
                    }

                    result = stringBuilder.toString();

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

        Log.d("jsontostring : ",""+result);

//        binding.resultJson.setText(result);
        jsonParsing(result);
    }

    public void jsonParsing(String json){
        try {
            String str;
            
/* 1번째 list로 만들어보기
            aData adata = new aData();
            adata.setData(json);

            String jsonAdata = (String) adata.getData();

            JSONObject jsonObject = new JSONObject(jsonAdata);
            ArrayList<Member> list = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject dataObject = jsonArray.getJSONObject(i);

                member.setName(dataObject.getString("name"));
                member.setRank(dataObject.getString("rank"));
                member.setTime(dataObject.getString("time"));

                list.add(member);
            }

            Log.d("json array : ",""+list.toString());
*/
            
//2번째 방법 dataclass에 담아서 string에 담아 출력 or string변수에 각각 지정해 text에 출력

            JSONObject jsonObject = new JSONObject(json);

            JSONArray dataArray = jsonObject.getJSONArray("data");

            for(int i=0; i<dataArray.length(); i++){
                JSONObject dataObject = dataArray.getJSONObject(i);
                Member data = new Member();
                data.setName(dataObject.getString("name"));
                data.setRank(dataObject.getString("rank"));
                data.setTime(dataObject.getString("time"));
                members.add(data);
//                str = data.getName() + "\n"+
//                        data.getRank() + "\n"+
//                        data.getTime() + "\n"+"\n";
//                binding.resultJson.append(str);

//                String name = dataObject.getString("name");
//                String rank = dataObject.getString("rank");
//                String time = dataObject.getString("time");
//
//                binding.resultJson.append("이름 : "+name+"  순위 : "+rank+"  시간 : "+time+"\n");
            }
            Log.d("members : ", ""+members.toString());
            Collections.sort(members, sortByRank);

            for(int i=0; i<members.size(); i++){
                str = members.get(i).getName() + "\n"+
                        members.get(i).getRank() + "\n"+
                        members.get(i).getTime() + "\n"+ "\n";
                binding.resultJson.append(str);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final static Comparator<Member> sortByRank = new Comparator<Member>() {
        @Override
        public int compare(Member member1, Member member2) {
            int mem1 = Integer.parseInt(member1.getRank());
            int mem2 = Integer.parseInt(member2.getRank());
            return Integer.compare(mem1, mem2);
        }
    };
}
package com.hyunsu.http;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hyunsu.http.databinding.RetrofitMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    RetrofitClient retrofitClient;
    RetrofitAPI retrofitAPI;

    private RetrofitMainBinding binding;
    List<AconData> aconDataList = new ArrayList<AconData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RetrofitMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofitClient = RetrofitClient.getInstance();
        retrofitAPI = RetrofitClient.getRetrofitApi();

        retrofitAPI.getData().enqueue(new Callback<RetrofitData>() {
            @Override
            public void onResponse(Call<RetrofitData> call, Response<RetrofitData> response) {
                binding.retrofitView.setText("");
                if( response.isSuccessful() ){
                    RetrofitData result = response.body();
                    Log.d("responsebbb1 : ",""+result.toString());
                    List<AconData> aconDatas = result.getData();
                    Log.d("responsebbb2 : ",""+aconDatas.toString());
                    String resultText = "";
                    for( AconData aconData : aconDatas ){
                        aconDataList.add(aconData);
                    }
                    Collections.sort(aconDataList, sortByRank);

                    for(AconData data : aconDataList){
                        resultText = data.getName()+"\n"+
                                data.getRank()+"\n"+
                                data.getTime()+"\n"+"\n";
                        binding.retrofitView.append(resultText);
                    }

                }
            }

            @Override
            public void onFailure(Call<RetrofitData> call, Throwable t) {

            }
        });

    }

    public final static Comparator<AconData> sortByRank = new Comparator<AconData>() {
        @Override
        public int compare(AconData aconData1, AconData aconData2) {
            int acon1 = Integer.parseInt(aconData1.getRank());
            int acon2 = Integer.parseInt(aconData2.getRank());
            return Integer.compare(acon1, acon2);
        }
    };

}

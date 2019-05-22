package com.papb.prima.jogingkuy.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.api.APIUtils;
import com.papb.prima.jogingkuy.api.BaseApiService;
import com.papb.prima.jogingkuy.model.BmiCalculator;
import com.papb.prima.jogingkuy.model.Event;
import com.papb.prima.jogingkuy.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tv_namaUser, tv_weight, tv_height, tV_bmi_received;

    FirebaseAuth auth;
    FirebaseDatabase database;

    private BaseApiService mApiService;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String uId = auth.getCurrentUser().getUid();

        tv_namaUser = rootView.findViewById(R.id.tV_namauser);
        tv_height = rootView.findViewById(R.id.tV_height_received);
        tv_weight = rootView.findViewById(R.id.tV_weight_received);
        tV_bmi_received = rootView.findViewById(R.id.tV_bmi_received);

        setProfile(uId);

        getBmi(getContext());

//        mApiService = APIUtils.getApiService();
//
//        Call<BmiCalculator> call = mApiService.getBmi(tv_height.getText().toString(),
//                tv_weight.getText().toString());
//        call.enqueue(new Callback<BmiCalculator>() {
//            @Override
//            public void onResponse(Call<BmiCalculator> call, Response<BmiCalculator> response) {
//                if(response.body().getBmi()!= null){
//                    tV_bmi_received.setText(String.valueOf(response.body().getBmi()));
//                    Log.d("keluar", String.valueOf(response.body().getBmi()));
//                }else{
//                    Log.d("null", String.valueOf(response.body().getBmi()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BmiCalculator> call, Throwable t) {
//
//            }
//        });
        return rootView;
    }

    void getBmi(Context context){
        //String stringUrl = "http://papblproject.co.nf/api/bmiCalculator.php?h="+tv_height.getText().toString()+"&w="+tv_weight.getText().toString();
        String stringUrl = "https://api.myjson.com/bins/tq4oe";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new AsyntaskActivity(tV_bmi_received).execute(stringUrl);
        }else{
            //Pasang toast
        }
    }

    void setProfile(String uId){
        DatabaseReference weight = database.getReference("USERS").child(uId).child("weight");

        weight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int weight = dataSnapshot.getValue(Integer.class);
                tv_weight.setText(String.valueOf(weight) + " kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference height = database.getReference("USERS").child(uId).child("height");

        height.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int height = dataSnapshot.getValue(Integer.class);
                tv_height.setText(String.valueOf(height) + " cm");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference username = database.getReference("USERS").child(uId).child("username");

        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                tv_namaUser.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getBmi(getActivity());

    }

}

class AsyntaskActivity extends AsyncTask<String, Void, String> {
    private static final String debug = "HttpExample";
    private TextView textView;

    public AsyntaskActivity(View v){
        this.textView = (TextView)v;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        }catch (IOException e){
            return "Unable to retrieve web page. URL may be invalid";
        }
    }

    private String downloadUrl(String myurl) throws IOException{
        InputStream is = null;
        int len = 500;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();
            Log.d(debug, "The respons is: "+response);
            is = conn.getInputStream();

            String contentAsString = readIt(is, len);
            return contentAsString;
        } finally {
            if(is != null){
                is.close();
            }
        }
    }

    @Override
    protected void onPostExecute(String res) {
        try{
            JSONObject json = new JSONObject(res);
            String bmi = json.getString("bmi");
            textView.setText(bmi.substring(0,6));

            Log.d("EEEE","SUKSEEEEES");

        }catch (JSONException e){
            Log.d("EEEE",e.getMessage().toString());
        }

    }

    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
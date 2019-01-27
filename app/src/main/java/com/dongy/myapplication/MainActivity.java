package com.dongy.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        //textView는 원래 스크롤 안되는데 되게하는 옵션 + activity_main의 android:scrollbars="vertical"와 같이 씀
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //event
                new EventListTask().execute();
            }
        });
    }

    public class EventListTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        //서버 통신 등 메소드 넣는 메서드
        @Override
        protected String doInBackground(String... params) {
            return loadConnecter("");
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }


        //화면작업 하는 메서드
        @Override
        protected void onPostExecute(String result) {
            try {
//                JSONArray temp = (JSONArray) new JSONObject(result).get("list");
//                for (int i = 0; i < temp.length(); i++) {
//                    JSONObject json = temp.getJSONObject(i);
//                    String isActive = json.getString("v_type");
//                    if ("1".equalsIgnoreCase(isActive)) {
////                        eventJsonArr.put(json);
//                    }
//                }
                Log.d("dongy",result);
                textView.setText(result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String loadConnecter(String paramUrl) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String  result = "";
        InputStream response = null;
        BufferedReader reader = null;
        try {
//            url = new URL("https://api.androidhive.info/contacts/" + paramUrl);
            url = new URL("http://web.seohan.com/food/" + paramUrl);
            Log.d("dongy", url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");

            response = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(response, "utf-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                result += (line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
            if (response != null) try {
                response.close();
            } catch (IOException ioe) {
            }
            if (reader != null) try {
                reader.close();
            } catch (IOException ioe) {
            }
        }
        return result;
    }
}

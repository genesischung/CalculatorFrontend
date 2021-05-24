package myzhong.com.ui.main;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalculatorTask extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();
        String url = urls[0];
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        String result = "";
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(String result) {
        //Log.e("========", result);
    }
}
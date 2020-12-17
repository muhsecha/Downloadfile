package com.example.downloadfile;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    Button btnDown;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new DownloadFileFromURL().execute("http://192.168.6.188/apiBajawa/export.php");

        new DownloadFileFromURL().execute("https://docs.google.com/spreadsheets/d/1YxeodOdnaiUERU-Z-oust95OHJZ7h5vC/edit#gid=2086184832");
//
//        btnDown = findViewById(R.id.btnDown);
//
//        btnDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AndroidNetworking.post("http://192.168.6.188/apiBajawa/export.php")
//                        .addBodyParameter("tanggalNota","16 November 2020")
//                        .addBodyParameter("statusOrder","selesai")
//                        .setTag("test")
//                        .setPriority(Priority.MEDIUM)
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
////                                    DownloadFileFromURL;
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Log.d("TAG", "onError: " + anError.getErrorDetail());
//                                Log.d("TAG", "onError: " + anError.getErrorBody());
//                                Log.d("TAG", "onError: " + anError.getErrorCode());
//                                Log.d("TAG", "onError: " + anError.getResponse());
//                            }
//                        });
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        class DownloadFileFromURL extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                System.out.println("Starting download");

                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading... Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            /**
             * Downloading file in background thread
             * */
            @Override
            protected String doInBackground(String... f_url) {
                int count;
                try {
                    String root = Environment.getExternalStorageDirectory().toString();

                    System.out.println("Downloading");
                    URL url = new URL(f_url[0]);

                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file

                    OutputStream output = new FileOutputStream(root+"/download rekap/api.xlsx");
                    byte data[] = new byte[1024];

                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;

                        // writing data to file
                        output.write(data, 0, count);

                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }



            /**
             * After completing background task
             * **/
            @Override
            protected void onPostExecute(String file_url) {
                System.out.println("Downloaded");

                pDialog.dismiss();
            }

        }


}
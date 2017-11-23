package com.example.guillemllados.uiiot;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillemllados on 23/11/17.
 */

public class AsyncUpdateDeviceDB extends AsyncTask<String,String,String> {

    private static final String IP= "192.168.1.190";//"10.0.2.2";
    private List<Atributs> atributs;
    private AsyncGetAtributesSingleDevice.onNewDataListener onNewDataListener;

    public AsyncUpdateDeviceDB(AsyncGetAtributesSingleDevice.onNewDataListener onNewDataListener) {

        this.onNewDataListener = onNewDataListener;
    }

    public interface onNewDataListener{
        public void onNewData(List<Atributs> llista);
    }

    @Override
    protected String doInBackground(String[] strings) {

        URL url;
        HttpURLConnection connection = null;
        OutputStream out = null;
        try {
            //Create connection
            String Surl = "http://"+ IP+"/php/updateDB.php/?deviceId="+strings[0];

            List<Atributs> rawDataToSend = Principal.items.get(strings[0]).getAtributs();

            JSONArray jsonArray = new JSONArray();

            for (Atributs atributs:rawDataToSend){

                JSONObject object = new JSONObject();

                object.put("Device_ID",strings[0]);
                object.put("Temperature",atributs.getAtrib1());
                object.put("Humidity",atributs.getAtrib2());
                object.put("Calendar",atributs.toString());

                jsonArray.put(object);

            }

            JSONObject dataToSendJson = new JSONObject();
            dataToSendJson.put("Students", jsonArray);

            String dataToSend = dataToSendJson.toString();

            url = new URL(Surl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");

            out = new BufferedOutputStream(connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));

            writer.write(dataToSend);

            writer.flush();

            writer.close();

            out.close();

            connection.connect();



            return strings[0];

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if(connection != null) {
                connection.disconnect();
            }

        }
        return  "-1";

    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(!s.equals("-1")){
            List<Atributs> llista = Principal.items.get(s).getAtributs();

            llista.addAll(atributs);

            this.onNewDataListener.onNewData(llista);
        }

    }

}

package com.karacraft.ribsncuts.helper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


public class AsyncHttpConnectionTask extends AsyncTask<String /** Parameters In */,Void /** Current Progress */ ,Void /** Result Out */>
{

    JSONObject postData;                //Post Parameters Sent by Calling Activity
    RequestType requestType;            //Request REST API
    RequestMode requestMode;            //Request Mode : Post or Get
    AsyncHCTCallback hctCallback;       //Callback function in calling activity
    String token;                       //Laravel Passport API Token

    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    /** The Interface */
    public interface AsyncHCTCallback
    {
        void OnAsyncTaskCompleted(Boolean success, RequestMode requestMode,RequestType requestType,String result);
    }

    /**
     * Initialize AsyncHttpConnectionTask for Getting Data
     * @param requestType Defined in Request Type
     * @param requestMode Post or Get
     * @param hctCallback Callback function in Calling Activity
     */
    public AsyncHttpConnectionTask(RequestMode requestMode,RequestType requestType,  AsyncHCTCallback hctCallback)
    {
        this.requestType = requestType;
        this.requestMode = requestMode;
        this.hctCallback = hctCallback;
    }

    /**
     * Initialize AsyncHttpConnectionTask for Posting Data
     * @param postData  in case of Post, The Data to be send to Server
     * @param requestType Definde in Request Type
     * @param requestMode Post or Get
     * @param hctCallback Callback function in Calling Activity
     */
    public AsyncHttpConnectionTask(RequestMode requestMode,RequestType requestType, AsyncHCTCallback hctCallback,Map<String,String> postData)
    {
        if (postData != null)
        {
            this.postData = new JSONObject(postData);
            this.requestType = requestType;
            this.requestMode = requestMode;
            this.hctCallback = hctCallback;
        }
    }

    public AsyncHttpConnectionTask(RequestMode requestMode,RequestType requestType, AsyncHCTCallback hctCallback,Map<String,String> postData, String token)
    {
        if (postData != null)
        {
            this.postData = new JSONObject(postData);
            this.requestType = requestType;
            this.requestMode = requestMode;
            this.hctCallback = hctCallback;
            this.token = token;
        }
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params)
    {

        String result = "";  //Data From Server
        Boolean operationSuccessful = false;


        try
        {
            //--------------------- COMMON STEPS -----------------------------//
            /** Get the Url we passed in string */
            URL url = new URL(params[0]);

            /** Create the Url Connection*/
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //--------------------- UNCOMMON STEPS -----------------------------//
            /** Check Request Type and Act Accordingly */

            switch (requestType)
            {
                case GET_PRODUCTS:
                case GET_UPDATES:
                    {
                        urlConnection.setRequestMethod(REQUEST_METHOD_GET);
                        urlConnection.setReadTimeout(READ_TIMEOUT);
                        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                        urlConnection.connect();
                    }
                    break;
                case POST_LOGIN:
                case POST_REGISTER:
                    {
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);
                        urlConnection.setConnectTimeout(30000);
                        urlConnection.setReadTimeout(30000);
                        urlConnection.setRequestMethod(REQUEST_METHOD_POST);
                        urlConnection.setRequestProperty("Content-type", "application/json");
                        urlConnection.connect();


                        /** Send the Post Body*/
                        if (this.postData != null)
                        {
                            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                            writer.write(postData.toString());
                            writer.flush();
                        }
                    }
                    break;
                case POST_DETAILS:
                case POST_UPDATE_PROFILE:
                case POST_ORDER:
                    {
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);
                        urlConnection.setConnectTimeout(30000);
                        urlConnection.setReadTimeout(30000);
                        urlConnection.setRequestMethod(REQUEST_METHOD_POST);
                        urlConnection.setRequestProperty("Content-type", "application/json");
                        urlConnection.setRequestProperty("Authorization","Bearer " + token);
                        urlConnection.connect();


                        /** Send the Post Body*/
                        if (this.postData != null)
                        {
                            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                            writer.write(postData.toString());
                            writer.flush();
                        }
                    }
                    break;
                default:
                    break;
            }

            Log.d(TAG, "doInBackground: Request Type Complete....");

            //--------------------- COMMON STEPS -----------------------------//
            /** Status code of operation */
            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200)
            {
                /** From here you can convert the string to JSON with whatever JSON parser you like to use
                 *  After converting the string to JSON, I call my custom callback. You can follow this
                 *  process too, or you can implement the onPostExecute(Result) method
                 */
                /** Convert every result, since we are sending proper data from website */
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                result = convertInputStreamToString(inputStream);
                operationSuccessful = true;
            }
            else if (statusCode == 461)
            {
                result = "Given email address & password aren't authorized to access the system " + statusCode ;
                operationSuccessful = false;
            }
            else if (statusCode == 462)
            {
                result = "Unable to validate the given information.Please try later " + statusCode;
                operationSuccessful = false;
            }
            else
            {
                result = "Errors occurred at server side. Please try again later " + statusCode;
                operationSuccessful = false;
            }


            hctCallback.OnAsyncTaskCompleted(operationSuccessful,requestMode,requestType,result);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            hctCallback.OnAsyncTaskCompleted(false,requestMode,requestType,e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }

    /**
     * Converts the Inputstream and Returns String for further Process
     * @param inputStream
     * @return String data
     * @throws IOException
     */
    public String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

}

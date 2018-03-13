package org.kandesun.directionservice.connect;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 2018/01/21.
 */

public class HttpCommonConnection extends AsyncTask<String, Void, String>
{


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        HttpURLConnection con = null;
        URL url = null;
        String strUrl = params[0];

        try
        {
            // URLの作成
            url = new URL(strUrl);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(true);

            // 接続
            con.connect();

            // 本文の取得
            InputStream in = con.getInputStream();
            String strData = readInputStream(in);
            in.close();

            return strData;
        }
        catch (MalformedURLException e)
        {
            Log.e(e.getLocalizedMessage(), Log.getStackTraceString(e));
        }
        catch (IOException e)
        {
            Log.e(e.getLocalizedMessage(), Log.getStackTraceString(e));
        }

        return null;
    }

    protected String readInputStream(InputStream in) throws IOException, UnsupportedEncodingException
    {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null)
        {
            sb.append(st);
        }
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        //parser.parse(result);
    }
}


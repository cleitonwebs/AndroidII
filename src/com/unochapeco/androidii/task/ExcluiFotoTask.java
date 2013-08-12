package com.unochapeco.androidii.task;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.util.Log;

public class ExcluiFotoTask extends AsyncTask<Void, Void, Void> {
	
	private String nome;
	
	public ExcluiFotoTask(String nome){
		this.nome = nome;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
	   
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nome",nome));
		Log.v("nome", nome);
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://www.grupoinmove.com.br/webservice/delete/index.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        httpclient.execute(httppost);

	   }catch(Exception e){
	        Log.v("ExcluiFotoTask", "Falha na conex‹o "+e.toString());
	   }

		return null;
	}

}

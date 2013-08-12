package com.unochapeco.androidii.task;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unochapeco.androidii.Fotos;
import com.unochapeco.androidii.ListaFotosActivity;

import android.os.AsyncTask;
import android.util.Log;

public class ListarFotosTask extends AsyncTask<Void, Void, Void> {
	
	private List<Fotos> listaFotos;
	private ListaFotosActivity activity;
	
	public ListarFotosTask(ListaFotosActivity activity){
		this.activity = activity;
	}
	
	
	/*
	 * Faz requisição ao webservice trazendo a string json
	 */
	@Override
	protected Void doInBackground(Void... params) {
		String url = "http://www.grupoinmove.com.br/webservice/list";
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		
		
		try{
			HttpResponse resp = client.execute(get);
			
			InputStream is = resp.getEntity().getContent();
			InputStreamReader reader = new InputStreamReader(is);
			
			Type tipo = new TypeToken<List<Fotos>>(){}.getType();
			
			Gson gs = new Gson();
			
			this.listaFotos = gs.fromJson(reader, tipo);
			
			this.publishProgress();
			
		}catch (Exception e) {
			Log.v("ListarFotosTask", "Falha na conexão "+e.toString());
		}
		
		return null;
	}
	
	
	@Override
	protected void onProgressUpdate(Void... values) {
		this.activity.receberRetornoLista(listaFotos);
	}

}

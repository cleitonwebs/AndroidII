package com.unochapeco.androidii;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unochapeco.androidii.adapter.ListaFotosAdapter;
import com.unochapeco.androidii.task.ExcluiFotoTask;
import com.unochapeco.androidii.task.ListarFotosTask;

public class ListaFotosActivity extends ListActivity {
	
	private List<Fotos> listaFotos;
	private ListaFotosAdapter adapter;
	private ListarFotosTask listarTask;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_fotos);
		if (verificaConexao()) {
		this.listaFotos = new ArrayList<Fotos>();
        this.adapter = new ListaFotosAdapter(this, 
        		R.layout.layout_item_lista, listaFotos);
        
        this.setListAdapter(adapter);
        
        executaTask();
        
        lv = getListView();
        
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                return onLongListItemClick(v,pos,id);
            }
        });
        
		} else {
			Toast.makeText(getBaseContext(), "N‹o foi detectada uma conex‹o com a Internet.", Toast.LENGTH_LONG).show();
			Toast.makeText(getBaseContext(), "Por favor ative uma conex‹o de Internet",  Toast.LENGTH_LONG).show();
		}
 
	}
	
	public boolean verificaConexao() {
		boolean conectado;
		ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conectivtyManager.getActiveNetworkInfo() != null
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {
			conectado = true;
		} else {
			conectado = false;
		}
		return conectado;
	}
	
	protected boolean onLongListItemClick(final View v, int pos, long id) {
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Excluir")
        .setMessage("Deseja excluir a imagem?")
        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	TextView texto = (TextView) v.findViewById(R.id.textNameImage);
            	String str = texto.getText().toString();
            	
            	new ExcluiFotoTask(str).execute();

            	executaTask();
            	
            	Toast.makeText(getApplicationContext(),
						"Imagem Excluida", Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("N‹o", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) { 
            }
        })
        .show();
	
	    return true;
	}
	
	public void executaTask(){
		this.listarTask = new ListarFotosTask(this);
        this.listarTask.execute();
	}

	
	public void receberRetornoLista(List<Fotos> fotos){
		this.listaFotos.clear();
		this.listaFotos.addAll(fotos);
		this.adapter.notifyDataSetChanged();
	}
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Fotos fotoSelecionada = this.listaFotos.get(position);
		
		Uri uriFoto = Uri.parse(fotoSelecionada.getImage());
		
		Intent intent = new Intent(this, VisualizarFotoActivity.class);
		intent.setData(uriFoto);
		
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_fotos, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent i = new Intent(this, EnviaImagemActivity.class);
		this.startActivityForResult(i, 0);
		
		return true;
	}

}

package com.unochapeco.androidii;

import com.unochapeco.androidii.task.CarregaFotoTask;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class VisualizarFotoActivity extends Activity {
	
	private ImageView imgFoto;
	private CarregaFotoTask carregaFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_foto);
		
		Intent intent = getIntent();
		Uri imagemFoto = intent.getData();
		
		imgFoto = (ImageView) findViewById(R.id.imgFoto);
		
		this.carregaFoto = new CarregaFotoTask( imagemFoto.toString(), imgFoto, 400, 500 );
		this.carregaFoto.execute();
		
	}

}

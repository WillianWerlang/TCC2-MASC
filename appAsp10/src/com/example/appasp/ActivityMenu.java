package com.example.appasp;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.service.ServiceFinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMenu extends Activity {
	ProgressDialog prgDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_three, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 //abrir outra tela
    public void mapas (View view){
    	startActivity(new Intent(getApplicationContext(), ActivityMapas.class));
    }
    public void MostraPsicaoAtual (View view){
    	Context contexto = getApplicationContext();
        String texto = "";
        int duracao = Toast.LENGTH_SHORT;
        
        String respLa,respLo;
        respLa="";
        respLo="";
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		Double lat,lon;
		try {
			lat = location.getLatitude ();
			lon = location.getLongitude ();
           
			respLa = String.valueOf(lat);
			respLo = String.valueOf(lon);   

		}catch (NullPointerException e){
    	   System.out.print("ERRRRRRRRRRO");
    	   e.printStackTrace();
    	   Log.i("Mapas","Erro");
		}
        
        texto = texto+" lat="+ respLa;
        texto = texto+" lon="+ respLo;

        Toast toast = Toast.makeText(contexto,texto,duracao);
        toast.show();
    }
   
    public void ListarRecursos (View view){
		 new RunListarRecursos().execute();	    
	}
    
    public void VisualizarRotas (View view){
		//new RunVisualizarRotas().execute();
    	new RunVisualizarRotas().execute();
    	//startActivity(new Intent(getApplicationContext(), ActivityMapasRotas.class));

	}
    
    public void VisualizarRotasRuas (View view){
    	new RunVisualizarRotasRuas().execute();
	}
	 
	private class RunListarRecursos extends AsyncTask <Void, Void, String> {
		String result="";
		@Override
		protected String doInBackground(Void... params) {
			try {
				ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
				Client client = Client.create();

			    WebResource webResource = client.resource("http://dissys-marcelot.rhcloud.com/rest/listarRecursos");

	            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	            String input;
	            
	            ArrayList<String> ss;
	            ss = new ArrayList<String>();
	            
	            String tipoUser,tipoDef,lat,lon;
	            Intent intent = getIntent();
				Bundle paramsi = intent.getExtras();  
				
				if(paramsi!=null){   
					tipoUser = paramsi.getString("tipoUser");
					//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
					tipoDef = paramsi.getString("tipoDef");
					//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();
					lat = paramsi.getString("lat");
					lon = paramsi.getString("lon");
					
					ss.add(tipoUser);
		            ss.add(tipoDef);
		            ss.add(lat);
		            ss.add(lon);
		            
		            String a="";
		    	    for (String s : ss) {
		    	        a += s + ";";
		    	    }
		    	    
		            input=a;        
		            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input.toString());
		            
		            if (response.getStatus() != 201) {
		                //throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		                result = "Falhou. HTTP error code : "+ response.getStatus()+"...";;
		            }
		            result = response.getEntity(String.class);
		            }
				}catch (Exception e) {
					result = "Falhou. "+e.getMessage()+"...";
				}
				return result;
			}
		
		protected void onPostExecute(String r) {
			//prgDialog.show();
			Log.d("Foiii", result);
			Log.d("veio isso..",r);
			if (r!=null){
				if(r.contains("Falhou")){
				//if(r.substring(0,6).equals("Falhou")){
					Log.d("Erro onPost. Resultou [",r+"]"+ "Erro ="+r+"...");
					Toast.makeText(getApplicationContext(), "Listar recursos não realizado:\n"+result+"\n", Toast.LENGTH_LONG).show();
				}else{
					Log.d("Listar recursos",r);
					Log.d("Listar recursos2",result);
					Toast.makeText(getApplicationContext(), "Listar recursos.", Toast.LENGTH_LONG).show();
					//abrir nova tela;
					Intent intent = getIntent();
					Bundle params = intent.getExtras();  
					
					if(params!=null){   
						String mostraTexto = params.getString("tipoUser");
						//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
						mostraTexto = params.getString("tipoDef");
						//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();		
						
						Intent intentAbrir = new Intent(getApplicationContext(), ActivityMapasRecursos.class);
		                Bundle paramsAbrir = new Bundle();
		                
		                paramsAbrir.putString("lat",params.getString("lat"));
		                paramsAbrir.putString("lon",params.getString("lon"));
		                paramsAbrir.putString("r", r);

		                intentAbrir.putExtras(paramsAbrir);
		                startActivity(intentAbrir);
						
						//startActivity(new Intent(getApplicationContext(), ActivityMapasRecursos.class));
					}
				}	
			}		
		}	
	}
	private class RunVisualizarRotas extends AsyncTask <Void, Void, String> {
		String result="";
		@Override
		protected String doInBackground(Void... params) {
			try {
				ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
				Client client = Client.create();

			    WebResource webResource = client.resource("http://dissys-marcelot.rhcloud.com/rest/listarRecursos");

	            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	            String input;
	            
	            ArrayList<String> ss;
	            ss = new ArrayList<String>();
	            
	            String tipoUser,tipoDef,lat,lon;
	            Intent intent = getIntent();
				Bundle paramsi = intent.getExtras();  
				
				if(paramsi!=null){   
					tipoUser = paramsi.getString("tipoUser");
					//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
					tipoDef = paramsi.getString("tipoDef");
					//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();
					lat = paramsi.getString("lat");
					lon = paramsi.getString("lon");
					
					ss.add(tipoUser);
		            ss.add(tipoDef);
		            ss.add(lat);
		            ss.add(lon);
		            
		            String a="";
		    	    for (String s : ss) {
		    	        a += s + ";";
		    	    }
		    	    
		            input=a;        
		            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input.toString());
		            
		            if (response.getStatus() != 201) {
		                //throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		                result = "Falhou. HTTP error code : "+ response.getStatus()+"...";;
		            }
		            result = response.getEntity(String.class);
		            }
				}catch (Exception e) {
					result = "Falhou. "+e.getMessage()+"...";
				}
				return result;
			}
		
		protected void onPostExecute(String r) {
			//prgDialog.show();
			Log.d("Foiii", result);
			Log.d("veio isso..",r);
			if (r!=null){
				if(r.contains("Falhou")){
				//if(r.substring(0,6).equals("Falhou")){
					Log.d("Erro onPost. Resultou [",r+"]"+ "Erro ="+r+"...");
					Toast.makeText(getApplicationContext(), "Listar recursos não realizado:\n"+result+"\n", Toast.LENGTH_LONG).show();
				}else{
					Log.d("Listar recursos",r);
					Log.d("Listar recursos2",result);
					Toast.makeText(getApplicationContext(), "Listar recursos.", Toast.LENGTH_LONG).show();
					//abrir nova tela;
					Intent intent = getIntent();
					Bundle params = intent.getExtras();  
					
					if(params!=null){   
						String mostraTexto = params.getString("tipoUser");
						//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
						mostraTexto = params.getString("tipoDef");
						//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();		
						
						Intent intentAbrir = new Intent(getApplicationContext(), ActivityMapasRotas.class);
		                Bundle paramsAbrir = new Bundle();
		                
		                paramsAbrir.putString("lat",params.getString("lat"));
		                paramsAbrir.putString("lon",params.getString("lon"));
		                paramsAbrir.putString("r", r);

		                intentAbrir.putExtras(paramsAbrir);
		                startActivity(intentAbrir);
						
						//startActivity(new Intent(getApplicationContext(), ActivityMapasRecursos.class));
					}
				}	
			}		
		}
	}
	public void getPosition (View view){
		// Get the location manager
		Context contexto = getApplicationContext();
        String texto = "";
        int duracao = Toast.LENGTH_SHORT;
        
        
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		
		Double lat,lon;
		try {
			lat = location.getLatitude ();
			lon = location.getLongitude ();
       
	       //return String.valueOf(lat)+","+String.valueOf(lon);
	       String respLa,respLo,respTemp;
	       respLa = String.valueOf(lat);
	       respLo = String.valueOf(lon);
	       texto = texto+" lat="+ respLa;
	       texto = texto+" lon="+ respLo; 
	       Toast toast = Toast.makeText(contexto,texto,duracao);
	       toast.show();
	       
		}catch (NullPointerException e){
    	   System.out.print("ERRRRRRRRRRO");
    	   e.printStackTrace();
		}
	}
	private class RunVisualizarRotasRuas extends AsyncTask <Void, Void, String> {
		String result="";
		@Override
		protected String doInBackground(Void... params) {
			try {
				ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
				Client client = Client.create();

			    WebResource webResource = client.resource("http://dissys-marcelot.rhcloud.com/rest/listarRecursos");

	            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	            String input;
	            
	            ArrayList<String> ss;
	            ss = new ArrayList<String>();
	            
	            String tipoUser,tipoDef,lat,lon;
	            Intent intent = getIntent();
				Bundle paramsi = intent.getExtras();  
				
				if(paramsi!=null){   
					tipoUser = paramsi.getString("tipoUser");
					//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
					tipoDef = paramsi.getString("tipoDef");
					//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();
					lat = paramsi.getString("lat");
					lon = paramsi.getString("lon");
					
					ss.add(tipoUser);
		            ss.add(tipoDef);
		            ss.add(lat);
		            ss.add(lon);
		            
		            String a="";
		    	    for (String s : ss) {
		    	        a += s + ";";
		    	    }
		    	    
		            input=a;        
		            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input.toString());
		            
		            if (response.getStatus() != 201) {
		                //throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		                result = "Falhou. HTTP error code : "+ response.getStatus()+"...";;
		            }
		            result = response.getEntity(String.class);
		            }
				}catch (Exception e) {
					result = "Falhou. "+e.getMessage()+"...";
				}
				return result;
			}
		
		protected void onPostExecute(String r) {
			//prgDialog.show();
			Log.d("Foiii", result);
			Log.d("veio isso..",r);
			if (r!=null){
				if(r.contains("Falhou")){
				//if(r.substring(0,6).equals("Falhou")){
					Log.d("Erro onPost. Resultou [",r+"]"+ "Erro ="+r+"...");
					Toast.makeText(getApplicationContext(), "Listar recursos não realizado:\n"+result+"\n", Toast.LENGTH_LONG).show();
				}else{
					Log.d("Listar recursos",r);
					Log.d("Listar recursos2",result);
					Toast.makeText(getApplicationContext(), "Listar recursos.", Toast.LENGTH_LONG).show();
					//abrir nova tela;
					Intent intent = getIntent();
					Bundle params = intent.getExtras();  
					
					if(params!=null){   
						String mostraTexto = params.getString("tipoUser");
						//Toast.makeText(getApplicationContext(), "veja tipoUser "+mostraTexto, Toast.LENGTH_LONG).show();
						mostraTexto = params.getString("tipoDef");
						//Toast.makeText(getApplicationContext(), "veja tipoDef "+mostraTexto, Toast.LENGTH_LONG).show();		
						
						Intent intentAbrir = new Intent(getApplicationContext(), ActivityMapasRotas.class);
		                Bundle paramsAbrir = new Bundle();
		                
		                paramsAbrir.putString("lat",params.getString("lat"));
		                paramsAbrir.putString("lon",params.getString("lon"));
		                paramsAbrir.putString("r", r);

		                intentAbrir.putExtras(paramsAbrir);
		                startActivity(intentAbrir);
						
						//startActivity(new Intent(getApplicationContext(), ActivityMapasRecursos.class));
					}
				}	
			}		
		}
	}
}

package com.example.appasp;
import java.util.ArrayList;


import org.osmdroid.util.GeoPoint;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.service.ServiceFinder;

import android.app.Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


public class MainActivity extends Activity {
	public SessionUser session;
	public GeoPoint pp;
	public Location location;
	public Location lastKnownLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	 /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
            return rootView;
        }
    }
    //abrir outra tela
    public void cadastro (View view){
    	startActivity(new Intent(getApplicationContext(), ActivityCadastro.class));
    }
    public void visitante (View view){
    	
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);	
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		// Or use LocationManager.GPS_PROVIDER
	    lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		Log.d("Certo","bora Trabalhar com lastKnownLocation"+lastKnownLocation);     

		Toast.makeText(
				getApplicationContext(),
				"Lat "+lastKnownLocation.getLatitude()+
				" Lon"+lastKnownLocation.getLongitude(), Toast.LENGTH_LONG).show();	
    	

    	
    	Intent intentAbrir = new Intent(getApplicationContext(), ActivityMapasRecursosRua.class);
        Bundle paramsAbrir = new Bundle();
        
        paramsAbrir.putString("lat",String.valueOf(lastKnownLocation.getLatitude()));
        paramsAbrir.putString("lon",String.valueOf(lastKnownLocation.getLongitude()));
        //paramsAbrir.putString("lat","-29.11");
        //paramsAbrir.putString("lon","-51.11");

        intentAbrir.putExtras(paramsAbrir);
        startActivity(intentAbrir);
    	
    }


    public void logar (View view){
//		 Context contexto = getApplicationContext();
//		 int duracao = Toast.LENGTH_SHORT;
//		 String texto = "";
//		 texto = "enviando";
//		 Toast toast = Toast.makeText(contexto,texto,duracao);
//		 toast.show();
    	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		try{
			location = locationManager.getLastKnownLocation(bestProvider);
	    }catch (NullPointerException e){
    	   System.out.print("ERRRRRRRRRRO");
    	   e.printStackTrace();
    	   Log.d("Erro na leitura do GPS", "Erro no GPS");
		}
        
		 new LongRunningGetIO().execute();	    
	}
	 
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
   	String result="";
		@Override
		protected String doInBackground(Void... params) {
			

            ArrayList<String> ss;
            ss = new ArrayList<String>();

            String email,senha;
            EditText temail,tsenha;
            
            temail = (EditText)findViewById(R.id.editTextEmailInicio);
            email = temail.getText().toString();
            
            tsenha = (EditText)findViewById(R.id.editTextSenhaInicio);
            senha = tsenha.getText().toString();
            
            ss.add(email);
            ss.add(senha);
            
    	    String a="";
    	    for (String s : ss) {
    	        a += s + ";";
    	        result=result+s;
    	    }
    	    
			
			try {
				ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
				Client client = Client.create();

			    WebResource webResource = client.resource("http://dissys-marcelot.rhcloud.com/rest/logar");

	            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	            String input;
	            
	    	    
	            input=a;        
	            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input.toString());
	            
	            if (response.getStatus() != 201) {
	                //throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	                result = "Falhou1. HTTP error code : "+ response.getStatus()+";";
	            }
	            result = response.getEntity(String.class);
	          
	          } catch (Throwable e) {
	        	  result = result+";"+e.getLocalizedMessage();
	        	  result = result+"; Falhou2. "+e.getMessage()+";";
	          }
			return result;
		}
	
		protected void onPostExecute(String r) {
			if (r!=null){

				if(result.contains("ok;")){
					Log.d("Bem Vindo;",result+r);
				
	//				String[] temp = result.split(";");
	//				for (int i = 0; i < temp.length; i++) {
	//					Toast.makeText(getApplicationContext(), temp[i], Toast.LENGTH_LONG).show();			
	//				}
					Toast.makeText(getApplicationContext(), "Login realizado.", Toast.LENGTH_LONG).show();	
					//guardar variaveis de sessão
					String[] veio;
					veio=r.split(";");
					int i=0;
					for(String s:veio){
						Log.d("valor ",s+"\n");
						Log.d("i=",i+"");
						i++;
					}
					//Usuario usuario;
					//usuario = new Usuario();
					//usuario.setNome(veio[0]);
					//usuario.setTipoDef(veio[veio.length-2]);
					//session = new SessionUser(usuario);
					//startActivity(new Intent(getApplicationContext(), ActivityMenu.class));
					
					
					Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
	                Bundle params = new Bundle();
	                
	                String resposta = veio[veio.length-2];
	                params.putString("tipoUser", resposta);
	                resposta = veio[veio.length-1];
	                params.putString("tipoDef", resposta);
	                //colocar 
	                
	                if(location!=null){
		        		params.putString("lat", String.valueOf(location.getLatitude()));
		        		params.putString("lon", String.valueOf(location.getLongitude()));
		    		
		                intent.putExtras(params);
		                startActivity(intent);
	                }else{
	                	Toast.makeText(getApplicationContext(), "GPS fora do ar.", Toast.LENGTH_LONG).show();	
	                	intent.putExtras(params);
	                	startActivity(intent);
	                }
					
				}else{
					Toast.makeText(getApplicationContext(), "Login inválido.", Toast.LENGTH_LONG).show();	
					Log.d("Erro onPost. Resultou [",result+"]"+ "Erro ="+r+";");
					//Toast.makeText(getApplicationContext(), "Login não realizado:\n"+result+"\n", Toast.LENGTH_LONG).show();
			
				}					
			}			
		}		
	} 
    
	public void getPosition (){
		// Get the location manager
		
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
	    location = locationManager.getLastKnownLocation(bestProvider);
		
		Double lat,lon;
		try {
			lat = location.getLatitude ();
			lon = location.getLongitude ();
       
	       //return String.valueOf(lat)+","+String.valueOf(lon);
	       String respLa,respLo,respTemp;
	       respLa = String.valueOf(lat);
	       respLo = String.valueOf(lon);
	       Log.d("333", respLa);
	       
	      
		}catch (NullPointerException e){
    	   System.out.print("ERRRRRRRRRRO");
    	   e.printStackTrace();
		}
		
	}
	LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
          // Called when a new location is found by the network location provider.
          //makeUseOfNewLocation(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
      };

}

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityCadastro extends Activity {

	private Spinner spnSelecionaTipo;
	private Spinner spnSelecionaDeficiencia;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_cadastro);
		//Desabilitar spinner
		spnSelecionaDeficiencia = (Spinner) findViewById(R.id.spnTipoDef);
		spnSelecionaDeficiencia.getSelectedView();
		spnSelecionaDeficiencia.setEnabled(false);
		
		//colocando evento no spinner
		spnSelecionaTipo = (Spinner) findViewById(R.id.spnTipoUser);
		spnSelecionaTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	// your code here
		    	//Context contexto = getApplicationContext();
		    	//int duracao = Toast.LENGTH_SHORT;
		    	//String texto = "";
		    	//texto = spnSelecionaTipo.getSelectedItem().toString();
		    	//Toast toast = Toast.makeText(contexto,texto,duracao);
		        //toast.show();
	    		if(spnSelecionaTipo.getSelectedItem().toString().equals("PcD")){
	    			spnSelecionaDeficiencia.setEnabled(true);
	    		}
		    	
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_two, menu);
		
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
//    public void selecionaDeficiencia (View view){
//    	try{
//    		//spnSelecionaTipo = (Spinner) findViewById(R.id.spinner1);
//    		//spnSelecionaDeficiencia = (Spinner) findViewById(R.id.spinner2);
//    		//if(spnSelecionaTipo.equals("PcD")){
//    			//spnSelecionaDeficiencia.setVisibility(1);
//    			//spnSelecionaDeficiencia.setVisibility(View.VISIBLE);
//    		//}	
//		}catch(Exception e){
//			Log.d("Erro","");	
//		}
//    }
	 public void enviarCadastro (View view){
//		 Context contexto = getApplicationContext();
//		 int duracao = Toast.LENGTH_SHORT;
//		 String texto = "";
//		 texto = "enviando";
//		 Toast toast = Toast.makeText(contexto,texto,duracao);
//		 toast.show();
		 new RunCadastro().execute();	    
	 }
	 
	 private class RunCadastro extends AsyncTask <Void, Void, String> {
    	String result="";
		@Override
		protected String doInBackground(Void... params) {
			
			ArrayList<String> ss;
            ss = new ArrayList<String>();
            
            String nome,email,senha,senharepete,tipoUser,tipoDef;
            EditText tnome,temail,tsenha,tsenharepete;
            
            tnome = (EditText)findViewById(R.id.edtNome);
            nome = tnome.getText().toString();
            
            temail = (EditText)findViewById(R.id.edtEmail);
            email = temail.getText().toString();
            
            tsenha = (EditText)findViewById(R.id.edtSenha);
            senha = tsenha.getText().toString();
            
            tsenharepete = (EditText)findViewById(R.id.edtRepeteSenha);
            senharepete = tsenharepete.getText().toString();
            
            
            if (!senha.equals(senharepete)){
            	Log.d("Erro na senha",result);
				//Toast.makeText(getApplicationContext(), "Confira a senha!\n", Toast.LENGTH_LONG).show();
            	result="Falhou. Erro na senha"+"...";
            	return null;
            }
            tipoUser=spnSelecionaTipo.getSelectedItem().toString();
            tipoDef=spnSelecionaDeficiencia.getSelectedItem().toString();
            
            ss.add(nome);
            ss.add(email);
            ss.add(senha);
            ss.add(tipoUser);
            ss.add(tipoDef);
            
            
    	    String a="";
    	    for (String s : ss) {
    	        a += s + ";";
    	    }
			
			try {
				ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
				Client client = Client.create();

			    WebResource webResource = client.resource("http://dissys-marcelot.rhcloud.com/rest/cadastro");

	            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	            String input;
	            
	            
	    	    
	            input=a;        
	            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input.toString());
	            
	            if (response.getStatus() != 201) {
	                //throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	                result = "Falhou. HTTP error code : "+ response.getStatus()+"...";
	            }else{
	            	 result = response.getEntity(String.class)+"000000";
	            }
	           
	          
	          } catch (Exception e) {
	        	  result = "Falhou. "+e.getMessage()+"...";
	          }
			return result;
		}
	
		protected void onPostExecute(String r) {
			if(result.substring(0,6).equals("Falhou")){
				Log.d("Erro onPost. Resultou [",result+"]"+ "Erro ="+r+"...");
				Toast.makeText(getApplicationContext(), "Cadastro não enviado:\n"+result+"\n", Toast.LENGTH_LONG).show();
			}else{
				if(result.contains("Email já cadastrado")){
					Log.d("Erro onPost. Resultou [",result+"]"+ "Email já cadastrado.");
					Toast.makeText(getApplicationContext(), "Email já cadastrado:\n"+result+"\n", Toast.LENGTH_LONG).show();
				}else{
					Log.d("Cadastro realizado",result+r);
					Toast.makeText(getApplicationContext(), "Cadastro realizado.", Toast.LENGTH_LONG).show();			
				}
			}	
		}
	}
}

package com.example.appasp;

import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.SimpleLocationOverlay;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
 
public class ActivityMapasRotas extends Activity implements LocationListener {
  
	public MyItemizedOverlay myItemizedOverlay = null;
	private SimpleLocationOverlay posicionActualOverlay;
	//private Location punto = null;
	private MapView mapView;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapas);
        mapView = (MapView) findViewById(R.id.mapview);
        
        //habilitar zoom
        mapView.setBuiltInZoomControls(true);
        //define tamanho do zoon inicial
        mapView.getController().setZoom(20);
        
        MyOverlay myo;
        myo =  new MyOverlay(getBaseContext(),this,mapView);
        
        mapView.getOverlays().add(myo);
        
        Boolean resp;
        resp = myo.rotas;
        Log.d("ROOTAS", resp+"");
        //l=mapView.getOverlays().add(myo);
        
        Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);
        
        //Drawable myCurrentLocationMarker = getResources().getDrawable(R.drawable.MembrosInferiores);
        Drawable myIcon = getResources().getDrawable(R.drawable.usermembrosinferiores);
        Drawable myIcon2;
        //int myIconWidth = marker.getIntrinsicWidth();
        //int myIconrHeight = marker.getIntrinsicHeight();
        //myIcon.setBounds(0, 2, 2, 0);
        
//        mapView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                	Log.d("location click", "Touch coordinates : " +
//                            String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
//                    
//                }
//                return true;
//            }
//        });
        
        
        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

        Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null){   
			String[] r;
			String[] l;
			
			//ponto corrente passado
			myItemizedOverlay = new MyItemizedOverlay(myIcon, resourceProxy);
	        mapView.getOverlays().add(myItemizedOverlay);
	        
			Double lat_p;
			Double lon_p;
			lat_p = Double.parseDouble(params.getString("lat"));
			lon_p = Double.parseDouble(params.getString("lon"));
			
			GeoPoint pontoAtual = new GeoPoint(lat_p,lon_p);
			myItemizedOverlay.addItem(pontoAtual, "Aqui1", "myPoint2");

	        mapView.getController().animateTo(pontoAtual);
	        mapView.getController().setCenter(pontoAtual);
	        
	        mapView.getOverlays().add(myItemizedOverlay);

			r = params.getString("r").split("\n");
			Double lat,lon;
			String icone;
			int id;
			
			String nome,nome2;
			GeoPoint puntoCap2;
			String PACKAGE_NAME = getApplicationContext().getPackageName();
			for (int i = 0; i < r.length-1; i++) {
				l=r[i].split(";");
				nome=l[0];
				icone = l[5].replace(" ", "").toLowerCase();
				id = getResources().getIdentifier(PACKAGE_NAME+":drawable/" + icone, null, null);
				Log.d("rrr icone", icone);
				Log.d("rrr icone id", id+"");
				
				String fnm = "vermelho"; //  this is image file name
				
				int imgId = getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
				System.out.println("IMG ID :: "+imgId);
				System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);
				
				myIcon2 = getResources().getDrawable(id);
				myItemizedOverlay = new MyItemizedOverlay(myIcon2, resourceProxy);
		        mapView.getOverlays().add(myItemizedOverlay);
		        
				nome2=l[2];
				lat=Double.parseDouble(l[3]);
				lon=Double.parseDouble(l[4]);
				Log.d("valor"+i, r[i]);
				puntoCap2 = new GeoPoint(lat,lon);
				myItemizedOverlay.addItem(puntoCap2, nome, nome2);
				//l = r[i].split(";");
				//for (int j = 0; j < l.length; j++) {
				//	Log.d("valor"+l, l[j]);
					//Toast.makeText(getApplicationContext(), "veja tipoUser "+r[i], Toast.LENGTH_LONG).show();
				//}
			}			
		}         
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		 if (location != null) {
		      //punto = location;
		      GeoPoint puntoCap = new GeoPoint(location);
		      this.posicionActualOverlay.setLocation(puntoCap);
		      mapView.getController().animateTo(puntoCap);
		      mapView.getController().setCenter(puntoCap);
		  }
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}    
}
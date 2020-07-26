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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
 
public class ActivityMapasRecursosRua extends Activity implements LocationListener {
  
	MyItemizedOverlay myItemizedOverlay = null;
	private SimpleLocationOverlay posicionActualOverlay;
	private MapView mapView;
	public Location location;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapas);
        mapView = (MapView) findViewById(R.id.mapview);
        
        //habilitar zoom
        mapView.setBuiltInZoomControls(true);
        //define tamanho do zoon inicial
        mapView.getController().setZoom(20);
        Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);
        Drawable myIcon = getResources().getDrawable(R.drawable.usermembrosinferiores);

        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

        myItemizedOverlay = new MyItemizedOverlay(myIcon, resourceProxy);
        mapView.getOverlays().add(myItemizedOverlay);
       
        
        Intent intent = getIntent();
		Bundle params = intent.getExtras();  
        
        Double lat_p;
		Double lon_p;
		lat_p = Double.parseDouble(params.getString("lat"));
		lon_p = Double.parseDouble(params.getString("lon"));
		
		GeoPoint pontoAtual = new GeoPoint(lat_p,lon_p);
		myItemizedOverlay.addItem(pontoAtual, "Aqui1", "myPoint2");

        mapView.getController().animateTo(pontoAtual);
        mapView.getController().setCenter(pontoAtual);
        
        mapView.getOverlays().add(myItemizedOverlay);
       
        MyOverlay myo;
        myo =  new MyOverlay(getBaseContext(),this,mapView);
        
        mapView.getOverlays().add(myo);
     
                
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub		
		
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
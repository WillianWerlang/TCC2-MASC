package com.example.appasp;

import android.os.Bundle;
import android.app.Activity;
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
 
public class ActivityMapas extends Activity implements LocationListener {
  
	MyItemizedOverlay myItemizedOverlay = null;
	MyItemizedOverlay myItemizedOverlay2 = null;
	private SimpleLocationOverlay posicionActualOverlay;
	//private Location punto = null;
	private MapView mapView;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapas);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
         
        Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);
        
        Drawable marker2=getResources().getDrawable(android.R.drawable.star_big_on);
        int marker2Width = marker2.getIntrinsicWidth();
        int marker2Height = marker2.getIntrinsicHeight();
        marker2.setBounds(0, marker2Height, marker2Width, 0);
         
        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
         
        myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        mapView.getOverlays().add(myItemizedOverlay);
        
        myItemizedOverlay2 = new MyItemizedOverlay(marker2, resourceProxy);
        mapView.getOverlays().add(myItemizedOverlay2);
        
        mapView.getController().setZoom(20);
        
        
//        GeoPoint myPoint1 = new GeoPoint(0*1000000, 0*1000000);
//        myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
//        GeoPoint myPoint2 = new GeoPoint(50*1000000, 50*1000000);
//        myItemizedOverlay.addItem(myPoint2, "myPoint2", "myPoint2");
        
//        GeoPoint myPoint1 = new GeoPoint(lat,lon);
//        myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
        
		
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		
        //ponto corrente
        GeoPoint puntoCap = new GeoPoint(location);
        myItemizedOverlay2.addItem(puntoCap, "myPoint2", "myPoint2");
        mapView.getController().animateTo(puntoCap);
        mapView.getController().setCenter(puntoCap);
        
        //ponto hospital -29.688640, -51.107094

        GeoPoint hosp = new GeoPoint(-29.688640, -51.107094);
        myItemizedOverlay2.addItem(hosp, "hospital", "Hospital Unimed");
        //mapView.getController().animateTo(puntoCap);
        //mapView.getController().setCenter(puntoCap);
        
        
        
//        OverlayItem olItem = new OverlayItem("Here", "SampleDescription", point);
//        Drawable newMarker = this.getResources().getDrawable(R.drawable.mymarker);
//        olItem.setMarker(newMarker);
//        mItems.add(olItem);
         
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
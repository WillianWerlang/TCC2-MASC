package com.example.appasp;

import android.app.Activity;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;




public class LocationServices extends Activity {
	
	double[] localization;

	public double[] getLocation() {
		return localization;
	}

	public void setLocation(double[] localization) {
		this.localization = localization;
	}
	
	public double[] discoverCurrentLocation(){
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		Double lat,lon;
		try {
			lat = location.getLatitude ();
			lon = location.getLongitude ();
       
	       String respLa,respLo,respTemp;
	       respLa = String.valueOf(lat);
	       respLo = String.valueOf(lon);
	       respTemp = "";
	       localization = new double[2];
	       localization[0]=lat;
	       localization[1]=lon;
	       Log.i("Mapas","Erro Lat=" +respLa +" Lon="+respLo +" Temp="+respTemp );

		}catch (NullPointerException e){
    	   System.out.print("ERRRRRRRRRRO");
    	   e.printStackTrace();
    	   Log.i("Mapas","Erro");
		}
		
		return localization;
	}
	
	

}

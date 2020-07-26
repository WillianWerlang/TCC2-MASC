package com.example.appasp;

import java.util.ArrayList;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

public class MyOverlay extends Overlay{
	
	public Drawable marker;
	public String latitude;
	public String longitude;
	public DialogHandler appdialog;
	public GeoPoint loc;
	public ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null; 
	Context ctx;
	Activity aa;
	MapView m;
	public boolean rotas;
	Road road;

	public MyOverlay(Context ctx, Activity aa, MapView m) {
		super(ctx);
		this.ctx=ctx;
		this.aa=aa;
		this.rotas=false;
		this.m=m;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void draw(Canvas arg0, MapView arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView) {
	    // Handle single-tap here, then return true.
		Projection proj = mapView.getProjection();
        loc = (GeoPoint) proj.fromPixels((int)event.getX(), (int)event.getY()); 
        this.latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
        this.longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
        ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
        OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double)loc.getLatitudeE6())/1000000), (((double)loc.getLongitudeE6())/1000000)));
        mapItem.setMarker(marker);
        overlayArray.add(mapItem);
        if(anotherItemizedIconOverlay==null){
            anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(ctx, overlayArray,null);
            mapView.getOverlays().add(anotherItemizedIconOverlay);
            mapView.invalidate();
        }else{
        	mapView.getOverlays().remove(anotherItemizedIconOverlay);
        	mapView.invalidate();
            anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(ctx, overlayArray,null);
            mapView.getOverlays().add(anotherItemizedIconOverlay);
        }
        
        //perguntar para o usuário
        
        //Toast.makeText(ctx, "Deseja solicitar rota acessível?", Toast.LENGTH_LONG).show();
        
        appdialog = new DialogHandler();
        appdialog.Confirm(this.aa, "Confirme:", "Deseja solicitar rota acessível?",
                "Cancelar", "Confirmar",aproc(mapView), bproc());
        
	    return true;
	}
	
	public Runnable aproc(final MapView mapView){
        return new Runnable() {
            public void run() {
                Log.d("Test", "This from A proc");
                Log.d("Test", "lat"+latitude);
                Log.d("Test", "lon"+longitude);
                //cxt.
                rotas=true;
                //m.addItem(loc, "Aqui1", "myPoint2"); 
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double)loc.getLatitudeE6())/1000000), (((double)loc.getLongitudeE6())/1000000)));
                mapItem.setMarker(marker);
                overlayArray.add(mapItem);
                if(anotherItemizedIconOverlay==null){
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(ctx, overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                    mapView.invalidate();
                }else{
                	mapView.getOverlays().remove(anotherItemizedIconOverlay);
                	mapView.invalidate();
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(ctx, overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                }
                PathOverlay myPath = new PathOverlay(Color.RED, ctx);
                myPath.addPoint(loc);
                //GeoPoint loc2 = new GeoPoint(-29.689157,-51.106644);
                //myPath.addPoint(loc2);
                //mapView.getOverlays().add(myPath);
                
                Intent intent = aa.getIntent();
        		Bundle params = intent.getExtras();  
                
                Double lat_p;
    			Double lon_p;
    			lat_p = Double.parseDouble(params.getString("lat"));
    			lon_p = Double.parseDouble(params.getString("lon"));
    			
    			GeoPoint pontoAtual = new GeoPoint(lat_p,lon_p);    			
    			myPath.addPoint(pontoAtual);
                mapView.getOverlays().add(myPath);
                
                
                ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        		GeoPoint startPoint = new GeoPoint(lat_p,lon_p);
                waypoints.add(startPoint);
                //GeoPoint endPoint = new GeoPoint(-29.6800269241333008,-51.11323547363281);
                GeoPoint endPoint = loc;
                waypoints.add(endPoint);
        		new UpdateRoadTask().execute(waypoints);
                  
            }
            
          };
    }
	private class UpdateRoadTask extends AsyncTask<Object, Void, Road> {

        protected Road doInBackground(Object... params) {
            @SuppressWarnings("unchecked")
            ArrayList<GeoPoint> w = (ArrayList<GeoPoint>)params[0];
            RoadManager roadManager = new OSRMRoadManager();


            return roadManager.getRoad(w);
        }
        @Override
        protected void onPostExecute(Road result) {
            road = result;
             // showing distance and duration of the road 
            Toast.makeText(aa, "Distância = "+road.mLength+" metros.", Toast.LENGTH_LONG).show();
            Toast.makeText(aa, "Duração = "+road.mDuration+" minutos.", Toast.LENGTH_LONG).show(); 

            if(road.mStatus != Road.STATUS_OK)
            Toast.makeText(aa, "Error when loading the road - status="+road.mStatus, Toast.LENGTH_SHORT).show();
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road,aa);

            m.getOverlays().add(roadOverlay);
            m.invalidate();
            //updateUIWithRoad(result);
            
            RoadManager roadManager = new MapQuestRoadManager("CyWGYDFJ6BVF9AjxjCmcqDQMw0mIFdCn");
            roadManager.addRequestOption("routeType=bicycle");
            Drawable nodeIcon = aa.getResources().getDrawable(R.drawable.marker_node);
            for (int i=0; i<road.mNodes.size(); i++){
                    RoadNode node = road.mNodes.get(i);
                    Marker nodeMarker = new Marker(m);
                    nodeMarker.setPosition(node.mLocation);
                    nodeMarker.setIcon(nodeIcon);
                    nodeMarker.setTitle("Step "+i);
                    m.getOverlays().add(nodeMarker);
            
            nodeMarker.setSnippet(node.mInstructions);
            nodeMarker.setSubDescription(Road.getLengthDurationText(node.mLength, node.mDuration));
            Drawable icon = aa.getResources().getDrawable(R.drawable.ic_launcher);
            nodeMarker.setImage(icon);
            }
            
         }
		 private void updateUIWithRoad(Road result) {
			// TODO Auto-generated method stub
			
		}
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
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
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


    public Runnable bproc(){
        return new Runnable() {
            public void run() {
                Log.d("Test", "This from B proc");
                Log.d("Test", "lat"+latitude);
                Log.d("Test", "lon"+longitude);
            }
          };
    }
	
    
}



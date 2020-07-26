package com.example.appasp;

import org.osmdroid.util.GeoPoint;


public interface MapEventsReceiver {

    /**
     * @param p the position where the event occurred.
     * @return true if the event has been "consumed" and should not be handled by other objects.
     */
    boolean singleTapConfirmedHelper(GeoPoint p);
   
    /**
     * @param p the position where the event occurred.
     * @return true if the event has been "consumed" and should not be handled by other objects.
     */
    boolean longPressHelper(GeoPoint p);    
}


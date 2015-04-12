package map.franklin.com.map;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;


public class MapsActivity extends FragmentActivity {

    private static final String LOG_TAG = MapsActivity.class.getSimpleName() ;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    static final LatLng COCHA = new LatLng(-17.383333,-66.166667);
    public static String[] resJason = new String[]{};
    public static String[][] matriz;
    public static int CTTE = 5;
    //private LocationManager manejador ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();


    }
//metodo onResume...ojo
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            Intent intent = this.getIntent();

           // manejador = (LocationManager)this.getSystemService(this.LOCATION_SERVICE);
           // Location localizacion = manejador.getLastKnownLocation();


            //Check if we were successful in obtaining the map.
            if (mMap != null) {
                GetResultTask task = new GetResultTask();
                task.execute();

            }
        }
    }


    private void setUpMap(String[][] matriz) {

        Marker[] markers = new Marker[resJason.length];
        LatLng[] latLngs = new LatLng[resJason.length];

        Marker locCocha = mMap.addMarker(new MarkerOptions().position(COCHA).title("Yo").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        for(int i = 0 ; i < resJason.length ; i++){

            latLngs[i] = new LatLng(Double.parseDouble(matriz[i][1]),Double.parseDouble(matriz[i][2]));
            markers[i] = mMap.addMarker(new MarkerOptions().position(latLngs[i]).title(matriz[i][3]+"\n"+matriz[i][4]).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(COCHA, 25));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);



    }




    class GetResultTask extends AsyncTask<Void, Void, String[]> {


        protected String[] doInBackground(Void... params) {

            String resultString = Utility.getJsonStringFromNetwork();
            try {

                return Utility.parseFixtureJson(resultString);//
            } catch (JSONException e) {
                Log.e(LOG_TAG,"Error Parsing"+e.getMessage(),e);
                e.printStackTrace();
                return new String[]{"NO DATA"};
            }

        }

        protected void onPostExecute(String[] strings) {
            resJason = strings;
            for (String result : resJason){
                Log.v(LOG_TAG,"oo"+result+"oo");
            }


            matriz = new String[resJason.length][CTTE];
            for (int i = 0 ; i < resJason.length; i++ ){
                String valores[] = resJason[i].split(":");
                for (int j = 0 ; j <valores.length; j++){
                    matriz[i][j] = valores[j];

                }

            }
            setUpMap(matriz);

        }
    }

}

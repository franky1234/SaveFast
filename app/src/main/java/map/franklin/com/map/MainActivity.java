package map.franklin.com.map;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName() ;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //como crear una lista que no cargue todos los datos que no entran en nuestra pantalla?.
            //atravez de un arrayAdapater que funciona como un appstore
            String[] lista = new String[]{
                    "Establecimiento 1N",
                    "Establecimiento 2N",
                    "Establecimiento 3N",
                    "Servicios"

            };

            ArrayList<String> result_equipos = new ArrayList<String>(Arrays.asList(lista));

            ArrayAdapter<String> list_optima = new ArrayAdapter<String>(getActivity(),

                    R.layout.result_view,    R.id.result_list,         result_equipos);
            ListView scrollList = (ListView)rootView.findViewById(R.id.lista_datos);
            scrollList.setAdapter(list_optima);

            scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.v(LOG_TAG, "soy" + position);
                    if (position == 0){
                        Intent intent = new Intent(getActivity(), MapsActivity.class);

                    startActivity(intent);
                        }
                }
            });

            return rootView;
        }
    }
}

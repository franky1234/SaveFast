package map.franklin.com.map;

/**
 * Created by Franklin on 4/10/2015.
 */
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Franklin on 3/15/2015.
 *  NOS creamos una clasre Utility para conectarnos a la API de football y ver sus datos
 *  en la red.
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getJsonStringFromNetwork() {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        try {
            final String IP_LOCAL = "http://savefastapi.esy.es/result/prueba.php";

            Uri builtUri = Uri.parse(IP_LOCAL).buildUpon().build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.v(LOG_TAG,"la url es:"+urlConnection.toString());
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[] parseFixtureJson(String fixtureJson) throws JSONException {
        Log.v(LOG_TAG,"<<<<<"+fixtureJson+">>>>>");
        JSONArray jsonArray = new JSONArray(fixtureJson); //agarramos en un array el array de JSons
        ArrayList<String> result = new ArrayList<>();
        final String ID = "id";
        final String LATITUDE = "latitude";
        final String LONGITUD = "longitude";
        final String NOMBRE = "nombre";
        final String TIPO = "tipo";

        for (int i = 0; i < jsonArray.length(); i++) {
            String id;
            String latitud;
            String longitud;
            String nombre;
            String tipo;
            JSONObject matchObject = jsonArray.getJSONObject(i);//aca tengo un Json
            id = matchObject.getString(ID);
            latitud = matchObject.getString(LATITUDE);
            longitud = matchObject.getString(LONGITUD);
            nombre = matchObject.getString(NOMBRE);
            tipo = matchObject.getString(TIPO);
            String resultString = new Formatter().format("%s: %s: %s: %s: %s", id,latitud,longitud,nombre,tipo).toString();
            //me devuelve el array de valores.
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }
}
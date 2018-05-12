package university.sathyabama.lorrymanagement;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends Activity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;

    List<Lorry> lorryList = new ArrayList<>();

    boolean isUpdating = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewLorries);

        readLorries();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the lorrylist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    Log.i("Lorries",object.getJSONArray("lorries").toString());
                    refreshLorryList(object.getJSONArray("lorries"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class LorryAdapter extends ArrayAdapter<Lorry> {

        //our lorry list
        List<Lorry> lorryList;


        //constructor to get the list
        public LorryAdapter(List<Lorry> lorryList) {
            super(MainActivity.this, R.layout.layout_lorry_list, lorryList);
            this.lorryList = lorryList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_lorry_list, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Lorry lorry = lorryList.get(position);

            textViewName.setText(lorry.getId());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true


                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

            return listViewItem;
        }
    }
    private void readLorries() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_LORRIES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshLorryList(JSONArray lorries) throws JSONException {
        //clearing previous heroes
        try {
            lorryList.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Lorries",lorries.toString());
        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < lorries.length(); i++) {
            //getting each hero object
            JSONObject obj = lorries.getJSONObject(i);
            Log.i("Got Json","Yay");
            //adding the lorry to the list
            Lorry temp = new Lorry(obj.getString("id"),
                    obj.getDouble("temp"),
                    obj.getInt("door"),
                    obj.getDouble("lat"),
                    obj.getDouble("lon"));
            Log.i("Created Obj","Created");
            lorryList.add(temp);
        }

        //creating the adapter and setting it to the listview
        LorryAdapter adapter = new LorryAdapter(lorryList);
        listView.setAdapter(adapter);
    }



}

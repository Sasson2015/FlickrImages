package com.sassonsoft.flickrimages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowImagesActivity extends AppCompatActivity {

    private Bundle extras;
    private String countryName;
    private GridView gridView;
    private Context context;
    private CustomGridViewAdapter adapter;
    private Bitmap bitmap = null;
    private URL url;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);

        initializeVariables();
        if (checkIfOnline()) {
            new GetImagesDetailsFromServer().execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + Consts.FLICKR_APP_KEY + "&tags=" + countryName + "&format=json&nojsoncallback=1");

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ShowImagesActivity.this, ViewFullImageSizeActivity.class);
                    intent.putExtra("IMAGE", imageItems.get(position).getImage());
                    startActivity(intent);

                }
            });

        }

        else
            Toast.makeText(this, "Error: Networks isn't available", Toast.LENGTH_LONG).show();
    }

    private void initializeVariables() {
        extras = getIntent().getExtras();
        context = getBaseContext();
        gridView = (GridView) findViewById(R.id.gridView);

        if (extras != null)
            countryName = extras.getString("COUNTRY_NAME");

        adapter = new CustomGridViewAdapter(ShowImagesActivity.this, R.layout.layout_grid_view_item, imageItems);
        gridView.setAdapter(adapter);
    }

    private boolean checkIfOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_images, menu);
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

    public class GetImagesDetailsFromServer extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            return FlickrServerAction.getImageDetails(params[0]);
        }

        protected void onPostExecute(String result) {
            if (result.equals("Error"))
                Toast.makeText(context, "Flickr server return an error", Toast.LENGTH_LONG).show();

            else {
                FlickrImageDetails.setImagesDetails(result);
                List<String> imageUrl = FlickrImageDetails.getUrl();
                for (int i = 0; i < imageUrl.size(); i++) {
                    new SetGridViewAdapter().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageUrl.get(i));
                }
            }
        }
    }

    public class SetGridViewAdapter extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.w("FlickrImages", e.getMessage());
            }

            catch (IOException e) {
                e.printStackTrace();
                Log.w("FlickrImages", e.getMessage());
            }


            return "Yes";

        }

        protected void onPostExecute(String result) {
            imageItems.add(new ImageItem(bitmap, "Image #" + Integer.toString(imageItems.size() + 1)));
            adapter.notifyDataSetChanged();
        }
    }
}

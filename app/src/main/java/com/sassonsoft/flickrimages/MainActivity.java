package com.sassonsoft.flickrimages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //אתחול משתנים

    private ListView countryListView;
    private ImageView countryFlag;
    private TextView countryName;

    private SimpleAdapter adapter;
    private List<Map<String, String>> countryList = new ArrayList<>();//רשימה שמכילה את כל התמונות והשמות של התמונות
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables(); //יצירת פונקציה להגדרה וקישור המשתנים

        adapter = new SimpleAdapter(this, countryList, R.layout.layout_country_list_item, new String[]{"FLAG_IMAGE", "NAME"}, new int[]{R.id.ivFlag, R.id.tvCountryName});
        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,ShowImagesActivity.class);
                intent.putExtra("COUNTRY_NAME", countryList.get(position).get("NAME"));
                startActivity(intent);
            }
        });
    }

    private void initializeVariables() {
        countryListView = (ListView) findViewById(R.id.lvCountry);
        countryFlag = (ImageView) findViewById(R.id.ivFlag);
        countryName = (TextView) findViewById(R.id.tvCountryName);

        //יצירת מערך והשמה לתוך רשימהגם לתמונה וגם לשמות
        CountryDetails.setVariables(getBaseContext());
        CountryDetails.createList(countryList);
        context =getBaseContext();

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
}

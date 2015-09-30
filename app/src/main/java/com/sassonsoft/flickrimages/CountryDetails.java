package com.sassonsoft.flickrimages;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sasson on 21/09/2015.
 */

//קלאס בשביל פרטי המדינות
public class CountryDetails {
    private static List<String> name = new ArrayList<>();
    private static List<Integer> flagImage = new ArrayList<>();

    public static List<String> getName() {
        return name;
    }
    public static List<Integer> getFlagImage() {
        return flagImage;
    }

    public static void createList(List<Map<String, String>> countryList) {
        for (int i = 0; i < name.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("NAME", name.get(i));
            map.put("FLAG_IMAGE", Integer.toString(flagImage.get(i)));
            countryList.add(map);
        }
    }

    public static void setVariables(Context context) {
        String[] countryName = context.getResources().getStringArray(R.array.country_name_arrays);
        name = Arrays.asList(countryName);

        int[] flags = new int[]{
                R.drawable.israel,
                R.drawable.argentina,
                R.drawable.brazil,
                R.drawable.canada,
                R.drawable.colombia,
                R.drawable.netherlands,
                R.drawable.peru,
                R.drawable.japan,
                R.drawable.south_africa,
                R.drawable.usa,
        };

        for (int i = 0; i < flags.length; i++)
            flagImage.add(flags[i]);
    }
}

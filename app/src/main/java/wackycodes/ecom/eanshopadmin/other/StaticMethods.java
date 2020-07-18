package wackycodes.ecom.eanshopadmin.other;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.other.StaticValues.CURRENT_CITY_CODE;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_ID;

public class StaticMethods {

    public static void showToast(Context context, String msg){
        Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
    }
    public static void showToast(String msg, Context context){
        Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
    }

    public static void gotoURL(Context context, String urlLink){
        Uri uri = Uri.parse( urlLink );
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity( intent );
    }

    public static String getRandomNumAccordingToDate(){
        Date date =  Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());

        String crrDateDay = simpleDateFormat.format(new Date());

        return crrDateDay;
    }

    public static String getTwoDigitRandom(){
        Random random = new Random();
        // Generate random integers in range 0 to 9999
        int rand_int1 = 0;
        String randNum = "";
        do {
            rand_int1 = random.nextInt(1000);
        }while ( rand_int1 <= 0 );

        if (rand_int1<99){
            rand_int1 = rand_int1*100;
        }

        randNum = String.valueOf( rand_int1 ).substring( 0, 2 );

        return randNum;
    }

    public static String getFiveDigitRandom(){
        Random random = new Random();
        // Generate random integers in range 0 to 9999
        int rand_int1 = 0;
        String randNum = "";
        do {
            rand_int1 = random.nextInt(100000);
        }while ( rand_int1 <= 0 );

        if (rand_int1<999){
            rand_int1 = rand_int1*1000;
        }else if (rand_int1<9999){
            rand_int1 = rand_int1*100;
        }else if (rand_int1<=99999){
            rand_int1 = rand_int1*10;
        }
        randNum = String.valueOf( rand_int1 ).substring( 0, 5 );

        return randNum;
    }

    public static String getRandomShopId(Context context){
        String shopId = "";
        if ( CURRENT_CITY_CODE.equals( "BHOPAL" ) ){
            shopId = "4620" + getFiveDigitRandom();
            return shopId;
        }else if ( CURRENT_CITY_CODE.equals( "INDORE" ) ){
            shopId = "4520" + getFiveDigitRandom();
            return shopId;
        }else{
            Toast.makeText( context, "City not registered yet!", Toast.LENGTH_SHORT ).show();
            return shopId;
        }
    }

    public static String getRandomProductId(Context context){
        String productId = SHOP_ID.substring( 4 ) + getFiveDigitRandom();
//        String str1 = SHOP_ID.substring( 4 );
        return productId;
    }

    // Remove Duplicate from string...
    public static String removeDuplicate(String reqString){
        // Remove duplicates...
        LinkedHashSet <String> tagSet = new LinkedHashSet <>( Arrays.asList(reqString));
        StringBuffer stringBuffer = new StringBuffer();
        int tempInd = 0;

        for (String s : tagSet){
            if (tempInd > 0){
                stringBuffer.append( " " );
            }
            stringBuffer.append( s );
            tempInd++;
        }

        return stringBuffer.toString();
    }


    /*
        // TODO : List...
        1. Add Shop View Page
            - Add Shop Properties
            - permission denied
            - Admin restrict
            - change owner admin

        2. Add New Admin
            - Type...
            - Permission..

        3. Total Shop List with city name
        4. Search Any shop
        5. Add New City
           - Add Home Page with
           - add category layout...
        6. Add New Shop
            - Using new Activity
            - add shop details

        7. Add New Category
            - Add Shop container with..

        8. Add Accept Request of Shop...


     */

}
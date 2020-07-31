package wackycodes.ecom.eanshopadmin.other;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

//    public static String getRandomShopId(Context context){
//        String shopId = "";
//        if ( CURRENT_CITY_CODE.equals( "BHOPAL" ) ){
//            shopId = "4620" + getFiveDigitRandom();
//            return shopId;
//        }else if ( CURRENT_CITY_CODE.equals( "INDORE" ) ){
//            shopId = "4520" + getFiveDigitRandom();
//            return shopId;
//        }else{
//            Toast.makeText( context, "City not registered yet!", Toast.LENGTH_SHORT ).show();
//            return shopId;
//        }
//    }

    public static String getRandomProductId(Context context){
        String productId = SHOP_ID.substring( 4 ) + getFiveDigitRandom();
//        String str1 = SHOP_ID.substring( 4 );
        return productId;
    }

    // Remove Duplicate from string...
    public static String removeDuplicate(String[] reqString){
        // Remove duplicates...
        LinkedHashSet <String> tagSet = new LinkedHashSet <>( Arrays.asList(reqString));
        StringBuffer stringBuffer = new StringBuffer();
        int tempInd = 0;

        for (String s : tagSet){
            if (s != null){
                stringBuffer.append( s.trim() );
                stringBuffer.append( ", " );
            }
            tempInd++;
        }

        return stringBuffer.toString();
    }

    public static void writeFileInLocal(@NonNull Context context, String fileName, String textMsg){
        try {
//            FileOutputStream fileOS = openFileOutput( fileName, MODE_PRIVATE );
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOS );
//            outputStreamWriter.write( textMsg );
//            outputStreamWriter.close();
            File folder1 = new File(context.getExternalFilesDir( Environment.getExternalStorageDirectory().getAbsolutePath() ), fileName);
            folder1.mkdirs();
            File pdfFile = new File(folder1, fileName + ".txt");
//            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream( pdfFile );
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOutputStream );
            outputStreamWriter.write( textMsg );
            outputStreamWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileFromLocal( @NonNull Context context, String fileName, boolean isChecked){
        StringBuilder fileValue = null;
        File file = new File( context.getExternalFilesDir( Environment.getExternalStorageDirectory().getAbsolutePath() ) + "/" + fileName + "/" + fileName + ".txt" );
        if (file.exists()){
            try {
                FileReader fileReader = new FileReader( file );
                while((fileReader.read( ))!= -1){
                    if (fileValue != null){
                        fileValue.append( (char) fileReader.read() );
                    }else{
                        fileValue = new StringBuilder( String.valueOf( (char) fileReader.read() ) );
                    }
                }

                return fileValue.toString().trim();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }else{
            return null;
        }
    }


    public static String readFileFromLocal( Context context, String fileName ){
        String msg = null;
        try {
            File file = new File( context
                    .getExternalFilesDir( Environment.getExternalStorageDirectory().getAbsolutePath() ), fileName + "/"+ fileName + ".txt");
            if (file.exists()){
                FileInputStream fileIS = new FileInputStream( file );
//            FileInputStream fileIS = openFileInput( fileName );
                InputStreamReader inputStreamReader = new InputStreamReader( fileIS );
                char[] inputBuffer = new char[100];
                int charRead;
                while(( charRead = inputStreamReader.read( inputBuffer )) > 0){
                    String readString = String.copyValueOf( inputBuffer, 0, charRead );
                    if (msg != null){
                        msg += readString;
                    }else{
                        msg = readString;
                    }
                }
                inputStreamReader.close();
            }

        }catch (Exception e){
            showToast( context, e.getMessage() );
        }finally{
            return msg;
        }
    }

    public static String getTimeFromNow( String dateData, String timeData ){
        String timing =  "on " + dateData;
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd wa HH:mm:ss");
            Date past = format.parse(dateData + " wa " + timeData );
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");

            if(seconds<60)
            {
//                System.out.println(seconds+" seconds ago");
                timing = "Just now";
            }
            else if(minutes<60)
            {
                timing = minutes + " Min ago";
            }
            else if(hours<24)
            {
                timing = hours + " Min ago";
            }
            else if (days < 8)
            {
                timing = days + " Min ago";
            }
        }
        catch (Exception j){
            j.printStackTrace();
        }finally {
            return timing;
        }
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

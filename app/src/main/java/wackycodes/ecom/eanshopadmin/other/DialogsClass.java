package wackycodes.ecom.eanshopadmin.other;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import wackycodes.ecom.eanshopadmin.R;

public class DialogsClass {


    public static Dialog getMessageDialog(Context context, @NonNull String title, @NonNull String message){
//         Single Ok Button ...
        final Dialog dialog = new Dialog( context );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_message_ok_layout );
        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.setCancelable( false );
        Button okBtn = dialog.findViewById( R.id.dialog_ok_btn );
        TextView titleText = dialog.findViewById( R.id.dialog_title );
        TextView messageText = dialog.findViewById( R.id.dialog_message );
        titleText.setText( title );
        messageText.setText( message );
        okBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        return dialog;
    }

    public static Dialog getDialog(Context context){
        // ---- Progress Dialog...
        Dialog progressDialog = new Dialog( context );
        progressDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        progressDialog.setContentView( R.layout.dialog_circle_process );
        progressDialog.setCancelable( false );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getDialog.getWindow().setBackgroundDrawable( context.getDrawable( R.drawable.x_ractangle_layout ) );
        }
        progressDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        getDialog.show();
        return progressDialog;
        // ---- Progress Dialog...
    }

    public static AlertDialog.Builder alertDialog(Context c, @Nullable String title, @NonNull String body) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        if (title!=null)
            builder.setTitle(title);
        builder.setMessage(body);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }


    public static void setAlarmOnNotification(Context context, String notifyTitle, String notifyBody){
        Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
        MediaPlayer mp = MediaPlayer. create ( context, alarmSound );
        mp.start();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "default" )
                        .setSmallIcon( R.mipmap.logo_round )
                        .setContentTitle( notifyTitle )
                        .setContentText( notifyBody ) ;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService( Context.NOTIFICATION_SERVICE );
        mNotificationManager.notify(( int ) System. currentTimeMillis() , mBuilder.build());
    }


}

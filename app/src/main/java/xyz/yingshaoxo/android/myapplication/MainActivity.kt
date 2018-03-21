package xyz.yingshaoxo.android.myapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get permission
        // import android.Manifest
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 999)

            super.onBackPressed()
            Thread.sleep(7 * 1000)
        }


        stopService(Layout)
        startService(Layout)

        minimize.setOnClickListener {
            push_notification()
            super.onBackPressed()
        }

        next.setOnClickListener {
            stopService(Layout)
            startService(Layout)
        }

        exit.setOnClickListener {
            stopService(Layout)
            finish()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        push_notification()
        super.onBackPressed()
    }


        // Method to start the service
    fun startService(view: View) {
        startService(Intent(baseContext, MyService::class.java))
    }

    // Method to stop the service
    fun stopService(view: View) {
        stopService(Intent(baseContext, MyService::class.java))
    }


    fun push_notification() {
        val mBuilder = NotificationCompat.Builder(this, "super yingshaoxo")
                .setSmallIcon(R.drawable.abc_switch_thumb_material)
                .setContentTitle("Playing...")
                .setContentText("Click here to the next song")

        var resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, mBuilder.build())
    }

}
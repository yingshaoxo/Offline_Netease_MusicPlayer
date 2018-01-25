package xyz.yingshaoxo.android.myapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var path_name = Environment.getExternalStorageDirectory().toString() + "/netease/cloudmusic/Music"
        var path_sequence = File(path_name).walkTopDown().drop(1)
        var path_list = path_sequence.toList()


        //mp = MediaPlayer()
        mp = GlobalVariable.mp
        mp.isLooping = false

        mp.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            this@MainActivity.finish()
        })

        imageButton.setOnClickListener {
            textView.text = path_list.random().toString()

            if (mp.isPlaying == false) {
                mp.setDataSource(textView.text.toString())
                mp.prepare()
                mp.start()

            } else {
                mp.stop()
                mp.reset()

                mp.setDataSource(textView.text.toString())
                mp.prepare()
                mp.start()
            }
        }

        imageButton.performClick() //start the music
        push_notification()
    }


    fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

    fun push_notification() {
        val mBuilder = NotificationCompat.Builder(this, "super yingshaoxo")
                .setSmallIcon(R.drawable.abc_switch_thumb_material)
                .setContentTitle("Playing...")
                .setContentText("Click here to the next song")


        var resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent(this, javaClass),
                PendingIntent.FLAG_NO_CREATE
        );
        mBuilder.setContentIntent(resultPendingIntent);

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, mBuilder.build())

        super.onBackPressed()
    }

}

object GlobalVariable {
    var mp = MediaPlayer()
}
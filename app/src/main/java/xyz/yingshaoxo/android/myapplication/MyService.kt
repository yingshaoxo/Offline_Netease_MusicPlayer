package xyz.yingshaoxo.android.myapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Environment
import android.os.IBinder
import android.support.annotation.Nullable
import android.widget.Toast
import java.io.File
import java.util.*


class MyService : Service() {
    var mp = MediaPlayer()

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Let it continue running until it is stopped.

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()

        var path_name = Environment.getExternalStorageDirectory().toString() + "/netease/cloudmusic/Music"
        var path_sequence = File(path_name).walkTopDown().drop(1)
        var path_list = path_sequence.toList()


        //mp = MediaPlayer()
        //mp = GlobalVariable.mp

        val file_name = path_list.random().toString()

        mp.setDataSource(file_name)
        mp.prepare()
        mp.start()


        mp.setOnCompletionListener {
            val file_name = path_list.random().toString()

            mp.stop()
            mp.reset()

            mp.setDataSource(file_name)
            mp.prepare()
            mp.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show()

        mp.stop()
        mp.release()
    }
}


fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

/*object GlobalVariable {
    var mp = MediaPlayer()
}*/
package com.example.videoplayer.fragment

import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.videoplayer.R
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerFragment(private val uri:String) : Fragment() {

    private val  mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaPlayer.apply {
            setOnPreparedListener(){
                progressBarH.max = it.duration
                seekTo(1)
                progressBar.visibility = View.INVISIBLE

               val width =  mediaPlayer.videoWidth
               val height =  mediaPlayer.videoHeight

                val params = surfaceView.layoutParams
                params.width = resources.displayMetrics.widthPixels
                params.height = (resources.displayMetrics.widthPixels * height/width*1.0f).toInt()
                surfaceView.layoutParams = params

            }
            setDataSource(uri)
            prepareAsync()
            progressBar.visibility = View.VISIBLE
        }

        lifecycleScope.launch{
            while (true){
                progressBarH.progress = mediaPlayer.currentPosition
                delay(500)
            }
        }

        surfaceView.holder.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                mediaPlayer.setDisplay(holder)
                mediaPlayer.setScreenOnWhilePlaying(true)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

            }

        })

    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.seekTo(1)
        mediaPlayer.start()
        lifecycleScope.launch {
            while (!mediaPlayer.isPlaying){
                mediaPlayer.seekTo(1)
                mediaPlayer.start()
                delay(500)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

}
package com.example.videoplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videoplayer.R
import kotlinx.android.synthetic.main.fragment_video.*

private val videoUris = listOf(
    "https://img.tukuppt.com/video_show/2629112/00/08/47/5d24abddcc069.mp4",
    "https://img.tukuppt.com/video_show/2269348/00/02/24/5b53286229689.mp4",
    "https://img.tukuppt.com/video_show/2629112/00/08/47/5d236747774df.mp4",
    "https://img.tukuppt.com/video_show/14331404/00/14/31/5e44bc936680d.mp4",
    "https://img.tukuppt.com/video_show/2405179/00/01/68/5b4840872ffb1.mp4",
    "https://img.tukuppt.com/video_show/2269348/00/19/79/5f1508601e6ff.mp4",
    "https://img.tukuppt.com/video_show/2418175/00/08/28/5d1c49965a254.mp4",
    "https://img.tukuppt.com/video_show/2629112/00/08/02/5d14f97682359.mp4",
    "https://img.tukuppt.com/video_show/2418175/00/08/25/5d22e19b46dd4.mp4",
    "https://img.tukuppt.com/video_show/14331404/00/14/31/5e44b875071c1.mp4",
    "https://img.tukuppt.com/video_show/3670116/00/02/03/5b4f3eec1442b.mp4",
    "https://img.tukuppt.com/video_show/2422006/00/01/70/5b4c47427d92b.mp4"

)

class VideoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewPager.apply {
            adapter  = object :FragmentStateAdapter(this@VideoFragment){
                override fun getItemCount() = videoUris.size

                override fun createFragment(position: Int) = PlayerFragment(videoUris[position])
            }
            offscreenPageLimit = 5
        }
    }

}
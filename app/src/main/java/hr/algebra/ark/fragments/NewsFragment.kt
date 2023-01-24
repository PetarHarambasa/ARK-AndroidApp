package hr.algebra.ark.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import hr.algebra.ark.R


/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    private var videoView: VideoView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_news, container, false)
        videoView = view.findViewById(R.id.vvArkAnimatedSeries)
        setUpVideo()

        // Inflate the layout for this fragment
        return view
    }

    private fun setUpVideo() {
        val videoPath = "android.resource://" + requireActivity().packageName + "/" + R.raw.arkanimatedsiries
        val uri = Uri.parse(videoPath)
        videoView!!.setVideoURI(uri)
        val mediaController = MediaController(
            activity
        )
        videoView!!.setMediaController(mediaController)
    }
}
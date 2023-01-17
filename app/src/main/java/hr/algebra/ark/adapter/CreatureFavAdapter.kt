package hr.algebra.ark.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ark.FavCreaturePagerActivity
import hr.algebra.ark.POSITION
import hr.algebra.ark.R
import hr.algebra.ark.framework.startActivity
import hr.algebra.ark.model.Creature
import java.io.File

class CreatureFavAdapter (private val context: Context, private val favCreatures: MutableList<Creature>) :
RecyclerView.Adapter<CreatureFavAdapter.ViewHolder>(){

    var mediaPlayer: MediaPlayer? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivDinoImage = itemView.findViewById<ImageView>(R.id.ivDinoImage)

        fun bind(creature: Creature) {
            Picasso.get()
                .load(File(creature.urlImage))
                .error(R.drawable.magmasaur)
                .into(ivDinoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.creature, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favCreature = favCreatures[position]

        holder.itemView.setOnClickListener{
            context.startActivity<FavCreaturePagerActivity>(POSITION, position)
            playCreatureSoundMusic(favCreature)
        }

        holder.bind(favCreature)
    }

    private fun playCreatureSoundMusic(favCreature: Creature) {
        val dinoSound = Uri.parse(favCreature.audio)
        mediaPlayer = MediaPlayer.create(context, dinoSound)
        mediaPlayer?.start()
    }

    override fun getItemCount() = favCreatures.size
}
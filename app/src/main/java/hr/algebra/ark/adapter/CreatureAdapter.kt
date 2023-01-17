package hr.algebra.ark.adapter

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ark.CreaturePagerActivity
import hr.algebra.ark.POSITION
import hr.algebra.ark.R
import hr.algebra.ark.framework.startActivity
import hr.algebra.ark.model.Creature
import hr.algebra.ark.provider.ARK_PROVIDER_CONTENT_URI
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class CreatureAdapter(private val context: Context, private val creatures: MutableList<Creature>) :
    RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {

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
        val creature = creatures[position]

        holder.itemView.setOnLongClickListener{
            deleteCreature(position)
            true
        }
        holder.itemView.setOnClickListener{
            context.startActivity<CreaturePagerActivity>(POSITION, position)
            playCreatureSoundMusic(creature)
        }

        holder.bind(creature)
    }

    private fun playCreatureSoundMusic(creature: Creature) {
        val dinoSound = Uri.parse(creature.audio)
        mediaPlayer = MediaPlayer.create(context, dinoSound)
        mediaPlayer?.start()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteCreature(position: Int) {
        val creature = creatures[position]
        AlertDialog.Builder(context).apply {

            setTitle(R.string.delete)
            setMessage(context.getString(R.string.really_delete))
            setIcon(R.drawable.deletedino)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                context.contentResolver.delete(
                    ContentUris.withAppendedId(
                        ARK_PROVIDER_CONTENT_URI,
                        creature._id!!
                    ), null, null
                )
                File(creature.urlImage).delete()
                creatures.removeAt(position)
                notifyDataSetChanged()

            }
            setNegativeButton(context.getString(R.string.cancel), null)
            show()

        }
    }

    override fun getItemCount() = creatures.size
}
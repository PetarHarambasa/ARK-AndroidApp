package hr.algebra.ark.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ark.R
import hr.algebra.ark.factory.getArkRepository
import hr.algebra.ark.model.Creature
import hr.algebra.ark.provider.ARK_PROVIDER_CONTENT_URI
import java.io.File

class CreaturePagerAdapter(
    private val context: Context,
    private val creatures: MutableList<Creature>
) :
    RecyclerView.Adapter<CreaturePagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivDinoImage = itemView.findViewById<ImageView>(R.id.ivDinoImage)
        private val ivDinoIcon = itemView.findViewById<ImageView>(R.id.ivDinoIcon)
        private val tvCreatureName = itemView.findViewById<TextView>(R.id.tvCreatureName)
        private val tvDiet = itemView.findViewById<TextView>(R.id.tvDiet)
        private val tvGroupType = itemView.findViewById<TextView>(R.id.tvGroupType)
        private val tvTemperament = itemView.findViewById<TextView>(R.id.tvTemperament)
        private val tvAboutCreature = itemView.findViewById<TextView>(R.id.tvAboutCreature)

        val ivFavouriteIcon = itemView.findViewById<ImageView>(R.id.ivDinoFavIcon)
        val ratingStars = itemView.findViewById<RatingBar>(R.id.ratingBar)

        fun bind(creature: Creature) {
            Picasso.get()
                .load(File(creature.urlImage))
                .error(R.drawable.magmasaur)
                .into(ivDinoImage)
            Picasso.get()
                .load(File(creature.urlIcon))
                .error(R.drawable.magmasaur)
                .into(ivDinoIcon)
            tvCreatureName.text = creature.creatureName
            tvDiet.text = creature.diet
            tvTemperament.text = creature.temperament
            tvGroupType.text = creature.groupType
            tvAboutCreature.text = creature.aboutCreature
            ivFavouriteIcon.setImageResource(if (creature.favourite) R.drawable.iconfavourite else R.drawable.iconfavouriteno)
            ratingStars.rating = creature.ratingStars
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.creature_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creature = creatures[position]

        holder.ivFavouriteIcon.setOnClickListener {
            creature.favourite = !creature.favourite
            context.contentResolver.update(
                ContentUris.withAppendedId(ARK_PROVIDER_CONTENT_URI, creature._id!!),
                ContentValues().apply {
                    put(Creature::favourite.name, creature.favourite)
                }, null, null
            )
            notifyItemChanged(position)
        }
        holder.ratingStars.setOnRatingBarChangeListener { ratingBar, fl, b ->
            creature.ratingStars = ratingBar.rating
            context.contentResolver.update(
                ContentUris.withAppendedId(ARK_PROVIDER_CONTENT_URI, creature._id!!),
                ContentValues().apply {
                    put(Creature::ratingStars.name, creature.ratingStars)
                }, null, null
            )
        }

        holder.bind(creature)
    }

    override fun getItemCount() = creatures.size
}
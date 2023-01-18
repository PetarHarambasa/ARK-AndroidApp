package hr.algebra.ark.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ark.R
import hr.algebra.ark.model.Creature
import hr.algebra.ark.provider.ARK_PROVIDER_CONTENT_URI
import java.io.File

class FavCreaturePagerAdapter(
    private val context: Context,
    private val favCreatures: MutableList<Creature>
) :
    RecyclerView.Adapter<FavCreaturePagerAdapter.ViewHolder>() {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.creature_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favCreature = favCreatures[position]

        holder.ivFavouriteIcon.setOnClickListener {
            favCreature.favourite = !favCreature.favourite
            context.contentResolver.update(
                ContentUris.withAppendedId(ARK_PROVIDER_CONTENT_URI, favCreature._id!!),
                ContentValues().apply {
                    put(Creature::favourite.name, favCreature.favourite)
                }, null, null
            )
            notifyItemChanged(position)
        }
        holder.ratingStars.setOnRatingBarChangeListener { ratingBar, fl, b ->
            favCreature.ratingStars = ratingBar.rating
            context.contentResolver.update(
                ContentUris.withAppendedId(ARK_PROVIDER_CONTENT_URI, favCreature._id!!),
                ContentValues().apply {
                    put(Creature::ratingStars.name, favCreature.ratingStars)
                }, null, null
            )
        }

        holder.bind(favCreature)
    }

    override fun getItemCount() = favCreatures.size

}

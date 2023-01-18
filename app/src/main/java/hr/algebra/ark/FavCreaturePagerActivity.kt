package hr.algebra.ark

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.ark.adapter.FavCreaturePagerAdapter
import hr.algebra.ark.databinding.ActivityFavCreaturePagerBinding
import hr.algebra.ark.framework.fetchCreatures
import hr.algebra.ark.model.Creature


class FavCreaturePagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavCreaturePagerBinding
    private lateinit var favCreatures: MutableList<Creature>
    private var favCreaturePosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavCreaturePagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        if (!favCreatures[favCreaturePosition].favourite){
            finish()
            startActivity(Intent(this, MainScreenActivity::class.java))
        }
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        favCreatures = fetchCreatures().filter { it.favourite } as MutableList<Creature>
        favCreaturePosition = intent.getIntExtra(POSITION, favCreaturePosition)
        binding.favViewPager.adapter = FavCreaturePagerAdapter(this, favCreatures)
        binding.favViewPager.currentItem = favCreaturePosition
    }
}
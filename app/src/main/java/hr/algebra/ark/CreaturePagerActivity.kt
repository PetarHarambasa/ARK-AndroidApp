package hr.algebra.ark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.ark.adapter.CreaturePagerAdapter
import hr.algebra.ark.databinding.ActivityCreaturePagerBinding
import hr.algebra.ark.framework.fetchCreatures
import hr.algebra.ark.model.Creature


const val POSITION = "hr.algebra.ark.position"
class CreaturePagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreaturePagerBinding
    private lateinit var creatures: MutableList<Creature>
    private var creaturePosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreaturePagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        creatures = fetchCreatures()
        creaturePosition = intent.getIntExtra(POSITION, creaturePosition)
        binding.viewPager.adapter = CreaturePagerAdapter(this, creatures)
        binding.viewPager.currentItem = creaturePosition
    }
}
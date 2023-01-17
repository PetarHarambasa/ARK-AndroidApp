package hr.algebra.ark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.ark.R
import hr.algebra.ark.adapter.CreatureAdapter
import hr.algebra.ark.adapter.CreatureFavAdapter
import hr.algebra.ark.databinding.FragmentCreaturesBinding
import hr.algebra.ark.databinding.FragmentFavouriteBinding
import hr.algebra.ark.framework.fetchCreatures
import hr.algebra.ark.model.Creature

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favCreatures: MutableList<Creature>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favCreatures = requireContext().fetchCreatures().filter { it.favourite } as MutableList<Creature>
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFavCreatures.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CreatureFavAdapter(requireContext(), favCreatures)
        }
    }
}
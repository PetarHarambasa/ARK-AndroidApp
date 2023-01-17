package hr.algebra.ark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.ark.R
import hr.algebra.ark.adapter.CreatureAdapter
import hr.algebra.ark.databinding.FragmentCreaturesBinding
import hr.algebra.ark.framework.applyAnimation
import hr.algebra.ark.framework.fetchCreatures
import hr.algebra.ark.model.Creature

/**
 * A simple [Fragment] subclass.
 * Use the [CreaturesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreaturesFragment : Fragment() {
    private lateinit var binding: FragmentCreaturesBinding
    private lateinit var creatures: MutableList<Creature>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creatures = requireContext().fetchCreatures()
        binding = FragmentCreaturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCreatures.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CreatureAdapter(requireContext(), creatures)
        }
    }
}
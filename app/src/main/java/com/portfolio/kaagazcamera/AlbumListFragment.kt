package com.portfolio.kaagazcamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.portfolio.kaagazcamera.databinding.FragmentAlbumListBinding

private var _binding: FragmentAlbumListBinding? = null
private val binding get() = requireNotNull(_binding)

class AlbumListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        setEventHandlers()
        return binding.root
    }

    private fun setEventHandlers() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.cameraFragment)
        }
    }
}
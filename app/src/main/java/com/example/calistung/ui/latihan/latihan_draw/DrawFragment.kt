package com.example.calistung.ui.latihan.latihan_draw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.calistung.R
import com.example.calistung.databinding.FragmentDrawBinding

class DrawFragment : Fragment() {
    private lateinit var binding: FragmentDrawBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDrawBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawView.setStrokeWidth(120F)

    }

    fun getBitmap()=binding.drawView.getBitmap()
    fun clear(){
        binding.drawView.clearCanvas()
    }
}
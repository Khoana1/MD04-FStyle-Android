package com.example.eu_fstyle_mobile.src.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eu_fstyle_mobile.R
import com.example.eu_fstyle_mobile.src.model.IntroSlider

class IntroSliderAdapter(private val introSlider: List<IntroSlider>) :
    RecyclerView.Adapter<IntroSliderAdapter.IntroSliderAdapterViewHolder>() {
    inner class IntroSliderAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvDes = view.findViewById<TextView>(R.id.tv_description)
        private val imgIcon = view.findViewById<ImageView>(R.id.img_slide_icon)
        fun bind(introSlider: IntroSlider) {
            tvTitle.text = introSlider.title
            tvDes.text = introSlider.description
            imgIcon.setImageResource(introSlider.icon)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroSliderAdapterViewHolder {
        return IntroSliderAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_intro_item, parent, false
            )
        )
    }


    override fun getItemCount(): Int {
        return introSlider.size
    }

    override fun onBindViewHolder(holder: IntroSliderAdapterViewHolder, position: Int) {
        holder.bind(introSlider[position])
    }
}
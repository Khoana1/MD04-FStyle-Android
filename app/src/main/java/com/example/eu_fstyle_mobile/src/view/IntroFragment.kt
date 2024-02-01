package com.example.eu_fstyle_mobile.src.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.eu_fstyle_mobile.R
import com.example.eu_fstyle_mobile.databinding.FragmentIntroBinding
import com.example.eu_fstyle_mobile.src.adapter.IntroSliderAdapter
import com.example.eu_fstyle_mobile.src.model.IntroSlider

class IntroFragment : Fragment() {
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlider(
                "Chào mừng",
                "Chào mừng bạn đến với ứng dụng bán giày nam của cửa hàng chúng tôi nơi bạn tìm được những mẫu giày ưng ý , tận tâm chăm sóc sức khỏe đôi chân của bạn. Khám phá ngay!!!",
                R.drawable.ic_first_intro
            ),
            IntroSlider(
                "Sản phẩm",
                "Chúng tôi cam kết mang đến những sản phẩm tốt nhất cho khách hàng. Từ những mẫu giày thể thao đến những mẫu giày thời trang độc đáo và phong cách giúp bạn tự tin mọi lúc mọi nơi.",
                R.drawable.ic_second_intro
            ),
            IntroSlider(
                "Chất lượng",
                "Tin tưởng chúng tôi, đảm bảo thoải mái đôi chân trong mọi buổi tập. Đăng ký nhận thông báo về sản phẩm mới. Luôn lắng nghe, chia sẻ niềm đam mê thể thao của bạn!",
                R.drawable.ic_thirst_intro
            ),

            )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpIntroSlider.adapter = introSliderAdapter
        setupIndicator()
        setCurrentIndicator(0)
        binding.vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == 2) {
                    binding.btnNext.text = "Bắt đầu thôi"
                } else {
                    binding.btnNext.text = "Tiếp theo"
                }
            }
        })
        binding.btnNext.setOnClickListener{
            if (binding.vpIntroSlider.currentItem + 1 < introSliderAdapter.itemCount) {
                binding.vpIntroSlider.currentItem += 1
                if (binding.vpIntroSlider.currentItem == introSliderAdapter.itemCount - 1) {
                    binding.btnNext.text = "Bắt đầu thôi"
                } else {
                    binding.btnNext.text = "Tiếp theo"
                }
            } else {
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, SplashFragment())
                fragmentTransaction.commit()
            }
        }
        binding.tvSkipIntro.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, SplashFragment())
            fragmentTransaction.commit()
        }
    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 0, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.lnIndicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.lnIndicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.lnIndicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active
                    )
                )
            }
        }
    }
}
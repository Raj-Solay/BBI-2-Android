package com.biz.bizbulls.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.biz.bizbulls.R
import com.biz.bizbulls.adapter.HomeSlideAdapter
import com.biz.bizbulls.data.adaptermodel.SliderItem
import com.biz.bizbulls.databinding.FragmentAdminBinding
import com.biz.bizbulls.model.UserDetails
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.adapter.*
import com.biz.bizbulls.utils.MyProcessDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeAdminFragment : Fragment() {
    private lateinit var binding: FragmentAdminBinding
   // var foSliderAdapter: FOSliderAdapter? = null
   lateinit var homeSlideAdapter: HomeSlideAdapter
   private lateinit var imagesModel:ArrayList<String>
   lateinit var sharedPrefsManager: SharedPrefsManager
    var isAdminLog = false
    var customerFOStatusFragment: CustomerFOStatusFragment? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(layoutInflater)
        sharedPrefsManager= activity?.let { SharedPrefsManager(it) }!!
        imagesModel= ArrayList()
        isAdminLog = sharedPrefsManager.isAdminLogin
       // getUserRole(activity )

        sliderUpdate()
        loadServices()
        loadRetailView()
        loadFranchiseView()
        loadFoodView()
        serviceView()
        approvalesView()
        myHrView()
        assingnmentView()
        projectScopeView()
        referView()

        return binding.root
    }

    private fun loadServices() {
        binding.rvServices.setHasFixedSize(true)
        binding.rvServices.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val servicesAdapter = activity?.let { ServicesAdapter(it) }
        binding.rvServices.adapter = servicesAdapter

        binding.recyclerleads.setHasFixedSize(true)
        binding.recyclerleads.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val foodAndBeverageAdapter = activity?.let { FoodAndBeverageAdapter(it) }
        binding.recyclerleads.adapter = foodAndBeverageAdapter
    }

    private fun loadRetailView() {

    }

    private fun loadFranchiseView() {

    }
    private fun serviceView() {
        binding.rcyServicesEmp.setHasFixedSize(true)
        binding.rcyServicesEmp.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val servicesAdapter = activity?.let { ServicesAdapter(it) }
        binding.rcyServicesEmp.adapter = servicesAdapter
    }
    private fun approvalesView() {
        binding.rcyAproval.setHasFixedSize(true)
        binding.rcyAproval.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val approvalsAdapter = activity?.let { ApprovalsAdapter(it) }
        binding.rcyAproval.adapter = approvalsAdapter
    }
    private fun myHrView() {
        binding.rcyHr.setHasFixedSize(true)
        binding.rcyHr.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val hrAdapter = activity?.let { HrAdapter(it) }
        binding.rcyHr.adapter = hrAdapter
    }
    private fun assingnmentView() {
        binding.rcyAssignment.setHasFixedSize(true)
        binding.rcyAssignment.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val myAssignmentAdapter = activity?.let { MyAssignmentAdapter(it) }
        binding.rcyAssignment.adapter = myAssignmentAdapter
    }
    private fun projectScopeView() {
        binding.rvProjectsScope.setHasFixedSize(true)
        binding.rvProjectsScope.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val projectsScopeAdapter = activity?.let { ProjectsScopeAdapter(it) }
        binding.rvProjectsScope.adapter = projectsScopeAdapter
    }
    private fun referView() {
        binding.rvRefer.setHasFixedSize(true)
        binding.rvRefer.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val referAdapter = activity?.let { ReferAdapter(it) }
        binding.rvRefer.adapter = referAdapter
    }

    private fun loadFoodView() {

    }

    fun sliderUpdate() {
        // for Demo Purpose
        val sliderItemList: MutableList<SliderItem> = ArrayList()
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/9868884/pexels-photo-9868884.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=3&amp;h=750&amp;w=1260",
                R.drawable.slider
            )
        )
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                R.drawable.slider
            )
        )
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                R.drawable.slider
            )
        )
        imagesModel.add("https://images.pexels.com/photos/9868884/pexels-photo-9868884.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=3&amp;h=750&amp;w=1260")
        imagesModel.add("https://images.pexels.com/photos/9868884/pexels-photo-9868884.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=3&amp;h=750&amp;w=1260")
        imagesModel.add("https://images.pexels.com/photos/9868884/pexels-photo-9868884.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=3&amp;h=750&amp;w=1260")
        var icons = intArrayOf(
            R.drawable.biz_bulls,
            R.drawable.biz_bulls,
            R.drawable.biz_bulls,
        )
        homeSlideAdapter = HomeSlideAdapter(requireContext(), imagesModel)
        binding.viewpager.adapter = homeSlideAdapter
        binding.indicator.setViewPager(binding.viewpager)
        /*binding?.viewpager?.adapter = viewPagerAdapter
        binding?.indicator?.setViewPager(binding?.viewpager)
        foSliderAdapter = FOSliderAdapter(activity, sliderItemList)
        binding!!.imgsliderfo.setSliderAdapter(foSliderAdapter!!)
        binding!!.imgsliderfo.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding!!.imgsliderfo.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding!!.imgsliderfo.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding!!.imgsliderfo.indicatorSelectedColor = Color.WHITE
        binding!!.imgsliderfo.indicatorUnselectedColor = Color.GRAY
        binding!!.imgsliderfo.scrollTimeInSec = 3
        binding!!.imgsliderfo.isAutoCycle = true
        binding!!.imgsliderfo.startAutoCycle()*/
    }

    fun getUserRole(activity: FragmentActivity?) {
         val call: Call<UserDetails> =
            RetrofitClient.getUrl().userRoleDetails(sharedPrefsManager.authToken)
        println("________URL ::${call.request().url}")
        println("________authToken ::${sharedPrefsManager.authToken}")
        var token = sharedPrefsManager.authToken;
        call.enqueue(object : Callback<UserDetails> {
            override
            fun onResponse(
                call: Call<UserDetails>,
                responseObject: Response<UserDetails>
            ) {
                if (responseObject.isSuccessful) {
                    //responseObject.body()?.data
                    var userDetails = responseObject.body()
                    if (userDetails?.data?.roleId.toString().equals("2")){
                        binding.llEmployee.visibility=View.VISIBLE
                        binding.llService.visibility=View.GONE
                    }else{
                        binding.llEmployee.visibility=View.GONE
                        binding.llService.visibility=View.VISIBLE
                    }
               }
                }

            override
            fun onFailure(call: Call<UserDetails>, t: Throwable) {
                MyProcessDialog.dismiss()
            }
        })
    }

}
package com.foldio.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbi.bizbulls.databinding.FragmentReferBinding
import com.foldio.android.adapter.InviteAdapter
import com.foldio.android.adapter.SocialinviteAdapter
import com.foldio.android.adapter.SuggestedContentsAdapter


class ReferFragment() : Fragment() {

    lateinit var binding: FragmentReferBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentReferBinding.inflate(layoutInflater)
        linearLayoutManager = LinearLayoutManager(activity)
        initview()
        return binding.root
    }

    fun initview() {
        binding.rcySocial.setLayoutManager(GridLayoutManager(activity, 4))
        var socialinviteAdapter=SocialinviteAdapter()
        binding.rcySocial.adapter=socialinviteAdapter
        binding.rcySuggestedContents.setLayoutManager(GridLayoutManager(activity, 4))
        var suggestedContentsAdapter= SuggestedContentsAdapter()
        binding.rcySuggestedContents.adapter=suggestedContentsAdapter
        var inviteAdapter= InviteAdapter()
        binding.rcyInvite.adapter=inviteAdapter
    }


}
package com.example.submission_android_fundamental.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_android_fundamental.databinding.FragmentFollowBinding
import com.example.submission_android_fundamental.data.adapter.UserAdapter
import com.example.submission_android_fundamental.data.viewmodel.UserViewModel
import com.example.submission_android_fundamental.utils.Constanta
import com.example.submission_android_fundamental.utils.Help

class FollowFragment : Fragment() {
    private var username: String = "github"
    private var follow : String = "followers"
    private var adapter = UserAdapter()
    private var _binding: FragmentFollowBinding? = null

    private val binding get() = _binding!!
    private val viewModel : UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(Constanta.USERNAME)!!
            follow = it.getString(Constanta.FOLLOWERS)!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvUser.apply {
            setHasFixedSize(true)
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            layoutManager = llm
            adapter = this@FollowFragment.adapter

        }

        viewModel.apply {
            loading.observe(viewLifecycleOwner) {
                binding.progressBar.visibility = it
            }
            error.observe(viewLifecycleOwner) {
                context?.let {
                    Help.toast(it, "")
                }}
                userData.observe(viewLifecycleOwner) {
                    adapter.apply {
                        initData(it)
                        notifyDataSetChanged()
                    }

                }
                this.setUserData(follow, username)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.root.requestLayout()
    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(paramUsername: String, paramFollow: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(Constanta.USERNAME, paramUsername)
                    putString(Constanta.FOLLOWERS, paramFollow)
                }
            }
    }
}
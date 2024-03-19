package com.demo.retrofitroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.retrofitroom.databinding.ActivityMainBinding
import com.demo.retrofitroom.databinding.BottomSheetChangeUserBinding
import com.demo.retrofitroom.databinding.ItemRepoBinding
import com.demo.retrofitroom.entity.Repo
import com.demo.retrofitroom.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeData()
        viewModel.fetchUserRepos(SP.username)
    }

    private fun setupUI() {
        setupRefreshLayout()
        initRecyclerView()
        binding.fabChangeUser.setOnClickListener {
            UsernameBottomSheetFragment().show(supportFragmentManager, "change_user_fragment")
        }
    }

    private fun setupRefreshLayout() {
        binding.swiper.setOnRefreshListener {
            viewModel.fetchUserRepos(SP.username) {
                binding.swiper.isRefreshing = false
            }
        }
    }

    private fun initRecyclerView() {
        adapter = RepoAdapter()
        binding.rvRepos.layoutManager = LinearLayoutManager(this)
        binding.rvRepos.verticalInterval(8)
        binding.rvRepos.adapter = adapter
    }

    private fun observeData() {
        "Start observing data".dLog()
        viewModel.getAll().observe(this) {
            "Data changed, refresh list, size:${it.size}".dLog()
            adapter.submitList(it)
        }
    }

    class RepoAdapter : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(object :
        DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
            return RepoViewHolder(
                ItemRepoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        class RepoViewHolder(private val binding: ItemRepoBinding) : ViewHolder(binding.root) {
            fun bind(repo: Repo) {
                binding.tvName.text = repo.name
                binding.tvUrl.text = repo.url
                binding.tvUpdateTime.text = repo.updateTime
                binding.tvStarCount.text = repo.starCount.toString()
                binding.root.setOnClickListener {

                }
            }
        }
    }

    class UsernameBottomSheetFragment : BottomSheetDialogFragment() {

        private var _binding: BottomSheetChangeUserBinding? = null
        private val binding get() = _binding!!
        private val activityViewModel: MainActivityViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = BottomSheetChangeUserBinding.inflate(layoutInflater, container, false)
            binding.edtUsername.addTextChangedListener {
                binding.btnChange.isEnabled = !(it.isNullOrEmpty() || it.toString() == SP.username)
            }
            binding.btnChange.setOnClickListener {
                val newUsername = binding.edtUsername.text.toString()
                SP.username = newUsername
                activityViewModel.fetchUserRepos(newUsername, cleanup = true) {
                    dismiss()
                }
            }
            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
}
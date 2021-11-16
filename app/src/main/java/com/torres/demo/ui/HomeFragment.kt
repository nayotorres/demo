package com.torres.demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.torres.demo.adapter.MovieAdapter
import com.torres.demo.data.model.MovieDao
import com.torres.demo.databinding.FragmentHomeBinding
import com.torres.demo.utilities.CustomProgress
import com.torres.demo.utilities.extension.showGenericAlertDialog
import com.torres.demo.viewModel.HomeViewModel
import com.torres.demo.vo.Resource
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    var progressBar: CustomProgress = CustomProgress()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupRecycler()
        setupObservers()
    }

    fun setupSearchView(){
        binding.searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               viewModel.setMovie(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun setupRecycler(){
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun setupObservers(){
        viewModel.searchMoview.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Loading->{
                    progressBar.show(requireActivity())
                }

                is Resource.Success->{
                    progressBar.dialog?.dismiss()
                    if(it.data!!.results!!.isNotEmpty()){
                        val lisMov =ArrayList<MovieDao>()
                        for(mov in it.data!!.results!! ){
                            val movie = MovieDao(mov.id!!,mov.title!!,mov.overview!!,mov.release_date!!)
                            viewModel.saveMovie(movie)
                            lisMov.add(movie)
                        }

                        binding.recycler.adapter = MovieAdapter(lisMov,requireContext())
                    }

                }

                is Resource.DataError->{
                    progressBar.dialog?.dismiss()
                    viewModel.setGetMovie("a")
                }
            }
        })

        viewModel.getMovie.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    binding.recycler.adapter = MovieAdapter(it.data!! as ArrayList<MovieDao>,requireContext())
                }
            }
        })
    }

}
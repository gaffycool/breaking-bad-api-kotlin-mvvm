package com.gaffy.brackingbadtechtest.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffy.brackingbadtechtest.R
import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import com.gaffy.brackingbadtechtest.ui.main.adapter.CharacterAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private val adapter = CharacterAdapter(mutableListOf(), {})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        mainViewModel = getViewModel()
        mainViewModel.state.observe(this, Observer { viewState ->
            handleViewState(viewState)
        })
        if (mainViewModel.state.value == null) {
            mainViewModel.getBreakingBadChars()
        }

    }

    private fun setupRecyclerView() {
        rv_characters.layoutManager = LinearLayoutManager(this)
        rv_characters.adapter = adapter
    }

    private fun handleViewState(viewState: MainViewModel.ViewState) {
        when (viewState) {
            is MainViewModel.ViewState.InProgress -> displayProgress()
            is MainViewModel.ViewState.Success -> handleSuccess(viewState.characters)
            is MainViewModel.ViewState.Error -> handleError(viewState.errorMessage)
        }
    }

    private fun handleError(errorMessage: String) {
        progress_bar.visibility = View.GONE
        rv_characters.visibility = View.GONE
        tv_message.text = errorMessage
        tv_message.visibility = View.VISIBLE
        btn_retry.visibility = View.VISIBLE
    }

    private fun handleSuccess(characters: List<BreakingBadChar>) {
        progress_bar.visibility = View.GONE
        rv_characters.visibility = View.VISIBLE
        adapter.updateData(characters)
        tv_message.visibility = View.GONE
        btn_retry.visibility = View.GONE
    }

    private fun displayProgress() {
        rv_characters.visibility = View.GONE
        tv_message.visibility = View.GONE
        btn_retry.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }
}
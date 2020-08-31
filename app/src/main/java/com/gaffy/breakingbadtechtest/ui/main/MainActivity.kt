package com.gaffy.breakingbadtechtest.ui.main

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffy.breakingbadtechtest.R
import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import com.gaffy.breakingbadtechtest.ui.details.DetailsActivity
import com.gaffy.breakingbadtechtest.ui.main.adapter.CharacterAdapter
import com.gaffy.breakingbadtechtest.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private val characterAdapter = CharacterAdapter(mutableListOf()) { character ->
        startActivity(DetailsActivity.getIntent(this@MainActivity, character))
    }
    lateinit var seasonAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupSpinner()
        mainViewModel = getViewModel()
        mainViewModel.state.observe(this, Observer { viewState ->
            handleViewState(viewState)
        })
        if (mainViewModel.state.value == null) {
            mainViewModel.getBreakingBadChars()
        }
        et_search.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable) {
                mainViewModel.getBreakingBadChars(
                    et_search.text.toString(),
                    sp_season.selectedItemPosition
                )
            }
        })
        btn_retry.setOnClickListener {
            mainViewModel.getBreakingBadChars(
                et_search.text.toString(),
                sp_season.selectedItemPosition
            )
        }

    }

    private fun setupSpinner() {
        seasonAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            arrayOf("All Seasons", "Season 1", "Season 2", "Season 3", "Season 4", "Season 5")
        )
        sp_season.adapter = seasonAdapter
        sp_season.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                mainViewModel.getBreakingBadChars(et_search.text.toString(), position)
            }

        }
    }

    private fun setupRecyclerView() {
        rv_characters.layoutManager = LinearLayoutManager(this)
        rv_characters.adapter = characterAdapter
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
        characterAdapter.updateData(characters)
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
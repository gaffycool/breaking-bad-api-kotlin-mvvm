package com.gaffy.breakingbadtechtest.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gaffy.breakingbadtechtest.R
import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    companion object {
        private const val KEY_CHARACTER = "character"
        fun getIntent(context: Context, character: BreakingBadChar): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(KEY_CHARACTER, character)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val character = intent.getParcelableExtra<BreakingBadChar>(KEY_CHARACTER)
        if (character == null) {
            finish()
        } else {
            setCharacterDetails(character)
        }
    }

    private fun setCharacterDetails(character: BreakingBadChar) {
        title = character.name
        tv_name.text = getString(R.string.name_with_value, character.name)
        tv_occupation.text = getString(R.string.occupation_with_value, character.occupation)
        tv_status.text = getString(R.string.status_with_value, character.status)
        tv_nick_name.text = getString(R.string.nickname_with_value, character.nickname)
        tv_season_appearance.text =
            getString(R.string.season_appearance_with_value, character.appearance.toString())

        Glide.with(iv_character).load(character.img).into(iv_character)
    }
}
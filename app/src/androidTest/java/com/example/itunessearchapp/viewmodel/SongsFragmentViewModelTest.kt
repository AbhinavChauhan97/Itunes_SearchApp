package com.example.itunessearchapp.viewmodel

import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.ExpectFailure.assertThat
import org.junit.Before
import org.junit.Test

class SongsFragmentViewModelTest{
    private lateinit var viewModel: SongsFragmentViewModel

    @Before
    fun setup(){
        viewModel = SongsFragmentViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun ifOnlineReturnTrueReturnFalseOtherwise(){

    }
}
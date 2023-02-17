package com.andyha.featureloginui.login

import android.os.Bundle
import com.andyha.coreui.base.activity.BaseActivity
import com.andyha.featureloginui.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>({ActivityLoginBinding.inflate(it)}) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {}
}
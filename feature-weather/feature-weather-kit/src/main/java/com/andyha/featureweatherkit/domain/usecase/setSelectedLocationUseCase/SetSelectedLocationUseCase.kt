package com.andyha.featureweatherkit.domain.usecase.setSelectedLocationUseCase

import com.andyha.coredata.base.BaseUseCase
import kotlinx.coroutines.flow.Flow


interface SetSelectedLocationUseCase: BaseUseCase<String, Flow<Boolean>>
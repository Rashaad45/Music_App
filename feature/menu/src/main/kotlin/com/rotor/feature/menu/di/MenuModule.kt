package com.rotor.feature.menu.di

import com.rotor.feature.menu.data.MenuTreeProvider
import com.rotor.feature.menu.data.StaticMenuTreeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MenuModule {
    @Binds
    fun bindMenuTreeProvider(impl: StaticMenuTreeProvider): MenuTreeProvider
}

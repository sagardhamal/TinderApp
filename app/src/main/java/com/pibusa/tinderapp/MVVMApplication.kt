package com.pibusa.tinderapp

import android.app.Application
import com.pibusa.tinderapp.data.repository.MainRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MVVMApplication: Application(),KodeinAware {
    override val kodein= Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from provider { MainRepository(instance()) }
    }


}
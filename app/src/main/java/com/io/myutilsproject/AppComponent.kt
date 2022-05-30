package com.io.myutilsproject

import com.io.myutilsproject.repository.FirstRepository
import com.io.myutilsproject.repository.SecondRepository
import com.io.myutilsproject.screens.first.FirstPresenter
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.third.ThirdPresenter
import dagger.*
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component(modules = [AppModule::class, AppRepositoryModule::class])
@Singleton
interface AppComponent{
    val factory: MyPresenterFactory

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}

@Component(modules = [NextModule::class, NextRepositoryModule::class])
@NextScope
interface NextComponent {
    val factory: MyPresenterFactory

    @Component.Builder
    interface Builder {

        fun build(): NextComponent
    }
}

@Module
interface AppModule{

    @Multibinds
    fun provideEmptyPresenters(): Map<Class<out Presenter<*,*,*>>, Presenter<*,*,*>>

    @Binds
    @[IntoMap PresenterKey(FirstPresenter::class)]
    fun provideFirstPresenter(firstPresenter: FirstPresenter): Presenter<*,*,*>

    @Binds
    @[IntoMap PresenterKey(SecondPresenter::class)]
    fun provideSecondPresenter(secondPresenter: SecondPresenter): Presenter<*,*,*>
}

@Module
class AppRepositoryModule{

    @Provides
    @Singleton
    fun provideFirstRepository(): FirstRepository{
        return FirstRepository()
    }

    @Provides
    fun provideSecondRepository(): SecondRepository{
        return SecondRepository()
    }
}

@Module
interface NextModule{

    @Binds
    @[IntoMap PresenterKey(ThirdPresenter::class)]
    fun provideFirstPresenter(thirdPresenter: ThirdPresenter): Presenter<*,*,*>
}

@Module
class NextRepositoryModule{

    @Provides
    fun provideSecondRepository(): SecondRepository{
        return SecondRepository()
    }
}

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NextScope

@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class PresenterKey(val value: KClass<out Presenter<*,*,*>>)
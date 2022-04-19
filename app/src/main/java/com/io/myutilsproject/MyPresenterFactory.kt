package com.io.myutilsproject

import com.io.navigation.PresenterFactory
import kotlin.reflect.KClass

class MyPresenterFactory(
    _presenterStore: HashMap<String, KClass<out Presenter>>
): PresenterFactory<Presenter>(_presenterStore) {

}
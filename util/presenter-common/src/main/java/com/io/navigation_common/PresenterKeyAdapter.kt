package com.io.navigation_common

import java.util.UUID

abstract class PresenterKeyAdapter<Guide: Any> {
   abstract fun getKey(): Guide

   open fun getCacheKey(): String{
      return UUID.randomUUID().toString()
   }
}
package com.torres.demo.utilities

import com.torres.demo.data.model.LocationEntity

interface ClickAdapter {
    fun result(location:LocationEntity, position:Int)
}
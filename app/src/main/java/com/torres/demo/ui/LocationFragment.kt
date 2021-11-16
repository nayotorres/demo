package com.torres.demo.ui

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.torres.demo.R
import com.torres.demo.adapter.LocationAdapter
import com.torres.demo.data.model.LocationEntity
import com.torres.demo.databinding.FragmentLocationBinding
import com.torres.demo.utilities.ClickAdapter
import com.torres.demo.utilities.FirebaseUtils
import kotlin.collections.ArrayList


class LocationFragment : Fragment() {

    private lateinit var binding:FragmentLocationBinding
    private var mMap: GoogleMap? = null
    var listMarker: ArrayList<Marker> = ArrayList()
    var listItems:ArrayList<LocationEntity> = ArrayList()
    var positionTemp= -1

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentLocationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setpRecycler()
        val  mapFragment =  childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it
        }
        setupGetData()
    }

    private fun moveCamera(latLng: LatLng){
        val cam = CameraPosition.builder()
            .target(latLng)
            .zoom(12.0f)
            .build()
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cam))
    }


    private fun setupGetData(){

        FirebaseUtils().fireStoreDatabase.collection("locationes")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    val latlong = document.data.values.toString().split(",")
                    var latitud = latlong[0].replace("[","").toDouble()
                    var longitud = latlong[1].replace("]","").toDouble()
                    listItems.add(LocationEntity(document.id,latitud,longitud))
                    val location= LatLng(latitud,longitud)
                    val marker = MarkerOptions().position(location)
                        .title("Mi ubicación")
                        .snippet("Mi ubicación")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .zIndex(-1f)
                    listMarker.add(mMap!!.addMarker(marker))

                    binding.recycleubicaciones.apply {
                        adapter = LocationAdapter(listItems,requireContext(),object:ClickAdapter{
                            override fun result(location: LocationEntity, position: Int) {
                                if(positionTemp!=-1){
                                    val markerold = listMarker[positionTemp]
                                    markerold.setIcon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_GREEN
                                        )
                                    )
                                    markerold.zIndex = -1f
                                    markerold.hideInfoWindow()
                                }
                                val marker = listMarker[position]
                                marker.zIndex = 1f
                                marker.setIcon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_RED
                                    )
                                )

                                positionTemp = position
                                marker.showInfoWindow()
                                moveCamera(marker.position)
                                animate(marker)
                            }
                        })
                    }

                }
                if(listMarker.isNotEmpty()){
                    val marker = listMarker[0]
                    moveCamera(marker.position)
                }


            }
            .addOnFailureListener { exception ->

            }
    }

    private fun setpRecycler(){
        binding.recycleubicaciones.setHasFixedSize(true)
        binding.recycleubicaciones.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun animate(marker: Marker) {
        val handler = Handler()
        val startTime = SystemClock.uptimeMillis()
        val duration: Long = 1500
        val proj = mMap!!.projection
        val markerLatLng = marker.position
        val startPoint = proj.toScreenLocation(markerLatLng)
        startPoint.offset(0, -100)
        val startLatLng = proj.fromScreenLocation(startPoint)
        val interpolator: Interpolator = BounceInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - startTime
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude
                val lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)
                if (t < 1.0) {
                    handler.postDelayed(this, 16)
                }
            }
        })
    }
}
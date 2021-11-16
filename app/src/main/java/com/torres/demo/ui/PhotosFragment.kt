package com.torres.demo.ui

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.torres.demo.R
import com.torres.demo.adapter.PhotosAdapter
import com.torres.demo.databinding.FragmentPhotosBinding
import com.torres.demo.utilities.Constants
import com.torres.demo.utilities.FirebaseUtils
import com.torres.demo.utilities.Utils
import com.torres.demo.utilities.extension.showGenericAlertDialog
import java.lang.Exception

class PhotosFragment : Fragment() {

    private var latestTmpUri: Uri? = null
    private lateinit var binding: FragmentPhotosBinding

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                saveImge(uri.toString())
            }
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                saveImge(uri.toString())
            }catch (e: Exception){
                requireContext().showGenericAlertDialog("No se pudo obtener acceso a la imagen")
            }
        }
    }

    private lateinit var adapterPhotos:PhotosAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentPhotosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupGetData()
        binding.fab.setOnClickListener {
            changeImage()
        }
    }

    fun saveImge(img:String){
        val photo = hashMapOf(
            "photo" to img
        )

        FirebaseUtils().fireStoreDatabase.collection("photos")
            .add(photo)
            .addOnSuccessListener { documentReference ->

            }
            .addOnFailureListener { e ->

            }
        adapterPhotos.addPhoto(img)

    }

    private fun changeImage() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    takeImage()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    selectImageFromGallery()
                }
            }
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Seleccione una opción")
            .setPositiveButton(getString(R.string.camera), dialogClickListener)
            .setNegativeButton(getString(R.string.imagengaleria), dialogClickListener).show()
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getTmpFileUri(): Uri {
        val tmpFile = Utils.createImageFile(Utils.GenerateUUID()+".jpeg")
        return FileProvider.getUriForFile(requireContext(),"com.torres.demo.fileprovider", tmpFile!!)
    }

    private fun setupRecycler(){
        binding.recyclerPhotos.setHasFixedSize(true)
        binding.recyclerPhotos.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
    }

    private fun setupGetData(){
        val listItems = ArrayList<String>()
        FirebaseUtils().fireStoreDatabase.collection("photos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    listItems.add(document.data.values.toString())
                }
                adapterPhotos = PhotosAdapter(listItems,requireContext())
                binding.recyclerPhotos.adapter = adapterPhotos


            }
            .addOnFailureListener { exception ->

            }
    }
}
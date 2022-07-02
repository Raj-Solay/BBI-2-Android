package com.bbi.bizbulls

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.databinding.ActivityAgreementsStatusBinding
import com.bbi.bizbulls.media.Adapter
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.widget.divider.Api21ItemDivider
import com.yanzhenjie.album.widget.divider.Divider


class AgreementsStatusActivity : AppCompatActivity() {
    var binding: ActivityAgreementsStatusBinding? = null
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    private var mAdapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementsStatusBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding?.recyclerViewFranchisee?.layoutManager = GridLayoutManager(this, 3)
        val divider: Divider = Api21ItemDivider(Color.TRANSPARENT, 10, 10)
        binding?.recyclerViewFranchisee?.addItemDecoration(divider)
        binding?.recyclerViewFranchisee?.adapter = mAdapter

        binding?.llUploadFranchiseeAgreement?.setOnClickListener {
            Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(5)
                .checkedList(mAlbumFiles)
//                .filterSize() // Filter the file size.
//                .filterMimeType() // Filter file format.
//                .afterFilterVisibility() // Show the filtered files, but they are not available.
                .onResult { result ->
                    mAlbumFiles = result
                    mAdapter?.notifyDataSetChanged(mAlbumFiles)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }

    }
}
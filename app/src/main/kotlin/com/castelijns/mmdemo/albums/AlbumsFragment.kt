package com.castelijns.mmdemo.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.app.BaseListFragment
import com.castelijns.mmdemo.models.Album
import kotlinx.android.synthetic.main.fragment_albums.*
import java.util.*

class AlbumsFragment : BaseListFragment(), AlbumsContract.View {

    private lateinit var presenter: AlbumsPresenter

    private lateinit var adapter: AlbumsAdapter
    private var albums: MutableList<Album>? = null
    private var itemClickListener: ItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = AlbumsPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albums = mutableListOf()
        adapter = AlbumsAdapter(albums!!)
        adapter.setItemClickListener(itemClickListener!!)
        rv_albums.layoutManager = LinearLayoutManager(context)
        rv_albums.setHasFixedSize(true)
        rv_albums.adapter = adapter

        presenter.start()
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }

    fun setShowPhotosClickedListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    override fun showAlbums(albums: List<Album>) {
        this.albums!!.addAll(albums)
        adapter.notifyDataSetChanged()
    }

    override fun showAlbumCount(count: Int) {
        setHeaderText(getString(R.string.header_albums, count))
    }
}

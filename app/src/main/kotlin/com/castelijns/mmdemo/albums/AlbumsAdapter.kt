package com.castelijns.mmdemo.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.models.Album
import kotlinx.android.synthetic.main.row_album.view.*

class AlbumsAdapter(
        private val dataSet: List<Album>
) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private var itemClickListener: ItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = dataSet[position]

        holder.itemView.apply {
            tv_title.text = album.title
            tv_username.text = album.userName
            btn_show_photos.setOnClickListener {
                if (itemClickListener != null) {
                    itemClickListener!!.onShowPhotosClicked(dataSet[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

}

interface ItemClickListener {
    fun onShowPhotosClicked(album: Album)
}

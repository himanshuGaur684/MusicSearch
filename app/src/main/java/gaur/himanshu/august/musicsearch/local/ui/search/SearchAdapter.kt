package gaur.himanshu.august.musicsearch.local.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView
import gaur.himanshu.august.musicsearch.BR
import gaur.himanshu.august.musicsearch.databinding.ListItemBinding
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {


    private var listItem = listOf<MusicDetail>()

    fun setContentList(list: List<MusicDetail>) {
        this.listItem = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(BR.music,listItem[position])

    }

    override fun getItemCount(): Int {
        return this.listItem.size
    }
}
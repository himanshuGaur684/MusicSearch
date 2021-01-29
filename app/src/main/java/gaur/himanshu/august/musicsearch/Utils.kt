package gaur.himanshu.august.musicsearch

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bindImage")
fun bindImage(view: ImageView, url: String) {
    if (url.isEmpty()) {
        view.setImageResource(R.drawable.ic_error)
    } else {
        Glide.with(view).load(url).placeholder(R.drawable.ic_error).error(R.drawable.ic_error).into(view)
    }
}
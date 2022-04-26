package id.dipoengoro.devbyte.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("goneIfNotNull")
fun ProgressBar.setGoneIfNotNull(data: Any?) {
    visibility = if (data != null) View.GONE else View.VISIBLE
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) = Glide.with(context).load(url).into(this)
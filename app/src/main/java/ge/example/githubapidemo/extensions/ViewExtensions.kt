package ge.example.githubapidemo.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import ge.example.githubapidemo.R

fun ImageView.setNetworkImage(
    cover: String?,
    fallback: Int = R.drawable.ic_launcher_foreground,
    placeHolder: Int = R.drawable.ic_launcher_foreground,
    errorImage: Int = R.drawable.ic_launcher_foreground
) {

    if (cover != null) {
        Glide.with(context)
            .load(cover)
            .error(errorImage)
            .placeholder(placeHolder)
            .fallback(fallback)
            .into(this)
    } else
        this.setImageResource(R.mipmap.ic_launcher)

}
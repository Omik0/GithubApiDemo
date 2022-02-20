package ge.example.githubapidemo.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import ge.example.githubapidemo.R

fun View.showSnack(message: String, color: Int) {
    val snackBar: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    val snackBarView = snackBar.view
    snackBarView.backgroundTintList = ContextCompat.getColorStateList(this.context, color)
    snackBar.show()
}


fun View.visible(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.invisible(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun ViewGroup.hideAll() {
    this.children.forEach {
        if (it.tag == "error")
            it.visible()
        else
            it.gone()
    }
}

fun ImageView.setDrawable(icon: Int) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context,
            icon
        )
    )
}


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
package minia.chatapp.adapters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import minia.chatapp.R
import minia.chatapp.models.Message


class MessageAdapter(val context: Context, val list: ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(val b: View) : RecyclerView.ViewHolder(b){
        val name = b.findViewById<TextView>(R.id.name)
        val time = b.findViewById<TextView>(R.id.time)
        val card = b.findViewById<CardView>(R.id.card)
        val triangle = b.findViewById<ImageView>(R.id.triangle)
        val message = b.findViewById<TextView>(R.id.message)
        val avatar = b.findViewById<CircleImageView>(R.id.avatar)
        val fileImage = b.findViewById<ImageView>(R.id.imageView)

    }

    var isPlaying = false

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val item = list[position]

        val p = holder.b.context.getSharedPreferences("myDetails", MODE_PRIVATE)
        val id = p.getString("id", "")

        if (item.type.equals("fromUserToEmployee")){
            holder.apply {

                val df = SimpleDateFormat("MMM d, hh:mm a", Locale("en"))
                time.text = df.format(Date(item.createdAt))

                if(item.content == "photo"){
                    fileImage.visibility = View.VISIBLE
                    message.visibility = View.GONE
                    Picasso.with(context).load(item.imageUrl).into(fileImage)
                }else{
                    message.text = item.content
                }


                val ava = when {
                    !item.user!!.image.isNullOrBlank() -> item.user!!.image
                    else -> "https://www.datastax.com/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" // Sorry
                }
                Picasso.with(holder.b.context).load(R.drawable.default_user_photo).into(avatar)

                name.text = item.user!!.name

                if (item.user!!.getKey().equals(id)) {
                    card.setCardBackgroundColor(Color.parseColor("#AEAEAE"))
                    triangle.setImageDrawable(
                            ContextCompat.getDrawable(
                                    holder.b.context,
                                    R.drawable.triangle_grey
                            )
                    )
                    ViewCompat.setLayoutDirection(
                            holder.b, LayoutDirection.LTR
                    )
                } else {
                    triangle.setImageDrawable(
                            ContextCompat.getDrawable(
                                    holder.b.context,
                                    R.drawable.triangle_purple
                            )
                    )
                    card.setCardBackgroundColor(
                            ContextCompat.getColor(
                                    holder.b.context,
                                    R.color.colorPrimary
                            )
                    )
                    ViewCompat.setLayoutDirection(
                            holder.b, LayoutDirection.RTL
                    )
                }
            }
        }else{
            holder.apply {
                val df = SimpleDateFormat("MMM d, hh:mm a", Locale("en"))
                time.text = df.format(Date(item.createdAt ))

                if(item.content == "photo"){
                    fileImage.visibility = View.VISIBLE
                    message.visibility = View.GONE
                    Picasso.with(context).load(item.imageUrl).into(fileImage)
                }else{
                    message.text = item.content
                }

                val ava = when {
                    !item.employee!!.image.isNullOrBlank() -> item.employee!!.image
                    else -> "https://www.datastax.com/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" // Sorry
                }
                Picasso.with(holder.b.context).load(R.drawable.default_user_photo).into(avatar)

                name.text = item.employee!!.name

                if (item.employee!!.getKey().equals(id)) {
                    card.setCardBackgroundColor(Color.parseColor("#AEAEAE"))
                    triangle.setImageDrawable(
                            ContextCompat.getDrawable(
                                    holder.b.context,
                                    R.drawable.triangle_grey
                            )
                    )
                    ViewCompat.setLayoutDirection(
                            holder.b, LayoutDirection.LTR
                    )
                } else {
                    triangle.setImageDrawable(
                            ContextCompat.getDrawable(
                                    holder.b.context,
                                    R.drawable.triangle_purple
                            )
                    )
                    card.setCardBackgroundColor(
                            ContextCompat.getColor(
                                    holder.b.context,
                                    R.color.colorPrimary
                            )
                    )
                    ViewCompat.setLayoutDirection(
                            holder.b, LayoutDirection.RTL
                    )
                }
            }
        }
    }
}

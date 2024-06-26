import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emil.network.Offer
import com.emil.ui.R
import java.text.NumberFormat
import java.util.Locale

class OfferAdapter : ListAdapter<Offer, OfferAdapter.ViewHolder>(OfferDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_offers, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOffer = getItem(position)
        holder.bind(currentOffer)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.iv_image)
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val town: TextView = itemView.findViewById(R.id.tv_town)
        private val price: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(offer: Offer) {
            image.setImageResource(when (offer.id){
                1 -> R.drawable.image1
                2 -> R.drawable.image2
                3 -> R.drawable.image3
                else ->  R.drawable.image1
            })
            title.text = offer.title
            town.text = offer.town
            price.text = "от ${separateRanks(offer.price.value)} ₽"
        }
    }

    class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }



}
private fun separateRanks (num:Int) : String? {
    return  NumberFormat.getNumberInstance(Locale.getDefault()).format(num)

}

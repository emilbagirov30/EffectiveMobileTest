import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emil.network.Ticket
import com.emil.ui.R
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.time.Duration

class TicketsAdapter : ListAdapter<Ticket, TicketsAdapter.ViewHolder>(TicketDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_tickets, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = getItem(position)
        holder.bind(ticket)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_price)
        private val departureDateTextView: TextView = itemView.findViewById(R.id.tv_departure_date)
        private val departureAirportTextView: TextView = itemView.findViewById(R.id.tv_departure_airport)
        private val arrivalDateTextView: TextView = itemView.findViewById(R.id.tv_arrival_date)
        private val arrivalAirportTextView: TextView = itemView.findViewById(R.id.tv_arrival_airpor)
        private val wayTimeTextView: TextView = itemView.findViewById(R.id.tv_way_time)
        private val hasTransferTextView: TextView = itemView.findViewById(R.id.tv_has_transfer)
        private val badgeTextView: TextView = itemView.findViewById(R.id.tv_badge_text)
        private val badgeCardView: CardView = itemView.findViewById(R.id.cv_badge)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(ticket: Ticket) {
            priceTextView.text = "${separateRanks(ticket.price.value)} ₽"
            departureDateTextView.text = formatTime(ticket.departure.date)
            departureAirportTextView.text = ticket.departure.airport
            arrivalDateTextView.text = formatTime(ticket.arrival.date)
            arrivalAirportTextView.text = ticket.arrival.airport
            wayTimeTextView.text = calculateWayTime(ticket.departure.date, ticket.arrival.date)
            hasTransferTextView.text = if (ticket.hasTransfer) "" else " / без пересадок"
            if (ticket.badge != null) {
                badgeTextView.text = ticket.badge
                badgeCardView.visibility = View.VISIBLE
            } else {
                badgeCardView.visibility = View.GONE
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatTime(dateTime: String): String {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = DateTimeFormatter.ofPattern("HH:mm")
            return LocalDateTime.parse(dateTime, inputFormat).format(outputFormat)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun calculateWayTime(departure: String, arrival: String): String {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val departureTime = LocalDateTime.parse(departure, inputFormat)
            val arrivalTime = LocalDateTime.parse(arrival, inputFormat)

            val hours = ChronoUnit.HOURS.between(departureTime, arrivalTime)
            val minutes = ChronoUnit.MINUTES.between(departureTime, arrivalTime) % 60

            return "${hours}ч ${minutes}м в пути"
        }

        private fun separateRanks(num: Int): String {
            return NumberFormat.getNumberInstance(Locale.getDefault()).format(num)
        }
    }

    class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }
}

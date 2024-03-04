package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding



open class CrimeHolderBase(
    protected val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

// NormalCrimeHolder class
class NormalCrimeHolder(
    binding: ListItemCrimeBinding
) : CrimeHolderBase(binding) {
    override fun bind(crime: Crime) {
        super.bind(crime)
        val contactPoliceButton: Button = binding.root.findViewById(R.id.contact_police_button)
        if (!crime.requiresPolice) {
            contactPoliceButton.visibility = View.GONE
        } else {
            contactPoliceButton.visibility = View.VISIBLE
        }
    }
}

// SeriousCrimeHolder class
class SeriousCrimeHolder(
    binding: ListItemCrimeBinding
) : CrimeHolderBase(binding) {
    override fun bind(crime: Crime) {
        super.bind(crime)
        val contactPoliceButton: Button = binding.root.findViewById(R.id.contact_police_button)
        if (!crime.requiresPolice) {
            contactPoliceButton.visibility = View.GONE
        } else {
            contactPoliceButton.visibility = View.VISIBLE
        }
    }
}


class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val firstViewType = 0
    val secondViewType = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)

        return when (viewType) {
            firstViewType -> NormalCrimeHolder(binding)
            secondViewType -> SeriousCrimeHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }

    }

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]
        return if (crime.requiresPolice == false) {
            firstViewType
        } else {
            secondViewType
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is NormalCrimeHolder -> holder.bind(crime)
            is SeriousCrimeHolder -> holder.bind(crime)
            else -> throw IllegalArgumentException("Invalid view holder type")
        }

    }
    override fun getItemCount() = crimes.size
}

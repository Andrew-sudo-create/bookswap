package com.example.bookswap.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookswap.R
import com.example.bookswap.databinding.ItemBookBinding
import com.example.bookswap.models.Book

class BookAdapter(
    private var books: List<Book>,
    private val onBookClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(book: Book) {
            binding.book = book
            
            val bgRes = when (book.condition) {
                "Like New" -> R.drawable.tag_bg_like_new
                "Good" -> R.drawable.tag_bg_good
                "Fair" -> R.drawable.tag_bg_fair
                else -> R.drawable.tag_bg_poor
            }
            binding.tvCondition.setBackgroundResource(bgRes)

            binding.root.setOnClickListener { onBookClick(book) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
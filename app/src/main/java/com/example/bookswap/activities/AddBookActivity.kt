package com.example.bookswap.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookswap.data.BookRepository
import com.example.bookswap.databinding.ActivityAddBookBinding
import com.example.bookswap.models.Book

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val conditions = arrayOf("Like New", "Good", "Fair", "Poor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, conditions)
        binding.autoCompleteCondition.setAdapter(adapter)

        binding.btnSubmit.setOnClickListener {
            saveBook()
        }
    }

    private fun saveBook() {
        val title = binding.etTitle.text.toString()
        val author = binding.etAuthor.text.toString()
        val edition = binding.etEdition.text.toString().ifBlank { "N/A" }
        val isbn = binding.etIsbn.text.toString().ifBlank { "N/A" }
        val priceStr = binding.etPrice.text.toString()
        val condition = binding.autoCompleteCondition.text.toString()
        val category = binding.etCategory.text.toString().ifBlank { "General" }
        val description = binding.etDescription.text.toString().ifBlank { "No description provided." }

        if (title.isBlank() || author.isBlank() || priceStr.isBlank() || condition.isBlank()) {
            Toast.makeText(this, "Please fill in all required fields (*)", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceStr.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show()
            return
        }

        val newId = (BookRepository.books.maxOfOrNull { it.id } ?: 0) + 1
        val newBook = Book(
            id = newId,
            title = title,
            author = author,
            edition = edition,
            isbn = isbn,
            price = price,
            condition = condition,
            category = category,
            description = description,
            sellerName = "Alex Thompson", // Dummy current user name
            sellerEmail = "alex.thompson@university.edu" // Dummy current user email
        )

        BookRepository.addBook(newBook)
        Toast.makeText(this, "Textbook listed successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
package com.example.bookswap.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookswap.activities.BookDetailsActivity
import com.example.bookswap.adapters.BookAdapter
import com.example.bookswap.databinding.FragmentBrowseBinding
import com.example.bookswap.models.Book

class BrowseFragment : Fragment() {
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BookAdapter
    private var allBooks = listOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupDummyData()
        setupRecyclerView()
        setupSearch()
    }

    private fun setupDummyData() {
        allBooks = listOf(
            Book(
                1, "Introduction to Algorithms", "Thomas H. Cormen", "3rd Edition", 
                "978-0262033848", 45.00, "Good", "Computer Science",
                "Comprehensive guide to algorithms. Some highlighting but in excellent readable condition.",
                "Sarah Johnson", "sarah.j@university.edu"
            ),
            Book(
                2, "Calculus: Early Transcendentals", "James Stewart", "8th Edition", 
                "978-1285741550", 65.00, "Like New", "Mathematics",
                "No markings or highlights. Basically brand new.",
                "Mike Chen", "mike.c@university.edu"
            ),
            Book(
                3, "Campbell Biology", "Jane B. Reece", "10th Edition", 
                "978-0321775658", 80.00, "Good", "Biology",
                "Comes with access code (unused).",
                "Jessica Smith", "jsmith@university.edu"
            ),
            Book(
                4, "Principles of Economics", "N. Gregory Mankiw", "7th Edition", 
                "978-1285165875", 55.00, "Fair", "Economics",
                "Cover has some wear, but pages are intact.",
                "David Lee", "dlee@university.edu"
            ),
            Book(
                5, "Physics for Scientists and Engineers", "Raymond A. Serway", "9th Edition", 
                "978-1133947271", 90.00, "Like New", "Physics",
                "Pristine condition.",
                "Amanda White", "awhite@university.edu"
            )
        )
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter(allBooks) { book ->
            val intent = Intent(requireContext(), BookDetailsActivity::class.java).apply {
                putExtra("BOOK_EXTRA", book)
            }
            startActivity(intent)
        }
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                val filtered = allBooks.filter {
                    it.title.lowercase().contains(query) ||
                    it.author.lowercase().contains(query) ||
                    it.isbn.contains(query)
                }
                adapter.updateBooks(filtered)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.bookswap.data

import com.example.bookswap.models.Book

object BookRepository {
    val books = ArrayList<Book>()

    init {
        books.add(Book(1, "Introduction to Algorithms", "Thomas H. Cormen", "3rd Edition", "978-0262033848", 45.00, "Good", "Computer Science", "Comprehensive guide to algorithms. Some highlighting but in excellent readable condition.", "Sarah Johnson", "sarah.j@university.edu"))
        books.add(Book(2, "Calculus: Early Transcendentals", "James Stewart", "8th Edition", "978-1285741550", 65.00, "Like New", "Mathematics", "No markings or highlights. Basically brand new.", "Mike Chen", "mike.c@university.edu"))
        books.add(Book(3, "Campbell Biology", "Jane B. Reece", "10th Edition", "978-0321775658", 80.00, "Good", "Biology", "Comes with access code (unused).", "Jessica Smith", "jsmith@university.edu"))
        books.add(Book(4, "Principles of Economics", "N. Gregory Mankiw", "7th Edition", "978-1285165875", 55.00, "Fair", "Economics", "Cover has some wear, but pages are intact.", "David Lee", "dlee@university.edu"))
        books.add(Book(5, "Physics for Scientists and Engineers", "Raymond A. Serway", "9th Edition", "978-1133947271", 90.00, "Like New", "Physics", "Pristine condition.", "Amanda White", "awhite@university.edu"))
    }

    fun searchBooks(query: String): List<Book> {
        val q = query.lowercase()
        return books.filter {
            it.title.lowercase().contains(q) ||
            it.author.lowercase().contains(q) ||
            it.isbn.contains(q)
        }
    }

    fun addBook(book: Book) {
        // Add to the beginning of the list so it shows up at the top
        books.add(0, book)
    }
}
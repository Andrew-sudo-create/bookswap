package com.example.bookswap.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookswap.R
import com.example.bookswap.databinding.ActivityBookDetailsBinding
import com.example.bookswap.models.Book

class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding
    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        book = intent.getSerializableExtra("BOOK_EXTRA") as? Book

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        populateBookDetails()

        binding.btnContactSeller.setOnClickListener {
            showInquiryDialog()
        }
    }

    private fun populateBookDetails() {
        book?.let { b ->
            binding.tvTitle.text = b.title
            binding.tvPrice.text = String.format("$%.2f", b.price)
            binding.tvCondition.text = b.condition
            binding.tvCategory.text = b.category
            binding.tvAuthor.text = b.author
            binding.tvEdition.text = b.edition
            binding.tvIsbn.text = b.isbn
            binding.tvDescription.text = b.description
            binding.tvSellerName.text = b.sellerName
            binding.tvSellerEmail.text = b.sellerEmail

            val bgRes = when (b.condition) {
                "Like New" -> R.drawable.tag_bg_like_new
                "Good" -> R.drawable.tag_bg_good
                "Fair" -> R.drawable.tag_bg_fair
                else -> R.drawable.tag_bg_poor
            }
            binding.tvCondition.setBackgroundResource(bgRes)
        }
    }

    private fun showInquiryDialog() {
        book?.let { b ->
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_inquiry)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.9).toInt(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val tvTitle = dialog.findViewById<TextView>(R.id.tv_dialog_title)
            val tvSubtitle = dialog.findViewById<TextView>(R.id.tv_dialog_subtitle)
            val etMessage = dialog.findViewById<EditText>(R.id.et_message)
            val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
            val btnSend = dialog.findViewById<Button>(R.id.btn_send)

            tvTitle.text = getString(R.string.send_inquiry, b.sellerName)
            tvSubtitle.text = "Your message will be sent to the seller's email: ${b.sellerEmail}"

            etMessage.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s.isNullOrBlank()) {
                        btnSend.backgroundTintList = getColorStateList(android.R.color.darker_gray)
                        btnSend.setTextColor(getColor(R.color.text_secondary))
                    } else {
                        btnSend.backgroundTintList = getColorStateList(R.color.primary_blue)
                        btnSend.setTextColor(getColor(R.color.white))
                    }
                }
            })

            btnCancel.setOnClickListener { dialog.dismiss() }
            btnSend.setOnClickListener {
                if (!etMessage.text.isNullOrBlank()) {
                    Toast.makeText(this, "Message sent to ${b.sellerName}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}
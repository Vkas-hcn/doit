package com.todo.tomato.ui.activitys

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.todo.tomato.R
import com.todo.tomato.databinding.ActivityT3Binding
import com.todo.tomato.tools.T0App.Companion.t0Name
import com.todo.tomato.tools.distinctId


class T3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityT3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityT3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            t0.setOnClickListener { finish() }
            t3.setOnClickListener {
                val shareP = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Share!")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=$t0Name"
                    )
                }
                startActivity(Intent.createChooser(shareP, "Share"))
            }
            t4.setOnClickListener {
                val url = "https://play.google.com/store/apps/details?id=$t0Name"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            t5.setOnClickListener { startActivity(Intent(this@T3Activity, T4Activity::class.java)) }

            userId.setOnLongClickListener {
                val clipboard: ClipboardManager =
                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", distinctId)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    applicationContext,
                    "Text copied to clipboard",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            userId.text = "User ID:$distinctId"
        }
    }
}
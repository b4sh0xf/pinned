package hc.b4sh0xf.talk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hc.b4sh0xf.talk.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadSavedConfig()

        binding.btnConnect.setOnClickListener {
            handleConnect()
        }
    }

    private fun loadSavedConfig() {
        val prefs = getSharedPreferences("api_config", MODE_PRIVATE)
        val savedIp = prefs.getString("server_ip", "")
        val savedPort = prefs.getString("server_port", "")

        if (!savedIp.isNullOrEmpty()) {
            binding.serverIp.setText(savedIp)
        }
        if (!savedPort.isNullOrEmpty()) {
            binding.serverPort.setText(savedPort)
        }
    }

    private fun handleConnect() {
        val ip = binding.serverIp.text.toString().trim()
        val port = binding.serverPort.text.toString().trim()

        if (ip.isEmpty()) {
            Toast.makeText(this, "server IP cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val finalPort = if (port.isEmpty()) "80" else port

        // Save config
        val prefs = getSharedPreferences("api_config", MODE_PRIVATE)
        prefs.edit().apply {
            putString("server_ip", ip)
            putString("server_port", finalPort)
            apply()
        }

        // Update ApiConfig (IP -> pinned.hc)
        ApiConfig.setServer(ip, finalPort)

        Toast.makeText(this, "pinned.hc -> $ip:$finalPort", Toast.LENGTH_SHORT).show()

        // Navigate to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

package hc.b4sh0xf.talk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hc.b4sh0xf.talk.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GenerateToken(this) {
            initApp()
        }.showTokenDialog()
    }

    private fun initApp() {
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.register.setOnClickListener {
            handleAuth(isLogin = false)
        }

        binding.login.setOnClickListener {
            handleAuth(isLogin = true)
        }
    }

    private fun handleAuth(isLogin: Boolean) {
        val user = binding.username.text.toString().trim()
        val pass = binding.password.text.toString().trim()

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "user and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val service = RetroFitClient.createApiService()
        val request = AuthRequest(user, pass)

        val call = if (isLogin) service.login(request) else service.register(request)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                    intent.putExtra("username", user)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "internal server error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
package hc.b4sh0xf.talk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hc.b4sh0xf.talk.databinding.ActivityDashboardBinding
import hc.b4sh0xf.talk.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("username") ?: "User"
        binding.welcome.text = "welcome, ${username}!"

        fetchLastLogin(username)

    }

    private fun fetchLastLogin(username: String) {
        val service = RetroFitClient.createApiService()

        service.getLastLogin(username).enqueue(object : Callback<LastLoginResponse> {
            override fun onResponse(call: Call<LastLoginResponse>, response: Response<LastLoginResponse>) {
                if (response.isSuccessful) {
                    //binding.last.text = response.body()?.timestamp
                }
            }

            override fun onFailure(call: Call<LastLoginResponse>, t: Throwable) {
                //binding.last.text = "Error loading timestamp"
            }
        })
    }
}
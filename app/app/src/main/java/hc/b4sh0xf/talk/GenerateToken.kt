package hc.b4sh0xf.talk

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import kotlin.experimental.xor

class GenerateToken(private val context: Context, private val onVerified: () -> Unit) {

    fun showTokenDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("internal usage only.")
        builder.setMessage("enter the token sent to your email:")
        builder.setCancelable(false)

        val input = EditText(context)
        builder.setView(input)

        builder.setPositiveButton("Verify") { _, _ ->
            if (v_chk_01(input.text.toString())) {
                onVerified()
            } else {
                (context as android.app.Activity).finish()
            }
        }
        builder.show()
    }

    private fun v_chk_01(input: String): Boolean {
        if (input.length != 27) return false

        val target = byteArrayOf(
            0x4a, 0x23, 0x67, 0x4c, 0x43, 0x24, 0x7a, 0x73,
            0x47, 0x6e, 0x64, 0x65, 0x42, 0x3d, 0x7b, 0x78,
            0x68, 0x7b, 0x4a, 0x5a, 0x62, 0x22, 0x21, 0x42,
            0x49, 0x73, 0x32
        )

        val inputBytes = input.toByteArray()

        try {
            for (i in inputBytes.indices) {
                val processed = (inputBytes[i].toInt() xor i).toByte() xor 0x13.toByte()
                if (processed != target[i]) return false
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
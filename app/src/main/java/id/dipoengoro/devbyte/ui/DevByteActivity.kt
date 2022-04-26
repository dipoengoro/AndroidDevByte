package id.dipoengoro.devbyte.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.dipoengoro.devbyte.R
import id.dipoengoro.devbyte.databinding.ActivityDevbyteBinding

class DevByteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityDevbyteBinding>(this, R.layout.activity_devbyte)
    }
}
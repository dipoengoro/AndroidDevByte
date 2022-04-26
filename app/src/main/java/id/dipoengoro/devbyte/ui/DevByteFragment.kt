package id.dipoengoro.devbyte.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.dipoengoro.devbyte.R
import id.dipoengoro.devbyte.databinding.DevbyteItemBinding
import id.dipoengoro.devbyte.databinding.FragmentDevbyteBinding
import id.dipoengoro.devbyte.domain.Video
import id.dipoengoro.devbyte.viewmodel.DevByteViewModel

class DevByteFragment : Fragment() {

    private val devByteViewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            DevByteViewModel.Factory(activity.application)
        )[DevByteViewModel::class.java]
    }

    private var viewModelAdapter: DevByteAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        devByteViewModel.playlist.observe(viewLifecycleOwner) {
            viewModelAdapter?.videos = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDevbyteBinding>(
            inflater,
            R.layout.fragment_devbyte,
            container,
            false
        )
        viewModelAdapter = DevByteAdapter(VideoClick {
            val packageManager = context?.packageManager ?: return@VideoClick
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if (intent.resolveActivity(packageManager) == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }
            startActivity(intent)
        })

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = devByteViewModel
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = viewModelAdapter
            }
        }

        return binding.root
    }

    private val Video.launchUri: Uri
        get() = Uri.parse("vnd.youtube:" + Uri.parse(url).getQueryParameter("v"))
}

class VideoClick(val block: (Video) -> Unit) {
    fun onClick(video: Video) = block(video)
}

class DevByteAdapter(private val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    var videos: List<Video> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder =
        DevByteViewHolder.from(parent)

    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) =
        holder.binding.run {
            video = videos[position]
            videoCallback = callback
        }

    override fun getItemCount(): Int = videos.size
}

class DevByteViewHolder(val binding: DevbyteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.devbyte_item

        fun from(parent: ViewGroup): DevByteViewHolder =
            DevByteViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    LAYOUT,
                    parent,
                    false
                )
            )

    }
}

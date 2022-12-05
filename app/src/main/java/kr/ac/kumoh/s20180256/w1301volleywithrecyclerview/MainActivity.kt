package kr.ac.kumoh.s20180256.w1301volleywithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20180256.w1301volleywithrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private val songAdapter = SongAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SongViewModel::class.java]

        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            // 시험에 applicationContext 없으면 application이 없으면 틀린거다.
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            binding.list.adapter = songAdapter
        }
        model.list.observe(this){
            songAdapter.notifyItemRangeInserted(0,
                songAdapter.itemCount)
        }
            /*
            binding.list.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
                adapter = songAdapter
            }


            model.list.observe(this) {
                // 좀더 구체적인 이벤트를 사용하라고 warning 나와서 변경함
                //songAdapter.notifyDataSetChanged()
                //Log.i("size", "${model.list.value?.size ?: 0}")

                // Changed가 아니라 Inserted
                songAdapter.notifyItemRangeInserted(0,
                    model.list.value?.size ?: 0)
            }

             */
            model.requestSong()
    }


    inner class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>(){
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val txTitle: TextView = itemView.findViewById<TextView>(R.id.text1)
            val txSinger : TextView = itemView.findViewById<TextView>(R.id.text2)
            //val txText : Textview = itemView.findyViewById(android.R.id.text1) as TextView
            //실습때 여기에 데이터들이 다 들어가게 된다.
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            //android.R.layout 을 itme_idol.xml의 레이아웃만 고쳐서 쓰면된다.
            val view = layoutInflater.inflate(R.layout.item_song,
            parent,
            false)
            return ViewHolder(view)
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //ViewHolder가 6줄이면 6개를 마찬가지로 세팅해줘야한다.
            holder.txTitle.text = model.list.value?.get(position)?.title
            holder.txSinger.text = model.list.value?.get(position)?.singer
        }

        override fun getItemCount() = model.list.value?.size ?: 0
    }

}

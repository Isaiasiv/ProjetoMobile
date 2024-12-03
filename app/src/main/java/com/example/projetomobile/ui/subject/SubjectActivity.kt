package com.example.projetomobile.ui.subject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.ui.home.tasks.DataTask
import com.example.projetomobile.ui.home.tasks.TaskAdapter

class SubjectActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataTask>

    lateinit var atividades:Array<String>
    lateinit var materias:Array<String>
    lateinit var datas:Array<String>
    



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContentView(R.layout.activity_subject)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        atividades = arrayOf(
            "Atividade 1",
            "Atividade 2",
            "Atividade 3",
            "Atividade 4",
        )

        materias = arrayOf(
            "matematica",
            "ciencias",
            "geografia",
            "matematica"
        )

        datas = arrayOf(
            "13-07-2024",
            "25-12-2024",
            "01-01-2025",
            "02-05-2025"
        )

        recyclerView = findViewById(R.id.recycleViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataTask>()
        getDataTask()


    }
    private fun getDataTask(){
        for (i in atividades.indices){
            val dataTask = DataTask(materias[i].toString(),atividades[i].toString(),datas[i].toString())
            dataList.add(dataTask)
        }

        recyclerView.adapter = TaskAdapter(dataList)
    }


}
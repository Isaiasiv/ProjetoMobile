package com.example.projetomobile.ui.welcome

import android.os.Bundle
<<<<<<< HEAD
import android.view.View
=======
>>>>>>> a68ceb5 (teste)
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
<<<<<<< HEAD
            //atribuir um evento ao click do botão para ir a proxima tela
        }
    }

    fun onClickNext(view: View) {}
    //adicionar a estrutura do botão da seta
=======
        }
    }
>>>>>>> a68ceb5 (teste)
}
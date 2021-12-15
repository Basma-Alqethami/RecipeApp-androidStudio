package com.example.recipeapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var rvAdapter: RVAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var addButton: Button

    var list = ArrayList<recipeDataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllData()

        recyclerView = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(this, list)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton = findViewById(R.id.btnAdd)
        addButton.setOnClickListener {
            addData()
            rvMain.scrollToPosition(list.size - 1)
        }
    }

    fun getAllData() {
        val api = Client().getClient()?.create(API::class.java)

        api?.getData()?.enqueue(object : Callback<recipeData> {
            override fun onResponse(
                call: Call<recipeData>,
                response: Response<recipeData>
            ) {
                for (item in response.body()!!) {
                    Log.d("item", "$item")
                    val pk = item.pk
                    val title = item.title
                    val author = item.author
                    val instructions = item.instructions
                    val ingredients = item.ingredients
                    Log.d("eeee", "title: $title , author: $author, instructions: $instructions, ingredients: $ingredients")
                    list.add(recipeDataItem(pk, author, ingredients, instructions, title))
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<recipeData>, t: Throwable) {
                Log.d("error", "$t")
            }
        })
    }

    fun addData() {

        val alert = AlertDialog.Builder(this)
        alert.setTitle("Enter the information.")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val title_textbox = EditText(this)
        title_textbox.hint = "Title"
        layout.addView(title_textbox)

        val author_textbox = EditText(this)
        author_textbox.hint = "Author"
        layout.addView(author_textbox)

        val ingredients = EditText(this)
        ingredients.hint = "Ingredients"
        layout.addView(ingredients)

        val instructions = EditText(this)
        instructions.hint = "Instructions"
        layout.addView(instructions)

        alert.setView(layout)

        alert.setPositiveButton(
            "Save"
        ) { dialog, whichButton ->
            val title = title_textbox.text.toString()
            val author = author_textbox.text.toString()
            val instructions = instructions.text.toString()
            val ingredients = ingredients.text.toString()
            val pk = 0
            if (title.isNotEmpty() && author.isNotEmpty() && instructions.isNotEmpty() && ingredients.isNotEmpty()){
                Log.d("ee", "title: $title , author: $author, instructions: $instructions, ingredients: $ingredients")
                postData(pk, title, author, ingredients, instructions)
                Toast.makeText(this, "Saved Sucessfully", Toast.LENGTH_LONG).show()}
            else{
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_LONG).show()
            }
        }

        alert.setNegativeButton(
            "Cancel"
        ) { dialog, whichButton ->
            // what ever you want to do with No option.
        }

        alert.show()
    }

    fun postData(pk: Int, title: String, author: String, ingredients: String, instructions: String) {

        val api = Client().getClient()?.create(API::class.java)

        api?.postData(recipeDataItem(pk, author, ingredients, instructions, title))?.enqueue(object : Callback<recipeDataItem> {
            override fun onResponse(call: Call<recipeDataItem>, response: Response<recipeDataItem>) {
                getAllData()
                Log.d("yy", "title: $title , author: $author, instructions: $instructions, ingredients: $ingredients")
                Toast.makeText(applicationContext, "Add Sucessfully!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<recipeDataItem>, t: Throwable) {
                Log.d("error", "$t")
            }
        })
    }
}
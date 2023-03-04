package com.example.skincareshopapp.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryProductAdapter
    private lateinit var categoryList: ArrayList<CategoryProductModel>
    val urlGetData: String="http://192.168.1.11/android/get_categoty_product.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categoryList = ArrayList()
        listCategoryRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        categoryList.add(CategoryProductModel(1,"Mon an Han Quoc", true))
        categoryList.add(CategoryProductModel(2,"Mon an Viet Nam", true))
        println(categoryList)
        adapter = CategoryProductAdapter(this, categoryList)
        listCategoryRecyclerView.adapter = adapter
        GetData().execute(urlGetData)
    }
    inner class GetData() : AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg params: String?): String {
            return  getContentURl(params[0])

        }
        override fun onPostExecute(result:String?){
            super.onPostExecute(result)
            var jsonArray: JSONArray = JSONArray(result)

            var name:String = ""
            var id:Int
            var show:Boolean

            for(category in 0..jsonArray.length()-1){

                var objectCategory:JSONObject = jsonArray.getJSONObject(category)
                id = objectCategory.getInt("id_category_product")
                name = objectCategory.getString("name_category_product")
                show = objectCategory.getBoolean("show")
                categoryList.add(CategoryProductModel(id, name, show))

                listCategoryRecyclerView.adapter = adapter

            }

            Toast.makeText(applicationContext,result,Toast.LENGTH_LONG).show()
        }

    }
    private fun getContentURl(url : String?) : String{
        var content: StringBuilder = StringBuilder()
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try{
            do{
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line!=null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA",e.toString())
        }
        return content.toString()
    }


}
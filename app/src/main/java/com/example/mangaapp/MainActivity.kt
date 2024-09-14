package com.example.mangaapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.mangaapp.fragment.BrowseFragment
import com.example.mangaapp.fragment.HomeFragment
import com.example.mangaapp.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class MainActivity : AppCompatActivity() {

    var fragLogic : Fragment? = null
    var query = "empty"

    private var TAG = "MainActivity"



    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.appbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.btnSearch).actionView as SearchView
        val component = ComponentName(this, MainActivity::class.java)
        val searcableInfo = searchManager.getSearchableInfo(component)
        searchView.setSearchableInfo(searcableInfo)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.onActionViewCollapsed()

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return true


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firstLaunch = "first-launch"


        var fileTitle = File("data/user/0/com.example.mangaapp/files/lastReadTitle.txt")
        var fileId = File("data/user/0/com.example.mangaapp/files/lastReadId.txt")
        var fileImg = File("data/user/0/com.example.mangaapp/files/lastReadImg.txt")
        var fileCh = File("data/user/0/com.example.mangaapp/files/lastReadCh.txt")
        var fileChId = File("data/user/0/com.example.mangaapp/files/lastReadChId.txt")

        if (fileTitle.exists() && fileId.exists() && fileImg.exists() && fileCh.exists() && fileChId.exists()) {
            Toast.makeText(this,"Already exists", Toast.LENGTH_SHORT).show()
        } else {
            val posisi = 0
            openFileOutput("lastReadId.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(firstLaunch.toByteArray())
            }
            openFileOutput("lastReadTitle.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(firstLaunch.toByteArray())
            }
            openFileOutput("lastReadImg.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(firstLaunch.toByteArray())
            }
            openFileOutput("lastReadCh.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(firstLaunch.toByteArray())
            }

            openFileOutput("lastReadChId.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(firstLaunch.toByteArray())
            }

            openFileOutput("lastReadPosition.txt", Context.MODE_PRIVATE).use { fileOut ->
                fileOut?.write(posisi.toString().toByteArray())
            }

            Toast.makeText(this,"Making file", Toast.LENGTH_SHORT).show()
        }





        val actionToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(actionToolbar)
        getSupportActionBar()?.setTitle("Manga App")

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.MainFragment, HomeFragment(), "myFragment")
            }
        }

        val bottomNavbar = findViewById<BottomNavigationView>(R.id.ViewNavbar)

        bottomNavbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.btnHome -> gantiFragment(HomeFragment())
                R.id.btnBrowser -> gantiFragment(BrowseFragment())
                else -> {
                }
            }
            true
        }








    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if(Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)!!
            var fragmenLayout = supportFragmentManager.findFragmentById(R.id.MainFragment)



            if (fragmenLayout !is SearchFragment) {

                val bundle = Bundle()
                bundle.putString("query", query)


                val fragmen = SearchFragment()
                fragmen.arguments = bundle

                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.MainFragment, fragmen, "myFragment")
                    addToBackStack(null)

                }




            } else if (fragmenLayout is SearchFragment) {
                val bundle = Bundle()
                bundle.putString("query", query)


                val fragmen = SearchFragment()
                fragmen.arguments = bundle
                supportFragmentManager.popBackStack()
                supportFragmentManager.commit {
//                    remove(fragmenLayout)
                    replace(R.id.MainFragment, fragmen, "myFragment")
                    addToBackStack(null)
                }
            }


        }
    }

    private fun gantiFragment(fragment: Fragment) {

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.MainFragment, fragment, "myFragment")
        }



    }

}
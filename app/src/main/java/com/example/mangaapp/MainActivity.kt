package com.example.mangaapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    var fragLogic : Fragment? = null


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
            val query = intent.getStringExtra(SearchManager.QUERY)
            var fragmenLayout = supportFragmentManager.findFragmentById(R.id.MainFragment)



            if (fragmenLayout !is SearchFragment ) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.MainFragment, SearchFragment(), "myFragment")
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
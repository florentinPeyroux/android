package com.example.flo.gestionduparcvert;

import android.content.Intent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.flo.gestionduparcvert.activity.AjouterActivity;
import com.example.flo.gestionduparcvert.activity.DetailProblemeActivity;
import com.example.flo.gestionduparcvert.database.DatabaseHelper;
import com.example.flo.gestionduparcvert.database.Probleme;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume(){
        super.onResume();
        try {
            ListView listView = (ListView) findViewById(R.id.listeProbleme);

//            //récupère le dao pour intérargir avec la table
            final Dao<Probleme,Integer> problemeDao = getHelper().getProblemeDao();
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

            //on initialise une requête
            QueryBuilder<Probleme, Integer> queryBuilder = problemeDao.queryBuilder();
            //on fait juste un orderBy avec la colonne de référence et un booléen, true pour ordre croissant, false pour décroissant
            queryBuilder.orderBy(Probleme.PROBLEME_TYPE,true);
            //on dit que l'on a fini
            PreparedQuery<Probleme> preparedQuery = queryBuilder.prepare();
            final List<Probleme> problemes = problemeDao.query(preparedQuery);

            for ( Probleme film : problemes ) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("type",film.getType());
                map.put("adresse",film.getAdresse());
                mylist.add(map);
            }
            SimpleAdapter adapter = new SimpleAdapter (this.getBaseContext(), mylist, R.layout.listview_probleme,
                    new String[] {"type", "adresse"}, new int[] {R.id.v_type, R.id.v_adresse});
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Probleme probleme = problemes.get(position);

                    Intent intent = new Intent(getApplicationContext(),DetailProblemeActivity.class);
                    intent.putExtra("probleme",probleme);
                    startActivity(intent);


                }
            });
            listView.setAdapter(adapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
    private DatabaseHelper getHelper(){
        if(this.databaseHelper == null){
            this.databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return this.databaseHelper;
    }

    public void lancerAjouterUnProblemeActitvity(View v){
        Intent intent = new Intent(this, AjouterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_genererBDD) {
            try {
                genererBDD();
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void genererBDD() throws SQLException {
        //on récupère les Dao des tables
        Dao<Probleme,Integer> problemeDao = getHelper().getProblemeDao();
        Probleme probleme;

        probleme = new Probleme("Détritus","SEMM, enseignement et multimédia - 7 Rue du 8 Mai 1945, 59650 Villeneuve-d'Ascq", 3.135445,50.609338,"detritus");
        problemeDao.create(probleme);

        probleme = new Probleme("Arbre à abattre","Batiment M5 Université Lille 1 - 8, 06750 Caille", 3.136328,50.609520,"arbre qui risque de tomber sur la route");
        problemeDao.create(probleme);

        probleme = new Probleme("Détritus","Lilliad - Learning Center Innovation - 2 Avenue Jean Perrin, 59650 Villeneuve-d'Ascq", 3.141780,50.609408,"detritus");
        problemeDao.create(probleme);

        probleme = new Probleme("Arbre à abattre","S.U.A.P.S.- Avenue Paul Langevin, 59650 Villeneuve-d'Ascq",  3.144969,50.605927,"arbre qui risque de tomber sur la route");
        problemeDao.create(probleme);

        probleme = new Probleme("Haie à tailler","IUT (Institut Universitaire Technologie A de Lille) - Cité Scientifique, 59650 Villeneuve-d'Ascq", 3.145412,50.611034,"arbre a tailler");
        problemeDao.create(probleme);

        probleme = new Probleme("Haie à tailler","Bâtiment DESS - 20 Rue Guglielmo Marconi, 59650 Villeneuve-d'Ascq", 3.133958,50.610368,"arbre a tailler");
        problemeDao.create(probleme);

        probleme = new Probleme("Mauvaise herbe","R.U. Sully - Avenue Paul Langevin, 59650 Villeneuve-d'Ascq", 3.136484,50.605371,"Mauvaise herbe");
        problemeDao.create(probleme);

        probleme = new Probleme("Arbre à tailler","A5 - 59650 Villeneuve-d'Ascq", 3.139455,50.606653,"");
        problemeDao.create(probleme);

        probleme = new Probleme("Autre","Bâtiment P1 - 2 Avenue Jean Perrin, 59650 Villeneuve-d'Ascq", 3.141652,50.610548,"herbe bruler");
        problemeDao.create(probleme);

        probleme = new Probleme("Autre","M1 - 59650 Villeneuve-d'Ascq", 3.138784,50.608904,"voiture bruler");
        problemeDao.create(probleme);

    }
}

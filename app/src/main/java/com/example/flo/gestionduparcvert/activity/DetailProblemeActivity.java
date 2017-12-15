package com.example.flo.gestionduparcvert.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flo.gestionduparcvert.R;
import com.example.flo.gestionduparcvert.database.DatabaseHelper;
import com.example.flo.gestionduparcvert.database.Probleme;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class DetailProblemeActivity extends AppCompatActivity {
    private TextView type;
    private TextView adresse;
    private TextView description;
    private Probleme probleme;

    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_probleme);

        Intent intent = getIntent();
        this.probleme = (Probleme)intent.getSerializableExtra("probleme");
        this.type = (TextView) findViewById(R.id.type);
        this.adresse = (TextView) findViewById(R.id.adresse);
        this.description = (TextView) findViewById(R.id.description);

        this.type.setText(probleme.getType());
        this.adresse.setText(probleme.getAdresse());
        this.description.setText(probleme.getDescription());
    }

    public void localiser(View v){
        if(v.getId() ==R.id.boutonLocaliser){
            String url ="https://www.google.com/maps/search/?api=1&query="+probleme.getLatitude()+","+probleme.getLongitude();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }

    public void supprimer(View v)  {
        if(v.getId()== R.id.boutonSupprimer){
            try {
                final Dao<Probleme,Integer> problemeDao = getHelper().getProblemeDao();

            AlertDialog.Builder builder  = new AlertDialog.Builder(DetailProblemeActivity.this);
            builder.setMessage("Vous allez supprimer le problème , êtes vous sur de vouloir continuer ?");
            builder.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        problemeDao.delete(probleme);
                        setResult(1);
                        finish();
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                }
            });
            builder.create().show();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private DatabaseHelper getHelper(){
        if(this.databaseHelper == null){
            this.databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return this.databaseHelper;
    }

}

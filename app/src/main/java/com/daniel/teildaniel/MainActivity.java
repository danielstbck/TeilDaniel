package com.daniel.teildaniel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.RemoteController;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MEDIKAMENT_NAME = "com.daniel.lucidus.EXTRA_MEDIKAMENT_NAME";
    public static final String EXTRA_MEDIKAMENT_WIRKSTOFF = "com.daniel.lucidus.EXTRA_MEDIKAMENT_WIRKSTOFF";
    public static final String EXTRA_MEDIKAMENT_ANWENDUNG = "com.daniel.lucidus.EXTRA_MEDIKAMENT_ANWENDUNG";
    public static final String EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG = "com.daniel.lucidus.EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG";

    private MedikamentViewModel medikamentViewModel;

    private static MainActivity instance;

    private RelativeLayout relativeLayout;

    private TextView begruessungTextView;

    final MedikamentAdapter adapter = new MedikamentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.relativeLayoutMain);

        instance = this;

        begruessungTextView = findViewById(R.id.begruesungText);

        // ----------------------------------------------------------------

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        final ArrayList<Medikament> unfilteredList = new ArrayList<>();

        medikamentViewModel = ViewModelProviders.of(this).get(MedikamentViewModel.class);
        medikamentViewModel.getAllMedikamente().observe(this, new Observer<List<Medikament>>() {
            @Override
            public void onChanged(@Nullable List<Medikament> medikamente) {
                adapter.setMedikamente(medikamente);
                unfilteredList.addAll(adapter.getMedikamente());
            }
        });

        recyclerView.setVisibility(View.INVISIBLE);

        // ----------------------------------------------------------------

        final EditText suchText = findViewById(R.id.searchText);

        suchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("") || s.toString().isEmpty()) {
                    adapter.setMedikamente(unfilteredList);
                    recyclerView.setVisibility(View.INVISIBLE);
                    begruessungTextView.setVisibility(View.INVISIBLE);
                } else {
                    filter(s.toString());
                    recyclerView.setVisibility(View.VISIBLE);
                    begruessungTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // -----------------------------------------------------------------------

        adapter.setOnItemClickListener(new MedikamentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Medikament medikament) {
                startActivity(medikament);
            }
        });

    }

    public void startActivity(Medikament medikament) {
        Intent medikamentIntent = new Intent(this, MedikamentActivity.class);

        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_NAME, medikament.getName());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_WIRKSTOFF, medikament.getWirkstoff());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_ANWENDUNG, medikament.getAnwendungsgebiet());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG, medikament.getVerschreibungspflichtig());

        startActivity(medikamentIntent);

    }

    private void filter(String text) {
        ArrayList<Medikament> filteredList = new ArrayList<>();

        for(Medikament med : adapter.getMedikamente()) {
            if (med.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(med);
            }
        }

        adapter.filterList(filteredList);
    }

    public List<Medikament> readCSV() {

        InputStream is = getResources().openRawResource(R.raw.medikamente);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        String cvsSplitBy = ";";

        List<Medikament> medikamentList = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {

                String[] a = line.split(cvsSplitBy);

                Medikament medikament = new Medikament(a[2], a[6], a[15], a[13]);

                medikamentList.add(medikament);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return medikamentList;

    }

    public static MainActivity getInstance() {
        return instance;
    }

}
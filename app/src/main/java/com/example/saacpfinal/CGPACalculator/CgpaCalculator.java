package com.example.saacpfinal.CGPACalculator;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saacpfinal.R;

import java.util.ArrayList;
import java.util.List;

public class CgpaCalculator extends AppCompatActivity {

    String[] grade = {"A+","A","A-","B+","B","B-","C+","C","D","F"};
    Spinner spinner;
    Button add,evaluate;
    EditText creadit,subs;
    Double totalc=0.00;
    Double totalcg=0.00;
    GridLayout grid;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa_calculator);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));


        spinner = (Spinner) findViewById(R.id.spinner_grade);
        add = (Button) findViewById(R.id.add_field);
        evaluate = (Button) findViewById(R.id.calculate_gpa);
        creadit = (EditText) findViewById(R.id.credit_view);
        subs = (EditText) findViewById(R.id.subject_view);
        textView = (TextView) findViewById(R.id.view);
        grid = findViewById(R.id.grid1);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycluar_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(CgpaCalculator.this));
        List<item> items = new ArrayList<item>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_view,grade);
        adapter.setDropDownViewResource(R.layout.spinner_view);
        spinner.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = spinner.getSelectedItem().toString();
                String cred = creadit.getText().toString();
                String subjectss = subs.getText().toString();


                //Toast.makeText(MainActivity.this, s, LENGTH_SHORT).show();
                //Adapter work recycler view
                if(subjectss.isEmpty()){
                    subs.setError("Subject not fill");
                }
                else if(cred.isEmpty())
                {
                    creadit.setError("Credit not fill");
                }
                else{
                    Double credit = Double.parseDouble(cred);
                    Double grades = return_double(value);
                    items.add(new item(subjectss,cred,value));
                    recyclerView.setAdapter(new CGPAAdapter(getApplicationContext(),items));
                    totalc = totalc+credit;
                    totalcg = totalcg + (credit*grades);
                    String s=String.valueOf(totalcg);
                }


            }
        });
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                String s="0.00";
                Double gpa = 0.00;
                gpa = totalcg/totalc;
                if(totalcg==0.00 || totalc==0.00 || gpa==0.00)
                {
                    s="0.00";
                }
                else{
                    s=String.format("%.2f",gpa);
                }


                if(s.equals("0.00")){
                    textView.setText("GPA: "+s);
                }
                else {

                    textView.setText("GPA: "+s);
                    Toast.makeText(CgpaCalculator.this, s, LENGTH_SHORT).show();
                    s=null;
                    totalcg=totalc=0.00;
                }
            }

        });



    }

    private Double return_double(String s) {
        double d;
        if(s.equals("A+"))d=4.00;
        else if(s.equals("A"))d=3.75;
        else if(s.equals("A-"))d=3.50;
        else if(s.equals("B+"))d=3.25;
        else if(s.equals("B"))d=3.00;
        else if(s.equals("B-"))d=2.75;
        else if(s.equals("C"))d=2.25;
        else if(s.equals("C+"))d=2.50;
        else if(s.equals("D"))d=2.00;
        else d=0.00;
        return d;
    }


}
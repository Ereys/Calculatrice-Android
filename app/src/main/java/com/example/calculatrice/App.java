package com.example.calculatrice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calculatrice.models.Calcul;
import com.example.calculatrice.models.CalculBuilder;
import com.example.calculatrice.models.HistoryCalcul;

public class App extends AppCompatActivity {

    private TextView screen;

    private TextView screenCalcul;

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private TableLayout allButton;
    private boolean isFirstCalcul = true;
    private CalculBuilder currentCalcul;

    private final int[] operation = {R.id.bt_add, R.id.bt_sub,R.id.bt_mult, R.id.bt_divide};
    private final int[] specialOperation = {R.id.bt_pow, R.id.bt_sqrt, R.id.bt_invert};
    private final int[] numberButton = {R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9, R.id.bt_dot};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout);

        this.screen = findViewById(R.id.screen);
        this.screenCalcul = findViewById(R.id.screen_calcul);
        this.allButton = findViewById(R.id.tablelayout01);
        this.listView = findViewById(R.id.list_view);

        this.arrayAdapter = new ArrayAdapter(this,R.layout.custom_textview, HistoryCalcul.getHistoryInstance().getHistoryCalcul());
        listView.setAdapter(arrayAdapter);

        this.currentCalcul = CalculBuilder.builder();
        this.screen.setText("0");

        operationButtonInit();
        numberButtonInit();
    }

    public void operationButtonInit(){
        for(int id : this.operation){
            findViewById(id).setOnClickListener(view -> handleOnOperationClick(view));
        }
        for(int id : this.specialOperation){
            findViewById(id).setOnClickListener(view -> handleOnSpecialOperationClick(view));
        }
        findViewById(R.id.bt_egal).setOnClickListener(view -> handleOnEgalTouched());
        findViewById(R.id.bt_C).setOnClickListener(view -> handleOnClearTouched());
        findViewById(R.id.bt_CM).setOnClickListener(view -> handleOnClearMemoryClick());
        findViewById(R.id.bt_return).setOnClickListener(view -> handleOnReturnClick());
    }

    public void numberButtonInit(){
        for(int id : this.numberButton){
            findViewById(id).setOnClickListener(view -> handleOnNumberTouched(view));
        }
    }

    /**
     * Handle the following operation : +,-,x and /
     *
     */
    public void handleOnOperationClick(View v){
        String operation = ((Button) v).getText().toString();
        if(!this.currentCalcul.getOperation().isEmpty() && !this.currentCalcul.getValue2().isEmpty()){
            this.currentCalcul.setOperation(operation);
            updateResultCalcul();
        }
        else if(!this.currentCalcul.getValue1().isEmpty()) {
            this.isFirstCalcul = false;
            this.currentCalcul.setOperation(operation);
            updateResultText("0");
        }
    }

    /**
     * Handle the following operations : x^2, 2√x and 1/x
     *
     */
    public void handleOnSpecialOperationClick(View v){
        String operation = ((Button) v).getText().toString();

        try {
            if (!this.currentCalcul.getValue1().isEmpty()) {
                this.currentCalcul.setOperation(operation);
                this.currentCalcul.addToSecondValue("0");
                Calcul c = this.currentCalcul.build();

                this.updateResultTextWithSpecialOperation(Double.toString(c.getResult()));
                HistoryCalcul.getHistoryInstance().addCalcul(c);

                this.currentCalcul = CalculBuilder.builder();
                this.currentCalcul.addToFirstValue(Double.toString( HistoryCalcul.getHistoryInstance().getLastCalcul().getResult()));
                this.isFirstCalcul = false;
            }
        }catch (Exception e){
            this.updateResultText(e.getMessage());
        }
    }


    public void handleOnEgalTouched(){
        try {
            if (!this.currentCalcul.getValue1().isEmpty() && !this.currentCalcul.getValue2().isEmpty()) {
                updateResultCalcul();
            }else{
                this.updateResultText(this.currentCalcul.toString());
            }
        }catch (Exception exc){
            this.updateResultText(exc.getMessage());
        }
    }

    /**
     * Handle the input of the numbers
     *
     */
    public void handleOnNumberTouched(View v){
        String number = ((Button) v).getText().toString();
        if(isFirstCalcul){
            this.currentCalcul.addToFirstValue(number);
            this.updateResultText(this.currentCalcul.getValue1());
        }else{
            this.currentCalcul.addToSecondValue(number);
            this.updateResultText(this.currentCalcul.getValue1());
        }
    }

    /**
     * Clear the current calcul when the user click on "C"
     *
     */
    void handleOnClearTouched(){
        this.currentCalcul = CalculBuilder.builder();
        this.isFirstCalcul = true;
        this.updateResultText("0");
    }

    /**
     * Handle the clear of the memory when the user click on "CM"
     *
     */
    public void handleOnClearMemoryClick(){
        HistoryCalcul.getHistoryInstance().clearHistory();
        this.arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Delete the last input from the current number when the user click on "return"
     *
     */
    public void handleOnReturnClick(){
        this.updateResultText("Not supported yet !");
     /*   if(isFirstCalcul){
            this.currentCalcul.subToFirstValue();
            this.updateResultText(this.currentCalcul.getValue1()); //  Result
        }else{
            this.currentCalcul.subToSecondValue();
            this.updateResultText(this.currentCalcul.getValue2()); //  Result
        } */
    }


    /**
     * Handle the switch between positiv and negativ number when the user click on "+/-"
     *
     */
    public void handleOnSwitchClick(){
        this.updateResultText("Not supported yet !");
    }
    public void updateResultCalcul(){
        Calcul c = this.currentCalcul.build();
        this.updateResultText(Double.toString(c.getResult()));
        HistoryCalcul.getHistoryInstance().addCalcul(c);
        this.arrayAdapter.notifyDataSetChanged();

        this.currentCalcul = CalculBuilder.builder();
        this.isFirstCalcul = false;
        this.currentCalcul.addToFirstValue(Double.toString(HistoryCalcul.getHistoryInstance().getLastCalcul().getResult()));
    }
    public void updateResultText(String result){
        this.screen.setText(result);
        this.screenCalcul.setText(this.currentCalcul.toString());
    }

    public void updateResultTextWithSpecialOperation(String result){
        this.screen.setText(result);
        this.screenCalcul.setText(this.currentCalcul.toStringWithSpecialOperation());
    }
}
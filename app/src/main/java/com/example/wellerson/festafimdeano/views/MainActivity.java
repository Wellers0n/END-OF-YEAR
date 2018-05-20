package com.example.wellerson.festafimdeano.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wellerson.festafimdeano.R;
import com.example.wellerson.festafimdeano.constants.FimDeAnoConstants;
import com.example.wellerson.festafimdeano.util.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder nViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        this.nViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.nViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.nViewHolder.buttonConfirm = (Button) findViewById(R.id.button_comfirm);

        this.nViewHolder.buttonConfirm.setOnClickListener(this);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.nViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeftToEndOfYear()), getString(R.string.dias));

        this.nViewHolder.textDaysLeft.setText(daysLeft);


    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        this.verifyPresence();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.button_comfirm){

            String presence = this.mSecurityPreferences.getStoreString(FimDeAnoConstants.PRESENCE);

            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

                intent.putExtra(FimDeAnoConstants.PRESENCE, presence);



            startActivity(intent);
        }
    }

    private int getDaysLeftToEndOfYear(){

        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int dayDecenber31 = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayDecenber31 - today;

    }

    public void verifyPresence(){

        String presence = this.mSecurityPreferences.getStoreString(FimDeAnoConstants.PRESENCE);

        if (presence.equals(""))
            this.nViewHolder.buttonConfirm.setText(R.string.naoConfirmado);
        else if (presence.equals(FimDeAnoConstants.CONFIRMED_WILL_GO))
            this.nViewHolder.buttonConfirm.setText(R.string.sim);
        else
            this.nViewHolder.buttonConfirm.setText(R.string.nao);

    }

    private static class ViewHolder{
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}

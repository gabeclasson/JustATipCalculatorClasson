package com.example.justatipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    public static final int lowestTipPercent = 10;
    public static final DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        SeekBar genSeek = findViewById(R.id.generosity);
        genSeek.setOnSeekBarChangeListener(new genLis());
        SeekBar qualSeek =  findViewById(R.id.quality);
        qualSeek.setOnSeekBarChangeListener(new qualLis());
        updatePercentage();
    }
    // Code based on stack exchange https://stackoverflow.com/questions/8956218/android-seekbar-setonseekbarchangelistener
    private class genLis implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            int generosity = progress;
            String text;
            if (generosity >= 90)
                text = getString(R.string.gen_text_10);
            else if (generosity >= 80)
                text = getString(R.string.gen_text_09);
            else if (generosity >= 70)
                text = getString(R.string.gen_text_08);
            else if (generosity >= 60)
                text = getString(R.string.gen_text_07);
            else if (generosity >= 50)
                text = getString(R.string.gen_text_06);
            else if (generosity >= 40)
                text = getString(R.string.gen_text_05);
            else if (generosity >= 30)
                text = getString(R.string.gen_text_04);
            else if (generosity >= 20)
                text = getString(R.string.gen_text_03);
            else if (generosity >= 10)
                text = getString(R.string.gen_text_02);
            else
                text = getString(R.string.gen_text_01);
            TextView gen = findViewById(R.id.generousText);
            gen.setText(text);
            updatePercentage();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }

    private class qualLis implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            int tipAmt = progress + lowestTipPercent;
            String text;
            if (tipAmt >= 30)
                text = getString(R.string.qual_text_01);
            else if (tipAmt >= 25)
                text = getString(R.string.qual_text_02);
            else if (tipAmt >= 20)
                text = getString(R.string.qual_text_03);
            else if (tipAmt >= 15)
                text = getString(R.string.qual_text_04);
            else if (tipAmt > 10)
                text = getString(R.string.qual_text_05);
            else if (tipAmt <= 10)
                text = getString(R.string.qual_text_06);
            else
                text = getString(R.string.qual_text_04);
            TextView qual = (TextView) findViewById(R.id.qualText);
            qual.setText(text);
            updatePercentage();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }

    public double updatePercentage(){
        TextView percentage = (TextView) findViewById(R.id.tipPercent);
        SeekBar qual = (SeekBar) findViewById(R.id.quality);
        SeekBar generosity = (SeekBar) findViewById(R.id.generosity);
        int basePercent = lowestTipPercent + qual.getProgress();
        double percent = basePercent * generosity.getProgress() / 50.0;
        percentage.setText(df.format(percent) + "%");
        return percent;
    }

    public void calculate (View v){
        EditText bill = (EditText) findViewById(R.id.bill);
        TextView tip = (TextView) findViewById(R.id.tip);
        TextView total = (TextView) findViewById(R.id.total);
        String billText = bill.getText().toString();
        billText = billText.replace("$", "");
        try {
            double billAmount = Double.parseDouble(billText);
            double tipCost = updatePercentage() / 100.0 * billAmount;
            tip.setText("$" + df.format(tipCost));
            total.setText("$" + df.format(tipCost + billAmount));
        }
        catch (Exception e){
            Context context = getApplicationContext();
            String text = getString(R.string.error_message);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}

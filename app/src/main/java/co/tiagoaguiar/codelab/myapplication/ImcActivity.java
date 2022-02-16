package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText editHeight;
    private EditText editWeigth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editHeight = findViewById(R.id.edit_imc_height);
        editWeigth = findViewById(R.id.edit_imc_weight);

        Button btnSend = findViewById(R.id.btn_imc_send);


        btnSend.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(ImcActivity.this, R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }
            String sHeight = editHeight.getText().toString();
            String sWeigth = editWeigth.getText().toString();

            int height = Integer.parseInt(sHeight);
            int weight = Integer.parseInt(sWeigth);

            double result = calculateImc(height, weight);
            Log.d("TESTE", "resultado" + result);

            int imcResponseID = imcResponse(result);

            AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage(imcResponseID)
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    })
                    .setNegativeButton(R.string.save, ((dialog1, which) -> {

                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", result);
                            runOnUiThread(() -> {
                                if (calcId > 0)
                                    Toast.makeText(ImcActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                            });
                        }).start();
                    }))
                    .create();

            dialog.show();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editWeigth.getWindowToken(), 0);
        });
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        else if (imc < 16)
            return R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return R.string.imc_low_weight;
        else if (imc < 25)
            return R.string.normal;
        else if (imc < 30)
            return R.string.imc_high_weight;
        else if (imc < 35)
            return R.string.imc_so_high_weight;
        else if (imc < 40)
            return R.string.imc_severely_high_weight;
        else return R.string.imc_extreme_weight;
    }

    private double calculateImc(int heigth, int weight) {
        return weight / (((double) heigth / 100) * ((double) heigth / 100));
    }

    private boolean validate() {
        return (!editWeigth.getText().toString().startsWith("0")
                && !editHeight.getText().toString().startsWith("0")
                && !editWeigth.getText().toString().isEmpty()
                && !editHeight.getText().toString().isEmpty());

    }
}
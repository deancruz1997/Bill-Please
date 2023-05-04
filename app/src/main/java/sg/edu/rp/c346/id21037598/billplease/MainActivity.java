package sg.edu.rp.c346.id21037598.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Input variables
    EditText amountInput;
    EditText paxInput;
    EditText discountInput;

    // toggleable buttons
    ToggleButton svcButton;
    ToggleButton gstButton;

    // radio button group
    RadioGroup paymentGroup;

    // buttons for split and reset
    Button splitButton;
    Button resetButton;

    // Variables to display output
    TextView billOutput;
    TextView splitOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link variables to xml elements
        amountInput = findViewById(R.id.amountInput);
        paxInput = findViewById(R.id.paxInput);
        discountInput = findViewById(R.id.discountInput);

        svcButton = findViewById(R.id.svcButton);
        gstButton = findViewById(R.id.gstButton);

        paymentGroup = findViewById(R.id.paymentGroup);

        splitButton = findViewById(R.id.splitButton);
        resetButton = findViewById(R.id.resetButton);

        billOutput = findViewById(R.id.billOutput);
        splitOutput = findViewById(R.id.splitOutput);

        paymentGroup.check(R.id.cashButton);

        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialize variables
                String amountResponse, paxResponse, discountResponse, splitTextOutput;

                double amountDouble, discountDouble, splitPay;
                int paxInt;

                // checking if required inputs are empty, if empty, send error msg
                if (!amountInput.getText().toString().equals("")) {
                    if (!paxInput.getText().toString().equals("")) {

                        //if not, capture inputs into strings
                        amountResponse = amountInput.getText().toString();
                        paxResponse = paxInput.getText().toString();

                        //convert strings to double or int
                        amountDouble = Double.parseDouble(amountResponse);
                        paxInt = Integer.parseInt(paxResponse);

                        //check if there is discount, and then apply
                        if (!discountInput.getText().toString().equals("")) {
                            discountResponse = discountInput.getText().toString();
                            discountDouble = Double.parseDouble(discountResponse);

                            amountDouble *= (1 - (discountDouble / 100));
                        }

                        //check if gst or svc buttons are toggled, return amt
                        if (gstButton.isChecked() && svcButton.isChecked()) {
                            amountDouble *= 1.1;
                            amountDouble *= 1.07;

                        } else if (!gstButton.isChecked() && svcButton.isChecked())
                            amountDouble *= 1.1;

                        else if (gstButton.isChecked() && !svcButton.isChecked())
                            amountDouble *= 1.07;

                        splitPay = amountDouble / paxInt;

                        // convert values back into string
                        amountResponse = String.format("%.2f", amountDouble);
                        splitTextOutput = String.format("%.2f", splitPay);

                        // adds PayNow to the end of the amountResponse string
                        if (paymentGroup.getCheckedRadioButtonId() == R.id.payNowButton)
                            splitTextOutput += " via PayNow to 9123 4567";

                            // return string
                        billOutput.setText("Total Bill: $" + amountResponse);
                        splitOutput.setText("Each Pays: $" + splitTextOutput);

                    } else {
                        billOutput.setText("Error. Please input number of pax.");
                    }
                } else {
                    billOutput.setText("Error. Please input amount.");
                }
            }
        });

        // resets all inputs
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountInput.setText("");
                paxInput.setText("");
                discountInput.setText("");

                svcButton.setChecked(false);
                gstButton.setChecked(false);

                paymentGroup.check(R.id.cashButton);

                billOutput.setText("");
                splitOutput.setText("");
            }
        });

    }
}
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import javax.xml.datatype.Duration;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when increment button is clicked
     */
    public void increment(View view) {
        Button incrementButton = (Button) findViewById(R.id.IncrementButtonView);
        if (quantity == 100) {
            incrementButton.setEnabled(false);
            Toast.makeText(this, getString(R.string.max_quantity), Toast.LENGTH_SHORT).show();
        } else {
            quantity += 1;
        }
        incrementButton.setEnabled(true);
        displayQuantity(quantity);
    }

    /**
     * This method is called when decrement is called
     */
    public void decrement(View view) {
        Button decrementButton = (Button) findViewById(R.id.decrementButtonView);
        if (quantity == 1) {
            decrementButton.setEnabled(false);
            Toast.makeText(this, getString(R.string.min_quantity), Toast.LENGTH_SHORT).show();
        } else {
            quantity -= 1;

        }
        decrementButton.setEnabled(true);
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Checks for WhippedCream Topping
        CheckBox addWhippedCream = (CheckBox) findViewById(R.id.WhippedCream);
        boolean hasWhippedCream = addWhippedCream.isChecked();

        //Checks for Chocolate Topping
        CheckBox addChocolate = (CheckBox) findViewById(R.id.ChoclateTopping);
        boolean hasChocolate = addChocolate.isChecked();

        //Gets the name from the EditText
        EditText nameEditText = (EditText) findViewById(R.id.NameText);
        String nameFromEditText = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, hasWhippedCream, hasChocolate, nameFromEditText);
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary);
//        orderSummaryTextView.setText(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderSummary_java) + nameFromEditText);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        startActivity(intent);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * Calculates the price received from SubmitOrder method
     *
     * @param addWhippedCream is used to check whether customer needs Whipped Cream
     * @param addChocolate    is used to check whether customer needs Chocolate
     * @return price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;
        if (addWhippedCream) {
            price += 1;
        }
        if (addChocolate) {
            price += 2;
        }
        return quantity * price;
    }

    /**
     * Creates an order summary
     *
     * @param price           is passed. This is the total price of order.
     * @param hasWhippedCream is passed to check the WhippedCream state.
     * @param hasChocolate    is passed to check the Chocolate state
     * @param name            is passed to get the name from EditText
     * @return the priceMessage to submitOrder method
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String priceMessage = getString(R.string.name_java, name);
        priceMessage += "\n" + getString(R.string.whipped_cream_java) + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.chocolate_java) + hasChocolate + "\n" + getString(R.string.quantity_java) + quantity;
        priceMessage += "\n" + getString(R.string.total, NumberFormat.getCurrencyInstance().format(price));
        priceMessage +=  price + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}
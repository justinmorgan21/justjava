package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = ((EditText)findViewById(R.id.name_edit_text_view)).getText().toString();
        boolean hasWhippedCream = ((CheckBox)findViewById(R.id.whipped_cream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox)findViewById(R.id.chocolate_checkbox)).isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, name, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);
    }

    public void increment(View view) {
        if(quantity < 100) {
            quantity++;
        }
        else {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity > 1) {
            quantity--;
        }
        else {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * Calculate the total price of the coffee order
     * @return
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        price += hasWhippedCream ? 1 : 0;
        price += hasChocolate ? 2 : 0;
        return price * quantity;
    }

    private String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate) {
        //CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        String message = "Name: " + name;
        message += "\nAdd whipped cream? " + addWhippedCream;
        message += "\nAdd chocolate? " + addChocolate;
        message += "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
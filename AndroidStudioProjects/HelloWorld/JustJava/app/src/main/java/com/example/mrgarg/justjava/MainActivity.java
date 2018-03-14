/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

package com.example.mrgarg.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for displaying other activity
        //TextView tv = new TextView(this);
        //tv.setText("lallalallalallalallalallalallalallalallalallalalla");
        //tv.setTextSize(50);
        //tv.setTextColor(Color.GREEN);
        //tv.setMaxLines(2);
        //setContentView(tv);

        setContentView(R.layout.activity_main);
    }

    int numberOfCoffee = 1;
    //boolean hasWhippedCreamChecked = false;

    //public  void hasWhippedCream(View view) {
        //hasWhippedCreamChecked = ((CheckBox) view).isChecked();
        //if(hasWhippedCreamChecked)
            //Log.v("MainActivity", "I need whipped cream");
        //else
            //Log.v("MainActivity", "I don't need whipped cream");
    //}

    public void increment(View view) {
        if (numberOfCoffee == 100) {
            Toast.makeText(this, getString(R.string.too_many_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
        display(++numberOfCoffee);
    }

    public void decrement (View view) {
        if(numberOfCoffee > 1) {
            display(--numberOfCoffee);
        } else {
            Toast.makeText(this, getString(R.string.too_few_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_editText);
        String name = nameEditText.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.has_whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        int whippedCreamPrice;
        //Log.v("MainActivity", "Has Whipped Cream: " + hasWhippedCream);

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = orderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(priceMessage);

//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Coffee Order");
//        intent.putExtra(AlarmClock.EXTRA_HOUR, 13);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES, 56);
//        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

    }

    /**
     * This function is for calculating the total price
     *
     * @param addWhippedCream
     * @param addChocolate
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 50;

        if (addWhippedCream)
            basePrice += 10;

        if (addChocolate)
            basePrice += 20;
        return numberOfCoffee * basePrice;
    }

    /**
     * This function is for making the order summary
     *
     * @param price
     * @return order summary
     */

    private String orderSummary(String addName, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, addName);
        priceMessage += "\n" + getString(R.string.add_whipped,  addWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.number_of_cups, numberOfCoffee);
        priceMessage += "\n" + getString(R.string.total_price, "" + NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you) ;
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen
     */

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}

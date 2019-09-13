package com.amr.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amr.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;
import com.amr.sandwichclub.model.Sandwich;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Unbinder unbinder;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownTV;
    @BindView(R.id.description_tv)
    TextView descriptionTV;
    @BindView(R.id.origin_tv)
    TextView originTV;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTV;
    Sandwich sandwich;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {

            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //also known as
        List<String> knownAsList = sandwich.getAlsoKnownAs();
        StringBuilder knownAsText = new StringBuilder() ;
        if (knownAsList != null) {
            for (String n: knownAsList) {
                knownAsText.append(n).append(" , ");
            }


                try {
                    knownAsText.deleteCharAt(knownAsText.length()-1);
                    knownAsText.deleteCharAt(knownAsText.length()-1);
                } catch (Exception e) {
                    e.printStackTrace();
                }




        }
        alsoKnownTV.setText(knownAsText);
        originTV.setText(sandwich.getPlaceOfOrigin());

        //ingredients
        List<String> ingredients = sandwich.getIngredients();
        StringBuilder ingredientsText = new StringBuilder() ;
        if (ingredients != null) {
            for (String n: ingredients) {
                ingredientsText.append(n).append(" , ");
            }
            ingredientsText.deleteCharAt(ingredientsText.length()-1);
            ingredientsText.deleteCharAt(ingredientsText.length()-1);

            }
        ingredientsTV.setText(ingredientsText);

        descriptionTV.setText(sandwich.getDescription());
        }


    }


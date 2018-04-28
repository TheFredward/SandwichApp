package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Set all Textviews using BindView in a list to be later implemented
    @BindViews({R.id.main_name_tv, R.id.also_known_tv, R.id.ingredients_tv,
            R.id.origin_tv, R.id.description_tv})
    List<TextView> sandwichViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //set all the text views accordingly once oncreate (app start) starts
        ButterKnife.bind(this);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich, sandwichViews);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param sandwich class that contains the data to be used for populating the xml
     * @param sandwichViews contains mainName, alsoKnownAs, Ingredients, origin, and description
     *                      TextViews in that order to be called and then place the necessary
     *                      Info
     */
    private void populateUI(Sandwich sandwich, List<TextView> sandwichViews) {
        //place each view accordingly to each view for the sandwich details
        //set all data based on the methods from Sandwich class
        sandwichViews.get(0).setText(sandwich.getMainName());
        sandwichViews.get(1).setText(sandwich.getAlsoKnownAs().toString());
        sandwichViews.get(2).setText(sandwich.getIngredients().toString());
        sandwichViews.get(3).setText(sandwich.getPlaceOfOrigin());
        sandwichViews.get(4).setText(sandwich.getDescription());
    }
}


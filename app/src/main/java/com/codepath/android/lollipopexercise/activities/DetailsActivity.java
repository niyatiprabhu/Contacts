package com.codepath.android.lollipopexercise.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.models.Contact;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private Contact mContact;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvPhone;
    private View vPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        vPalette = findViewById(R.id.vPalette);

        // Extract contact from bundle
        mContact = (Contact) getIntent().getExtras().getSerializable(EXTRA_CONTACT);

        // Fill views with data
        Glide.with(this).asBitmap().load(mContact.getThumbnailDrawable()).centerCrop().into(target);
        tvName.setText(mContact.getName());
        tvPhone.setText(mContact.getNumber());
    }

    // Define an asynchronous listener for image loading
    CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            // TODO 1. Instruct Glide to load the bitmap into the `holder.ivProfile` profile image view
            Glide.with(DetailsActivity.this).load(resource).into(ivProfile);
            // TODO 2. Use generate() method from the Palette API to get the vibrant color from the bitmap
            // Set the result as the background color for `holder.vPalette` view containing the contact's name.
            Palette palette = Palette.from(resource).generate();
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant != null) {
                // Set the background color of a layout based on the vibrant color
                vPalette.setBackgroundColor(vibrant.getRgb());
                // Update the title TextView with the proper text color
                tvName.setTextColor(vibrant.getTitleTextColor());
                tvPhone.setTextColor(vibrant.getBodyTextColor());
            }
        }


        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

        }
    };

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            supportFinishAfterTransition();
            return true;
        }
}

package com.project.blaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.blaze.databinding.ActivityAppMainBinding;
import com.project.blaze.home.domain.DeckViewModel;
import com.project.blaze.home.presentation.dialogs.AddDeckDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AppMainActivity extends AppCompatActivity implements AddDeckDialog.DeckCreatedListener {

    private FirebaseAuth mAuth;

    private NavController navController;

    private ActivityAppMainBinding binding;
    private DeckViewModel deckViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        mAuth = FirebaseAuth.getInstance();

        //set up bottom navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navHost_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            NavigationUI.onNavDestinationSelected(item,navController);
            return true;
        });

        ///
        deckViewModel =  new ViewModelProvider(this).get(DeckViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(AppMainActivity.this, AuthActivity.class));
            finish();
        }
    }

    @Override
    public void onDeckCreated(String deckName) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = sdf.format(currentDate);
        int count = 0;
        deckViewModel.createDeck(deckName,date,count);
        Toast.makeText(this, "Deck created", Toast.LENGTH_SHORT).show();

    }
}
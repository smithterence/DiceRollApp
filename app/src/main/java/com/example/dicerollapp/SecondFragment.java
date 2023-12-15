package com.example.dicerollapp;
import android.content.res.Resources;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.dicerollapp.databinding.FragmentSecondBinding;

import java.util.Objects;
import java.util.Random;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final boolean[] turn = {true};
        int frames = 50;
        final int[] p1score = {0};
        final int[] p2score = {0};
        int[] dice = {R.drawable.sixsideddice1,R.drawable.sixsideddice2,R.drawable.sixsideddice3,
                R.drawable.sixsideddice4, R.drawable.sixsideddice5, R.drawable.sixsideddice6};

        AnimationDrawable rollAnimation = new AnimationDrawable();
        for (int i : new int[]{R.drawable.sixsideddice1, R.drawable.sixsideddice3,
                R.drawable.sixsideddice2, R.drawable.sixsideddice4, R.drawable.sixsideddice6,
                R.drawable.sixsideddice5}) {
            rollAnimation.addFrame(Objects.requireNonNull
                    (ResourcesCompat.getDrawable(getResources(), i, getActivity().getTheme())),frames);
        }
        AnimationDrawable rollAnimation2 = new AnimationDrawable();
        for (int i : new int[]{R.drawable.sixsideddice3, R.drawable.sixsideddice1, R.drawable.sixsideddice2,
                R.drawable.sixsideddice6, R.drawable.sixsideddice5, R.drawable.sixsideddice4}) {
            rollAnimation2.addFrame(Objects.requireNonNull
                    (ResourcesCompat.getDrawable(getResources(), i, getActivity().getTheme())),frames);
        }


        binding.dice1.setImageDrawable(rollAnimation);
        binding.dice2.setImageDrawable(rollAnimation2);
        binding.quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });


        binding.rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rollButton.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        binding.dice1.setImageDrawable(rollAnimation);
                        binding.dice2.setImageDrawable(rollAnimation2);
                        rollAnimation.start();
                        rollAnimation2.start();
                        Random rand = new Random();
                        int[] scores = {rand.nextInt(6) + 1, rand.nextInt(6) + 1};
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                rollAnimation.stop();
                                rollAnimation2.stop();
                                binding.dice1.setImageResource(dice[scores[0] - 1]);
                                binding.dice2.setImageResource(dice[scores[1] - 1]);

                                int sum = scores[0] + scores[1];
                                if (scores[0] == 1 || scores[1] == 1) {
                                    if (turn[0]) {
                                        p1score[0] = 0;
                                        binding.textviewScore1.setText(getString(R.string.player_one_score, p1score[0]));
                                    }
                                    else {
                                        p2score[0] = 0;
                                        binding.textviewScore2.setText(getString(R.string.player_two_score, p2score[0]));
                                    }
                                    turn[0] = !turn[0];
                                    if (turn[0]) {
                                        binding.textviewTurn.setText(R.string.player_one_turn);
                                    }
                                    else {
                                        binding.textviewTurn.setText(R.string.player_two_turn);
                                    }
                                }
                                else {
                                    if (turn[0]) {
                                        p1score[0] += sum;
                                        binding.textviewScore1.setText(getString(R.string.player_one_score, p1score[0]));
                                    }
                                    else {
                                        p2score[0] += sum;
                                        binding.textviewScore2.setText(getString(R.string.player_two_score, p2score[0]));
                                    }
                                }
                                binding.rollButton.setEnabled(true);
                            }
                        }, 500);
                    }
                }, 550);

            }
        });

        binding.holdButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                turn[0] = !turn[0];
                if (turn[0]) {
                    binding.textviewTurn.setText(R.string.player_one_turn);
                }
                else {
                    binding.textviewTurn.setText(R.string.player_two_turn);
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
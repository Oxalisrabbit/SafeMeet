package com.example.originalnavigationdrawer.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.originalnavigationdrawer.R;
import com.example.originalnavigationdrawer.databinding.FragmentTestBinding;

public class TestFragment extends Fragment {
    private TestViewModel testViewModel;
    private FragmentTestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);

        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

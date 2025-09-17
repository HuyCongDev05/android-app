package com.example.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

public class ProfileFragment extends Fragment {
    private EditText lastNameInput;
    private EditText firstNameInput;
    private EditText emailInput;
    private EditText phoneInput;
    boolean check = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        emailInput = view.findViewById(R.id.emailInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        Button editButton = view.findViewById(R.id.editButton);
        Button updateButton = view.findViewById(R.id.updateButton);

        editButton.setOnClickListener(v -> {
            if (!check) {
                check = true;
                Toast.makeText(getContext(), "Đã mở chỉnh sửa", Toast.LENGTH_SHORT).show();
                openButtonChange();
            }else Toast.makeText(getContext(), "Chỉnh sửa đang được mở", Toast.LENGTH_SHORT).show();
        });

        updateButton.setOnClickListener(v -> {
            String lastName = lastNameInput.getText().toString().trim();
            String firstName = firstNameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            if (lastName.isEmpty() && firstName.isEmpty() && email.isEmpty() && phone.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public void openButtonChange() {
        lastNameInput.setFocusableInTouchMode(true);
        lastNameInput.setClickable(true);
        lastNameInput.setCursorVisible(true);

        firstNameInput.setFocusableInTouchMode(true);
        firstNameInput.setClickable(true);
        firstNameInput.setCursorVisible(true);

        emailInput.setFocusableInTouchMode(true);
        emailInput.setClickable(true);
        emailInput.setCursorVisible(true);

        phoneInput.setFocusableInTouchMode(true);
        phoneInput.setClickable(true);
        phoneInput.setCursorVisible(true);
    }
}


package com.example.test.ui;

import static com.example.test.repository.DataCache.account;

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
import com.example.test.entity.Account;
import com.example.test.repository.DataCache;
import com.example.test.service.UpdateUserService;

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
        setUserData(account);
        UpdateUserService updateUserService = new UpdateUserService();


        editButton.setOnClickListener(v -> {
            if (!check) {
                check = true;
                Toast.makeText(getContext(), "Đã mở chỉnh sửa", Toast.LENGTH_SHORT).show();
                openButtonChange();
            }else Toast.makeText(getContext(), "Chỉnh sửa đang được mở", Toast.LENGTH_SHORT).show();
        });

        updateButton.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString().trim();
            String lastName = lastNameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String phonePattern = "^0[0-9]{9}$";
            String gmailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
            }else if (phone.matches(phonePattern) || email.matches(gmailPattern)) {
                Toast.makeText(getContext(), "Vui lòng nhập đúng email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
            }else {
                Account updatedAccount = new Account();
                updatedAccount.setAccountId(account.getAccountId());
                updatedAccount.setLastName(lastName);
                updatedAccount.setFirstName(firstName);
                updatedAccount.setEmail(email);
                updatedAccount.setPhoneNumber(phone);
                updateUserService.checkUpdateUserAsync(updatedAccount).thenAccept(success -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (success) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                DataCache.account = updatedAccount;
                            } else {
                                Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
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
    public void setUserData(Account account) {
        if (account.getLastName() != null) {
            lastNameInput.setText(account.getLastName());
        }
        if (account.getFirstName() != null) {
            firstNameInput.setText(account.getFirstName());
        }
        if (account.getEmail() != null) {
            emailInput.setText(account.getEmail());
        }
        if (account.getPhoneNumber() != null) {
            phoneInput.setText(account.getPhoneNumber());
        }
    }
}


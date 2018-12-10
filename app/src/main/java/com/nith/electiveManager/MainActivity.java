package com.nith.electiveManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llsignin, llsignup;
    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    private TextInputEditText rollNo, name, password, fName, dob, regNo, email, mobile, dept;
    ApiInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
        llsignup = (LinearLayout) findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);

        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                if (isSigninScreen)
                    btnSignup.startAnimation(clockwise);
            }
        });
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

        rollNo = findViewById(R.id.roll_no);
        name = findViewById(R.id.full_name);
        password = findViewById(R.id.password);
        fName = findViewById(R.id.f_name);
        dob = findViewById(R.id.dob);
        regNo = findViewById(R.id.reg_no);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        dept = findViewById(R.id.dept);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSigninScreen) {
                    doSignUp(rollNo.getText().toString(), name.getText().toString(), password.getText().toString(), fName.getText().toString(), dob.getText().toString(), regNo.getText().toString(), email.getText().toString(), mobile.getText().toString(), dept.getText().toString(), "1");
                }
            }
        });
    }

    private void showSigninForm() {
        llSignin = findViewById(R.id.llSignin);
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();

        llSignup = findViewById(R.id.llSignup);
        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);

        final TextInputEditText loginRollNo = findViewById(R.id.login_roll_no);
        final TextInputEditText loginPassword = findViewById(R.id.login_password);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSigninScreen) {
                    doSignIn(loginRollNo.getText().toString(), loginPassword.getText().toString());
                }
            }
        });
    }

    private void doSignUp(String rollNo, String name, String password, String fName, String dob, String regNo, String email, String phone, String dept, String act) {
        Call<String> call = apiService.doRegister(name, rollNo, password, fName, regNo, dob, dept, phone, email, act);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("+++++++++++++++", response.body());
                startActivity(new Intent(MainActivity.this, ElectiveActivity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "SignUp success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ElectiveActivity.class));
            }
        });
    }

    private void doSignIn(String rollNo, String password) {
        Call<String> call = apiService.doLogin(rollNo, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("+++++++++++++", response.body());
                startActivity(new Intent(MainActivity.this, ElectiveActivity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ElectiveActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llSignin || v.getId() == R.id.llSignup) {
            // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }


}

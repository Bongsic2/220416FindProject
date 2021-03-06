package org.techtown.findproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpOKBtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.signUpOKBtn) {
                signUp();
            }
        }
    };

    private void signUp(){
        String email = ((EditText)findViewById(R.id.editEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editPw)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.editPwCheck)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("???????????? ?????????????????????");
                                    myStartActivity(org.techtown.findproject.MemberInitActivity.class, email);
                                    finish();
                                } else {
                                    if(task.getException() != null){
                                        startToast("?????? ???????????? ???????????????");
                                    }
                                }
                            }
                        });
            }else{
                startToast("??????????????? ???????????? ????????????");
            }
        }else{
            startToast("???????????? ??????????????? ?????? ????????? ?????????");
        }


    }

    private void startToast(String msg){
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }


    private void myStartActivity(Class c, String email){
        Intent intent = new Intent(this,c);
        intent.putExtra("email",email.toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ????????? ?????? ?????????????????? ?????????????????? ???????????? ??????
        startActivity(intent);
    }
}

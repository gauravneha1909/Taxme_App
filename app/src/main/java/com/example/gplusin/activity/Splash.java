package com.example.gplusin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.gplusin.MainActivity;
import com.example.gplusin.R;
import com.example.gplusin.User;

public class Splash extends AppCompatActivity {
    ImageView image;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image=findViewById(R.id.image);
        user=new User(Splash.this);



//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
//                R.anim.hyperspace_jump);
//        image.startAnimation(hyperspaceJumpAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!user.getUserid().isEmpty()){
//                    if (user.getBussinesscategory().isEmpty()){
//                        startActivity( new Intent( Splash.this, BissinessCategory.class ) );
//                        finish();
//                    }else {
//                        startActivity( new Intent( Splash.this, MainActivity.class ) );
//                        finish();
//                    }
//                }else {
                // startActivity( new Intent( Splash.this, Login.class ) );

                Uri uri=getIntent().getData();

                if (uri!=null){

                    String uu= String.valueOf( uri );
                    // Intent intent = new Intent( Splash.this, OutStandingRequestDetaile.class );

//                    Intent intent = new Intent( Splash.this, NewPasswordLink.class );
//                    String removedata = uu.substring( uu.lastIndexOf( ".in/" ) + 4 );
//                    String idddd = removedata.substring( removedata.lastIndexOf( "/" ) + 1 );
//                    String idStr = removedata.substring(0, removedata.lastIndexOf("/"));
//                    intent.putExtra( "id", String.valueOf( idddd ) );
//                    intent.putExtra( "phoneoremail", String.valueOf( idStr ) );
//                    startActivity( intent );


                }else {

                    String userid=user.getUserid();

                    if (userid.isEmpty()) {

                        startActivity(new Intent(Splash.this, ChooseLoginPage.class));
                        finish();

                    } else {

                        if (userid.equals("")){
                            startActivity(new Intent(Splash.this, ChooseLoginPage.class));
                            finish();

                        }else {

                            startActivity(new Intent(Splash.this, MainActivity.class));
                            finish();

                        }


                    }

                }
                //    }

            }
        }, 2000);
    }


}

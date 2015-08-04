package org.gdgny.androidfan.reader;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

    private RelativeLayout rl;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.android_fan);



        rl = (RelativeLayout) findViewById(R.id.rl);

        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);

        alphaAnim.setDuration(2500);

        alphaAnim.setFillAfter(true);

        alphaAnim.setAnimationListener(new Animation.AnimationListener() {





            public void onAnimationStart(Animation animation) {



            }



            @Override

            public void onAnimationRepeat(Animation animation) {



            }



            @Override

            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(SplashActivity.this,

                        MainActivity.class);

                startActivity(intent);

                finish();

                overridePendingTransition(R.anim.slide_in_right,

                        R.anim.slide_out_left);

            }

        });

        rl.startAnimation(alphaAnim);

    }
}
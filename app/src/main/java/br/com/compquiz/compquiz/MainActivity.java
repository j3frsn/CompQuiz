package br.com.compquiz.compquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private Intent intent;
    private Button newGameByCategory, newGameByDifficultLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

    /* Chamar o método setFullScreen() ANTES da chamada do Construtor
        setFullScreen();
    */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        newGameByCategory = (Button) findViewById(R.id.new_game_by_category);
        newGameByDifficultLevel = (Button) findViewById(R.id.new_game_by_difficulty_level);

        newGameByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ChooseCategories.class);
                startActivity(intent);
            }
        });

        newGameByDifficultLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ChooseDifficultyLevel.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

            Para definir o FullScreen via AndroidManifest:
           android:theme="@android:style/Theme.NoTitleBar.Fullscreen"

    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
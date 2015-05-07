package br.com.compquiz.compquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import static br.com.compquiz.compquiz.GameConsts.PlayerSkillLevel;

/**
 * Created by jeferson on 22/04/15.
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @since 1.0
 */
public class ChooseDifficultyLevel extends Activity {

    private static final String KEY_DIFICULT_LEVEL_CHOSEN = "chosenDifficultLevel";

    private Intent intent;
    private Integer difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_difficulty_level);

        Button playByDifficulty = (Button) findViewById(R.id.new_game_by_difficulty);
        playByDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getBaseContext(), PlayByDifficultyLevel.class);
                Bundle bundle = new Bundle();

                // Passa o nível escolhido de forma criptografada para a view PlayByDifficultyLevel
                bundle.putInt(ChooseDifficultyLevel.KEY_DIFICULT_LEVEL_CHOSEN, difficulty);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void selectItem(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.beginner:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.BEGINNER.ordinal();
                }
                break;
            case R.id.nerd_wannable:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.NERD_WANNABLE.ordinal();
                }
                break;
            case R.id.nerd:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.NERD.ordinal();
                }
                break;
            case R.id.geek:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.GEEK.ordinal();
                }
                break;
            case R.id.professional:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.PROFESSIONAL.ordinal();
                }
                break;
            case R.id.god:
                if (isChecked) {
                    difficulty = PlayerSkillLevel.GOD.ordinal();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


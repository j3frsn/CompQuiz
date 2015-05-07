package br.com.compquiz.compquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static br.com.compquiz.compquiz.GameConsts.QuestionAlts;
import static br.com.compquiz.compquiz.GameConsts.QuestionAlts.ALTERNATIVE1;
import static br.com.compquiz.compquiz.GameConsts.QuestionAlts.ALTERNATIVE2;
import static br.com.compquiz.compquiz.GameConsts.QuestionAlts.ALTERNATIVE3;
import static br.com.compquiz.compquiz.GameConsts.QuestionAlts.ALTERNATIVE4;

/**
 * Inicia um novo jogo de acordo com a opção escolhida pelo jogador
 * Created by jeferson on 22/04/15.
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @see br.com.compquiz.compquiz.ChooseCategories
 * @see br.com.compquiz.compquiz.ChooseDifficultyLevel
 * @since 1.0
 */
public class PlayByDifficultyLevel extends Activity {

    // Chaves para acesso aos dados passados pelas outras Activityes de escolha do tipo de jogo
    private static final String KEY_CHOSEN_DIFFICULTY_LEVEL = "chosenDifficultLevel";

    private DbHelper dbHelper = null;
    private TextView statmnt = null;
    private QuestionAlts playerGuess = null;

    private RadioButton alt1 = null;
    private RadioButton alt2 = null;
    private RadioButton alt3 = null;
    private RadioButton alt4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        // getApplicationContext só pega o contexto dento de onCreate.
        dbHelper = new DbHelper(getApplicationContext());

        statmnt = (TextView) findViewById(R.id.statmnt);
        alt1 = (RadioButton) findViewById(R.id.alt1);
        alt2 = (RadioButton) findViewById(R.id.alt2);
        alt3 = (RadioButton) findViewById(R.id.alt3);
        alt4 = (RadioButton) findViewById(R.id.alt4);
        Button confirm = (Button) findViewById(R.id.confirm);

        alt1.setOnCheckedChangeListener(new Alt1Listener());
        alt2.setOnCheckedChangeListener(new Alt2Listener());
        alt3.setOnCheckedChangeListener(new Alt3Listener());
        alt4.setOnCheckedChangeListener(new Alt4Listener());
        confirm.setOnClickListener(new ConfirmListener());

        // Inicia o jogo
        play();
    }


    /**
     * Sobrecarga do método showNewQuestion para o caso do jogador ter escolhido jogar por
     * nível de dificuldade
     *
     * @param playerChosenDifficultyLevel nível de dificuldade escolhido pelo jogador
     */
    public void showNewQuestion(Integer playerChosenDifficultyLevel) {

        Question auxiliarQuestion =
                dbHelper.getNewQuestionByDifficultyLevel(playerChosenDifficultyLevel);

        statmnt.setText(auxiliarQuestion.getStatmnt());
        alt1.setText(auxiliarQuestion.getAlt1());
        alt2.setText(auxiliarQuestion.getAlt2());
        alt3.setText(auxiliarQuestion.getAlt3());
        alt4.setText(auxiliarQuestion.getAlt4());
    }

    /**
     * Inicia um novo jogo de acordo com a opção escolhida pelo jogador
     *
     * @since 1.0
     */
    public void play() {

        /* Depois mudar para o jogo ser progressivo;
            Primeiro o usuário começa como principiante e vai evoluindo à medida que
            consegue ponto.
         */

        Integer playerChosenDificultyLevel =
                getIntent().getIntExtra(KEY_CHOSEN_DIFFICULTY_LEVEL, -1);

        if (playerChosenDificultyLevel > -1) {

            showNewQuestion(playerChosenDificultyLevel);

        } else {
            Toast.makeText(getBaseContext(), PlayByDifficultyLevel.class.getName() + ": método play",
                    Toast.LENGTH_LONG).show();
            Log.w("JOGAR", "Retorno do bundle inválido/desconhecido");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Classe Listener do Botão Confirmar
     */
    private class ConfirmListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (playerGuess) {
                case ALTERNATIVE1:
                    if (dbHelper.checkQuestion(ALTERNATIVE1)) {
                        Toast.makeText(getBaseContext(),
                                "Você acertou!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Que pena, você errou!!!", Toast.LENGTH_LONG).show();
                    }

                    break;

                case ALTERNATIVE2:
                    if (dbHelper.checkQuestion(ALTERNATIVE2)) {
                        Toast.makeText(getBaseContext(),
                                "Você acertou!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Que pena, você errou!!!", Toast.LENGTH_LONG).show();
                    }

                    break;

                case ALTERNATIVE3:
                    if (dbHelper.checkQuestion(ALTERNATIVE3)) {
                        Toast.makeText(getBaseContext(),
                                "Você acertou!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Que pena, você errou!!!", Toast.LENGTH_LONG).show();
                    }

                    break;

                case ALTERNATIVE4:
                    if (dbHelper.checkQuestion(ALTERNATIVE4)) {
                        Toast.makeText(getBaseContext(),
                                "Você acertou!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Que pena, você errou!!!", Toast.LENGTH_LONG).show();
                    }

                    break;

                default:
                    Toast.makeText(getBaseContext(), "Escolha alguma das alternativas",
                            Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Classe Listener do botão Alt1
     */
    private class Alt1Listener implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                buttonView.setChecked(false);
            } else {
                buttonView.setChecked(true);
                playerGuess = ALTERNATIVE1;
            }
        }
    }

    /**
     * Classe Listener do botão Alt2
     */
    private class Alt2Listener implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                buttonView.setChecked(false);
            } else {
                buttonView.setChecked(true);
                playerGuess = ALTERNATIVE2;
            }
        }
    }

    /**
     * Classe Listener do botão Alt1
     */
    private class Alt3Listener implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                buttonView.setChecked(false);
            } else {
                buttonView.setChecked(true);
                playerGuess = ALTERNATIVE3;
            }
        }
    }

    /**
     * Classe Listener do botão Alt1
     */
    private class Alt4Listener implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                buttonView.setChecked(false);
            } else {
                buttonView.setChecked(true);
                playerGuess = ALTERNATIVE4;
            }
        }
    }
}

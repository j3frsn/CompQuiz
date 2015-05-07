package br.com.compquiz.compquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by jeferson on 01/04/15.
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @since 1.0
 */
public class ChooseCategories extends Activity {

    private static final String KEY_CHOSEN_CATEGORIES = "chosenCategories";
    /*
        Conferir se na passagem para a outra classe via Intent o modificador private interfere
        SEMPRE inicializar os ArrayList com o construtor padrão.
    */
    private ArrayList<Integer> chosenCategories = new ArrayList<>();
    private Button newGameByCategory;
    private Intent intent;

    private CheckBox curiosities = null;
    private CheckBox varieties = null;
    private CheckBox companies = null;
    private CheckBox hardware = null;
    private CheckBox internet = null;
    private CheckBox renown = null;
    private CheckBox programming = null;
    private CheckBox software = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_categories);

        curiosities = (CheckBox) findViewById(R.id.curiosities);
        varieties = (CheckBox) findViewById(R.id.varieties);
        companies = (CheckBox) findViewById(R.id.companies);
        hardware = (CheckBox) findViewById(R.id.hardware);
        internet = (CheckBox) findViewById(R.id.internet);
        renown = (CheckBox) findViewById(R.id.renown);
        programming = (CheckBox) findViewById(R.id.programming);
        software = (CheckBox) findViewById(R.id.software);

        newGameByCategory = (Button) findViewById(R.id.new_game_by_category);

        curiosities.setOnCheckedChangeListener(new CuriositiesListener());
        varieties.setOnCheckedChangeListener(new VarietiesListener());
        companies.setOnCheckedChangeListener(new CompaniesListener());
        hardware.setOnCheckedChangeListener(new HardwareListener());
        internet.setOnCheckedChangeListener(new InternetListener());
        renown.setOnCheckedChangeListener(new RenownListener());
        programming.setOnCheckedChangeListener(new ProgrammingListener());
        software.setOnCheckedChangeListener(new SoftwareListener());


        newGameByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getBaseContext(), PlayByDifficultyLevel.class);

                intent.putIntegerArrayListExtra(
                        KEY_CHOSEN_CATEGORIES, chosenCategories);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * ****************************************************************
     * TODOS OS LISTENERS DOS CHECK BOXES
     */
    private class CuriositiesListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.CURIOSITIES.ordinal());
            }
        }
    }

    private class VarietiesListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.VARIETIES.ordinal());
            }
        }
    }

    private class CompaniesListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.COMPANIES.ordinal());
            }
        }
    }

    private class HardwareListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.HARDWARE.ordinal());
            }

        }
    }

    private class InternetListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.INTERNET.ordinal());
            }
        }
    }

    private class RenownListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.RENOWN.ordinal());
            }
        }
    }

    private class ProgrammingListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.PROGRAMMING.ordinal());
            }
        }
    }

    private class SoftwareListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                chosenCategories.add(GameConsts.QuestionCategories.SOFTWARE.ordinal());
            }
        }
    }
}
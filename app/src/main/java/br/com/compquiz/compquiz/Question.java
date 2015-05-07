package br.com.compquiz.compquiz;

import java.util.ArrayList;

/**
 * Created by jeferson on 22/04/15.
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @since 1.0
 */
public class Question {
    private Integer id = 0;
    private String statmnt;
    private String alt1;
    private String alt2;
    private String alt3;
    private String alt4;
    private Integer answer;

    private ArrayList<Integer> difficultyLevels;
    private ArrayList<Integer> categories;

    public Question() {
        this(null, null, null, null, null, null, null, null, 0);
    }

    public Question(Integer id, String statmnt, String alt1, String alt2,
                    String alt3, String alt4, ArrayList<Integer> difficultyLevels,
                    ArrayList<Integer> categories, Integer answer) {
        setId(id);
        setStatmnt(statmnt);
        setAlt1(alt1);
        setAlt2(alt2);
        setAlt3(alt3);
        setAlt4(alt4);
        setDifficultyLevels(difficultyLevels);
        setCategories(categories);
        setAnswer(answer);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatmnt() {
        return this.statmnt;
    }

    public void setStatmnt(String statmnt) {
        this.statmnt = statmnt;
    }

    public String getAlt1() {
        return this.alt1;
    }

    public void setAlt1(String alt1) {
        this.alt1 = alt1;
    }

    public String getAlt2() {
        return this.alt2;
    }

    public void setAlt2(String alt2) {
        this.alt2 = alt2;
    }

    public String getAlt3() {
        return this.alt3;
    }

    public void setAlt3(String alt3) {
        this.alt3 = alt3;
    }

    public String getAlt4() {
        return this.alt4;
    }

    public void setAlt4(String alt4) {
        this.alt4 = alt4;
    }

    public ArrayList<Integer> getDifficultyLevels() {
        return this.difficultyLevels;
    }

    public void setDifficultyLevels(ArrayList<Integer> difficultyLevels) {
        this.difficultyLevels = difficultyLevels;
    }

    public ArrayList<Integer> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<Integer> categories) {
        this.categories = categories;
    }

    public Integer getAnswer() {
        return this.answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
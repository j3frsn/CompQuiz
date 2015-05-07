package br.com.compquiz.compquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static br.com.compquiz.compquiz.GameConsts.DbAttibs.DB_NAME;
import static br.com.compquiz.compquiz.GameConsts.DbAttibs.DB_VERSION;
import static br.com.compquiz.compquiz.GameConsts.DbAttibs.TABLE_NAME;
import static br.com.compquiz.compquiz.GameConsts.PlayerSkillLevel.BEGINNER;
import static br.com.compquiz.compquiz.GameConsts.QuestionAlts;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ALTERNATIVE1;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ALTERNATIVE2;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ALTERNATIVE3;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ALTERNATIVE4;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ANSWER;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.CATEGORIES;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.DIFFICULTY_LEVELS;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.ID;
import static br.com.compquiz.compquiz.GameConsts.QuestionAttribs.STATEMENT;

/**
 * Created by jeferson on 25/04/15.
 * <p/>
 * Classe de adição das questões ao BD na primeira inicialização do jogo e de manipulação de
 * operações básicas de CRUD.
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @since 1.0
 */
public class DbHelper extends SQLiteOpenHelper {

    private final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME.getDbAttrib()
                    + "(" + ID.getQuestionAttrib() + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATEMENT.getQuestionAttrib()
                    + " TEXT, " + ALTERNATIVE1.getQuestionAttrib()
                    + " TEXT, " + ALTERNATIVE2.getQuestionAttrib()
                    + " TEXT, " + ALTERNATIVE3.getQuestionAttrib()
                    + " TEXT, " + ALTERNATIVE4.getQuestionAttrib()
                    + " TEXT, " + DIFFICULTY_LEVELS.getQuestionAttrib()
                    + " TEXT, " + CATEGORIES.getQuestionAttrib()
                    + " TEXT, " + ANSWER.getQuestionAttrib()
                    + " NUM);";

    private ArrayList<Question> answeredQuestions = new ArrayList<>();

    private Question currentQuestion;
    private Random random = new Random();

    DbHelper(Context context) {
        super(context, DB_NAME.getDbAttrib(), null, DB_VERSION.getDbVersion());
        Log.e(DbHelper.class.getName() + "", "Base de dados criada.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(DbHelper.class.getName() + ": onCreate()", "Criando a tabela no bd...");
            db.beginTransaction();
            db.execSQL(SQL_CREATE_TABLE);

            addAllQuestionsToDb();

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(DbHelper.class.getName() + ": onCreate()", "Erro ao criar a tabela"
                    + TABLE_NAME.getDbAttrib() + " no BD");
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(DbHelper.class.getName() + ": onUpgrade()", "Atualizando a base de dados da versão "
                + oldVersion + " para a " + newVersion);

        onCreate(db);
    }

    /**
     * Confere se a alternativa escolhida pelo jogador está correta
     *
     * @param playerGuess alternativa escolhida pelo jogador
     * @since 1.0
     */
    public boolean checkQuestion(QuestionAlts playerGuess) {

        addCurrentQuestionToAnsweredQuestionsList();

        /* If simplificado */
        return getCurrentQuestion().getAnswer().equals(playerGuess.ordinal());

    }

    /**
     * Pega a questão que está sendo feita
     *
     * @return a questão que está sendo feita
     * @since 1.0
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Seta uma nova questão para a variável estática currentQuestion
     *
     * @param question questão para ser setada como a currentQuestion do jogo
     */
    public void setCurrentQuestion(Question question) {
        currentQuestion = question;
    }

    /**
     * Adiciona a currentQuestion para para a lista de questões feitas
     *
     * @since 1.0
     */
    public void addCurrentQuestionToAnsweredQuestionsList() {
        answeredQuestions.add(currentQuestion);
    }

    /**
     * Pega uma nova questão ainda não feita e adiciona à variável estática currentQuestion
     *
     * @param playerChosenDifficultyLevel nível de dificuldade escolhido pelo usuário
     * @throws java.lang.NullPointerException se a lista retornada pelo DbHelper estiver vazia
     * @since 1.0
     */
    public Question getNewQuestionByDifficultyLevel(
            Integer playerChosenDifficultyLevel) throws NullPointerException {

        Integer randomNumber;

        ArrayList<Question> allQuestionsByDifficultyLevel =
                getAllQuestionsByDifficultyLevel(playerChosenDifficultyLevel);

        /* Pegar uma gesposta nunca feita na list de forma aleatória
            Se der problema, declarar a questão de fora do while.
         */

        while (answeredQuestions.contains(
                allQuestionsByDifficultyLevel.get(randomNumber =
                        random.nextInt(allQuestionsByDifficultyLevel.size())))) {

            setCurrentQuestion(allQuestionsByDifficultyLevel.get(randomNumber));

        }
        return getCurrentQuestion();
    }

    /**
     * @param playerChosenCategories categorias escolhidas pelo jogador
     * @return uma questão ainda não perguntada
     * @throws java.lang.NullPointerException se a lista retornada pelo DbHelper estiver vazia
     * @since 1.0
     */
    public Question getNewQuestionByCategory(ArrayList<Integer> playerChosenCategories) {

        Integer randomNumber;

        ArrayList<Question> allQuestionsByCategory =
                getAllQuestionsByCategory(playerChosenCategories);

        while (answeredQuestions.contains(
                allQuestionsByCategory.get(randomNumber =
                        random.nextInt(allQuestionsByCategory.size())))) {

            setCurrentQuestion(allQuestionsByCategory.get(randomNumber));

        }

        return getCurrentQuestion();
    }

    /**
     * Transforma um ArrayList de Strings em uma única string, com cada item separado por um token
     * informado
     *
     * @param integerArrayList ArrayList de Srings que será transformado em uma única String
     * @return String resultante da tokenização
     * @since 1.0
     */
    private String integerArrayListToString(ArrayList<Integer> integerArrayList) {
        String delimiter = ",";
        String resultString = null;

        for (int i = 0; i <= integerArrayList.size(); i++) {
            resultString += integerArrayList.get(i) + delimiter;
        }
        return resultString;
    }

    /**
     * @param string string que será quebrada
     * @return ArrayList de Integer
     * @since 1.0
     */
    private ArrayList<Integer> stringToIntegerArrayList(String string) {

        String delimiter = ",";

        String[] str = string.split(delimiter);
        ArrayList<Integer> integerArrayList = new ArrayList<>();

        for (int i = 0; i <= str.length; i++) {
            integerArrayList.add(Integer.parseInt(str[i]));
        }

        return integerArrayList;
    }

    /**
     * Adiciona uma nova questão ao banco de dados
     *
     * @param question questão que será adicionada ao banco de dados
     * @throws android.database.sqlite.SQLiteException se houver algum problema no SQLite
     * @since 1.0
     */
    public void addNewQuestionToDb(Question question) throws SQLiteException {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(STATEMENT.getQuestionAttrib(),
                    question.getStatmnt());
            values.put(ALTERNATIVE1.getQuestionAttrib(),
                    question.getAlt1());
            values.put(ALTERNATIVE2.getQuestionAttrib(),
                    question.getAlt2());
            values.put(ALTERNATIVE3.getQuestionAttrib(),
                    question.getAlt3());
            values.put(ALTERNATIVE4.getQuestionAttrib(),
                    question.getAlt4());

            // Torna o ArrayList difficultyLevels numa String que separa os itens pelo delimitador ","
            values.put(DIFFICULTY_LEVELS.getQuestionAttrib(),
                    integerArrayListToString(question.getDifficultyLevels()));

            // Torna o ArrayList categories numa String que separa os itens pelo delimitador ","
            values.put(CATEGORIES.getQuestionAttrib(),
                    integerArrayListToString(question.getCategories()));

            values.put(ANSWER.getQuestionAttrib(),
                    question.getAnswer());

            // Insere a questão no Banco de Dados
            db.insert(TABLE_NAME.getDbAttrib(), null, values);

        } finally {
            db.close();
        }
    }

    /**
     * Método chamado pelo onCreate na primeira inicialização do jogo. Ele apenas adiciona todas as
     * questões no bd
     *
     * @since 1.0
     */
    public void addAllQuestionsToDb() {

        ArrayList<Integer> difficultyLevels = new ArrayList<>();
        ArrayList<Integer> categories = new ArrayList<>();

        difficultyLevels.add(BEGINNER.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD_WANNABLE.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD.ordinal());

        categories.add(GameConsts.QuestionCategories.CURIOSITIES.ordinal());
        categories.add(GameConsts.QuestionCategories.COMPANIES.ordinal());

        this.addNewQuestionToDb(new Question(0, "Qual empresa de tecnologia é conhecida como \"A " +
                "Gigante de Mountain View?\"", "Google", "Microsoft", "Apple",
                "Hewlet Package (HP)", difficultyLevels, categories, 1));

        /***************************************************************************/

        difficultyLevels.add(BEGINNER.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD_WANNABLE.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD.ordinal());

        categories.add(GameConsts.QuestionCategories.CURIOSITIES.ordinal());
        categories.add(GameConsts.QuestionCategories.INTERNET.ordinal());
        categories.add(GameConsts.QuestionCategories.VARIETIES.ordinal());

        this.addNewQuestionToDb(new Question(0, "Que palavra inglesa é utilizada para descrever o " +
                "carreramento de um arquivo da internet?", "Upgrade", "Upload", "Download",
                "Downgrade", difficultyLevels, categories, 3));

        /***************************************************************************/

        difficultyLevels.add(BEGINNER.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD_WANNABLE.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.NERD.ordinal());

        categories.add(GameConsts.QuestionCategories.CURIOSITIES.ordinal());
        categories.add(GameConsts.QuestionCategories.INTERNET.ordinal());
        categories.add(GameConsts.QuestionCategories.VARIETIES.ordinal());

        this.addNewQuestionToDb(new Question(0, "O que é a Internet?", "Uma peça", "Um programa" +
                "de computador", "Um conjunto de computadores de uma empresa", "Um sistema " +
                "global de computadores interligados em rede", difficultyLevels, categories, 4));

        /***************************************************************************/

        difficultyLevels.add(GameConsts.PlayerSkillLevel.GEEK.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.GOD.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.PROFESSIONAL.ordinal());

        categories.add(GameConsts.QuestionCategories.HARDWARE.ordinal());

        this.addNewQuestionToDb(new Question(0, "Qual peça de um computador é responsável "
                + "pela filtragem de impurezas na tensão de entrada?", "Fonte de alimentação",
                "Capacitor", "Placa Mãe", "Porta USB", difficultyLevels, categories, 4));

        /***************************************************************************/

        difficultyLevels.add(GameConsts.PlayerSkillLevel.GEEK.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.GOD.ordinal());
        difficultyLevels.add(GameConsts.PlayerSkillLevel.PROFESSIONAL.ordinal());

        categories.add(GameConsts.QuestionCategories.SOFTWARE.ordinal());
        categories.add(GameConsts.QuestionCategories.RENOWN.ordinal());
        categories.add(GameConsts.QuestionCategories.PROGRAMMING.ordinal());

        this.addNewQuestionToDb(new Question(0, "Qual o nome do criador da rede social digital que " +
                "tem como logo a letra \"F?\"", "Linus Torvalds", "Mark Zuckerberg", "Sergey brin",
                "Satya Nadella", difficultyLevels, categories, 2));
    }

    /**
     * Pega todas as questões existentes no banco de dados
     *
     * @since 1.0
     */
    public ArrayList<Question> getAllQuestionsFromDb() {

        SQLiteDatabase db = getReadableDatabase();

        final String SQL_GET_ALL_QUESTIONS = "SELECT * FROM "
                + TABLE_NAME.getDbAttrib();

        Question q = new Question();
        ArrayList<Question> allQuestionsAuxList = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL_GET_ALL_QUESTIONS, null);

        try {
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {

                    q.setId(cursor.getInt(cursor.getColumnIndex(ID.getQuestionAttrib())));

                    q.setStatmnt(cursor.getString(
                            cursor.getColumnIndex(STATEMENT.getQuestionAttrib())));

                    q.setAlt1(cursor.getString(
                            cursor.getColumnIndex(ALTERNATIVE1.getQuestionAttrib())));

                    q.setAlt2(cursor.getString(
                            cursor.getColumnIndex(ALTERNATIVE2.getQuestionAttrib())));

                    q.setAlt3(cursor.getString(
                            cursor.getColumnIndex(ALTERNATIVE3.getQuestionAttrib())));

                    q.setAlt4(cursor.getString(
                            cursor.getColumnIndex(ALTERNATIVE4.getQuestionAttrib())));

                    q.setCategories(stringToIntegerArrayList(cursor.getString(
                            cursor.getColumnIndex(CATEGORIES.getQuestionAttrib()))));

                    q.setDifficultyLevels(stringToIntegerArrayList(
                            cursor.getString(cursor.getColumnIndex(
                                    DIFFICULTY_LEVELS.getQuestionAttrib()))));

                    q.setAnswer(cursor.getInt(cursor.getColumnIndex(
                            ANSWER.getQuestionAttrib())));

                    allQuestionsAuxList.add(q);
                }
            }
        } catch (Exception e) {
            Log.d(DbHelper.class.getName() + ": getAllQuestions()",
                    "Falhou ao obter todas as questões do BD");
        } finally {
            cursor.close();
            db.close();
        }
        return allQuestionsAuxList;
    }

    /**
     * Pega todas as questões de acordo com as categorias escolhidas pelo jogador
     *
     * @param playerChosenCategories categorias de questões escolhidas pelo jogador na Activity
     *                               ChoosseCategories
     * @see br.com.compquiz.compquiz.ChooseCategories
     * @since 1.0
     */
    public ArrayList<Question> getAllQuestionsByCategory(
            ArrayList<Integer> playerChosenCategories) {

        ArrayList<Question> allQuestions = getAllQuestionsFromDb();
        ArrayList<Question> tempQuestionsArray = new ArrayList<>();

        for (int i = 0; i <= allQuestions.size(); i++) {
            for (int j = 0; j <= allQuestions.get(i).getCategories().size(); j++) {
                Integer category = allQuestions.get(i).getCategories().get(j);

                if (playerChosenCategories.contains(category)) {
                    tempQuestionsArray.add(allQuestions.get(i));
                }
            }
        }

        return tempQuestionsArray;
    }

    /**
     * Pega todas as questões do banco de dados, de acordo com o nível de dificuldade escolhido
     * pelo jogador
     *
     * @param playerChosenDifficultLevel null se nenhuma questão pertencer ao nível de dificuldade
     *                                   escolhido
     * @return ArrayList<Question> lista de questões por nível de dificuldade
     * @throws java.lang.NullPointerException se allQuestions não estiver vazia
     * @see br.com.compquiz.compquiz.ChooseDifficultyLevel
     * @since 1.0
     */
    public ArrayList<Question> getAllQuestionsByDifficultyLevel(
            Integer playerChosenDifficultLevel) {

        ArrayList<Question> allQuestions = getAllQuestionsFromDb();
        ArrayList<Question> tempQuestionArray = new ArrayList<>();

        for (int i = 0; i <= allQuestions.size(); i++) {
            for (int j = 0; j <= allQuestions.get(i).getDifficultyLevels().size(); i++) {
                if (allQuestions.get(i).getDifficultyLevels().get(j)
                        .equals(playerChosenDifficultLevel)) {

                    tempQuestionArray.add(allQuestions.get(i));
                }
            }
        }

        return tempQuestionArray;
    }
}

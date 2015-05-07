package br.com.compquiz.compquiz;

/**
 * Created by jeferson on 05/05/15.
 * <p/>
 * Classe de enumerações com todas as constantes públicas utilizadas no jogo
 *
 * @author Jeferson Andrade
 * @version 1.0
 * @since 1.0
 */
public abstract class GameConsts {

    enum DbAttibs {

        DB_NAME("comp_quiz.db"),
        TABLE_NAME("questions"),
        DB_VERSION(1);

        private String dbAttrib;
        private Integer dbVersion;

        private DbAttibs(String dbStringAttrib) {
            this.dbAttrib = dbStringAttrib;
        }

        private DbAttibs(Integer dbIntegerAttrib) {
            this.dbVersion = dbIntegerAttrib;
        }

        public String getDbAttrib() {
            return dbAttrib;
        }

        public Integer getDbVersion() {
            return dbVersion;
        }
    }

    enum PlayerSkillLevel {

        BEGINNER,
        NERD_WANNABLE,
        NERD,
        GEEK,
        PROFESSIONAL,
        GOD
    }

    enum QuestionCategories {

        CURIOSITIES,
        VARIETIES,
        COMPANIES,
        HARDWARE,
        INTERNET,
        RENOWN,
        PROGRAMMING,
        SOFTWARE
    }

    enum QuestionAlts {

        ALTERNATIVE1,
        ALTERNATIVE2,
        ALTERNATIVE3,
        ALTERNATIVE4
    }

    enum QuestionAttribs {

        ID("id"),
        STATEMENT("statmnt"),
        ALTERNATIVE1("alt1"),
        ALTERNATIVE2("alt2"),
        ALTERNATIVE3("alt3"),
        ALTERNATIVE4("alt4"),
        DIFFICULTY_LEVELS("difficulty_levels"),
        ANSWER("answer"),
        CATEGORIES("categories");

        private String questionAttrib;

        private QuestionAttribs(final String questionAttrib) {
            this.questionAttrib = questionAttrib;
        }

        public String getQuestionAttrib() {
            return this.questionAttrib;
        }
    }
}

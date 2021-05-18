package com.soft.model;

/**
 * “ипы вопросов.
 */
public enum QuestionType {

    /** ¬опрос с произвольным текстовым ответом пользовател€. */
    QUESTION_WITH_TEXT_ANSWER("¬опрос с текстовым ответом пользовател€."),

    /** ¬опрос с выбором одного ответа из предлагаемых вариантов. */
    QUESTION_WITH_SINGLE_FIXED_ANSWER("¬опрос с выбором одного ответа из предлагаемых вариантов."),

    /** ¬опрос с выбором нескольких ответов из предлагаемых вариантов. */
    QUESTION_WITH_MULTIPLE_FIXED_ANSWERS("¬опрос с выбором нескольких ответов из предлагаемых вариантов.");

    /** ќписание. */
    private String description;

    QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


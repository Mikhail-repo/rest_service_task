package com.soft.model;

/**
 * ���� ��������.
 */
public enum QuestionType {

    /** ������ � ������������ ��������� ������� ������������. */
    QUESTION_WITH_TEXT_ANSWER("������ � ��������� ������� ������������."),

    /** ������ � ������� ������ ������ �� ������������ ���������. */
    QUESTION_WITH_SINGLE_FIXED_ANSWER("������ � ������� ������ ������ �� ������������ ���������."),

    /** ������ � ������� ���������� ������� �� ������������ ���������. */
    QUESTION_WITH_MULTIPLE_FIXED_ANSWERS("������ � ������� ���������� ������� �� ������������ ���������.");

    /** ��������. */
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


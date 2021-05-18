package com.soft.entity;

import com.soft.model.QuestionType;
import javax.persistence.*;
import java.util.List;

/** Сущность вопросов. */
@Entity
@Table( name = "questions" )
public class Question {

        @Id
        @Column(name = "id", unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "questiontype")
        private QuestionType questionType;

        @Column(name = "questiontext")
        private String questionText;

        @ManyToMany
        @JoinTable (name="joinpolls",
                joinColumns=@JoinColumn (name="question_id"),
                inverseJoinColumns=@JoinColumn(name="poll_id"))
        private List<Poll> pollList;

        public Question() {
        }

        public Question(QuestionType questionType, String questionText, List<Poll> pollList) {
                this.questionType = questionType;
                this.questionText = questionText;
                this.pollList = pollList;
        }

        public Question(Integer id, QuestionType questionType, String questionText, List<Poll> pollList) {
                this.id = id;
                this.questionType = questionType;
                this.questionText = questionText;
                this.pollList = pollList;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public QuestionType getQuestionType() {
                return questionType;
        }

        public void setQuestionType(QuestionType questionType) {
                this.questionType = questionType;
        }

        public String getQuestionText() {
                return questionText;
        }

        public void setQuestionText(String questionText) {
                this.questionText = questionText;
        }

        public List<Poll> getPollList() {
                return pollList;
        }

        public void setPollList(List<Poll> pollList) {
                this.pollList = pollList;
        }
}

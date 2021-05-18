package com.soft.entity;

import javax.persistence.*;

@Entity
@Table( name = "answers" )
public class Answer {

        @Id
        @Column(name = "id", unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "account_id")
        private Integer account_id;

        @Column(name = "poll_id")
        private Integer poll_id;

        @Column(name = "question_id")
        private Integer question_id;

        @Column(name = "answertext")
        private String answerText;

        public Answer() {
        }

        public Answer(Integer account_id, Integer poll_id, Integer question_id, String answerText) {
                this.account_id = account_id;
                this.poll_id = poll_id;
                this.question_id = question_id;
                this.answerText = answerText;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Integer getAccount_id() {
                return account_id;
        }

        public void setAccount_id(Integer account_id) {
                this.account_id = account_id;
        }

        public Integer getPoll_id() {
                return poll_id;
        }

        public void setPoll_id(Integer poll_id) {
                this.poll_id = poll_id;
        }

        public Integer getQuestion_id() {
                return question_id;
        }

        public void setQuestion_id(Integer question_id) {
                this.question_id = question_id;
        }

        public String getAnswerText() {
                return answerText;
        }

        public void setAnswerText(String answerText) {
                this.answerText = answerText;
        }
}

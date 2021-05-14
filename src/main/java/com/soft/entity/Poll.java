package com.soft.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/** Сущность для тем опросов. */
@Entity
@Table( name = "polls" )
public class Poll {

        @Id
        @Column(name = "id", unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "name")
        private String name;

        @Column(name = "startDate")
        private Instant startDate;

        @Column(name = "endDate")
        private Instant endDate;

        @Column(name = "description")
        private String description;

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinTable (name="joinpolls",
                    joinColumns=@JoinColumn (name="poll_id"),
                    inverseJoinColumns=@JoinColumn(name="question_id"))
        private List<Question> questionList;

        public Poll() {
        }

        public Poll(String name, Instant startDate, Instant endDate, String description, List<Question> questionList) {
                this.name = name;
                this.startDate = startDate;
                this.endDate = endDate;
                this.description = description;
                this.questionList = questionList;
        }

        public Poll(Integer id, String name, Instant startDate, Instant endDate, String description, List<Question> questionList) {
                this.id = id;
                this.name = name;
                this.startDate = startDate;
                this.endDate = endDate;
                this.description = description;
                this.questionList = questionList;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Instant getStartDate() {
                return startDate;
        }

        public void setStartDate(Instant startDate) {
                this.startDate = startDate;
        }

        public Instant getEndDate() {
                return endDate;
        }

        public void setEndDate(Instant endDate) {
                this.endDate = endDate;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public List<Question> getQuestionList() {
                return questionList;
        }

        public void setQuestionList(List<Question> questionList) {
                this.questionList = questionList;
        }
}

package com.quiz.together.Enum;

public enum QuestionType {
    MULTIPLECHOICE {
        public String toString() {
            return "Multiple Choice";
        }
    }, FREERESPONSE {
        public String toString() {
            return "Free Response";
        }
    }, TRUEFALSE {
        public String toString() {
            return "True False";
        }
    }
}

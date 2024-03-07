package com.boot.board_240214.validator;

import com.boot.board_240214.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {

    public boolean supports(Class clazz) {
        return Board.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "content", "empty","내용을 입력하세요");
//        Person p = (Person) obj;
//        if (p.getAge() < 0) {
//            e.rejectValue("age", "negativevalue");
//        } else if (p.getAge() > 110) {
//            e.rejectValue("age", "too.darn.old");
//        }
    }
}
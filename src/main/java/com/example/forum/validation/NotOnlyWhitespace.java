package com.example.forum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// 投稿の入力内容に関するバリデーション。
// 空白のみ、改行のみ、スペースのみをチェックする。
// @NotBlankではスペースのみが判定できなかった為、カスタムアノテーションを作成。
@Target({ ElementType.FIELD })
@Constraint(validatedBy = NotOnlyWhitespaceValid.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotOnlyWhitespace {
    String message() default "投稿内容を入力してください";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.example.forum.repository;

import com.example.forum.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // JpaRepositoryを継承。
    // Service側でJpaRepositoryクラスのメソッド(findAllとか)を使用する場合は、このクラス内にメソッドの記述は不要。

    // 投稿を削除する際に紐づいているコメントも削除。
    void deleteByContentId(int contentId);
}

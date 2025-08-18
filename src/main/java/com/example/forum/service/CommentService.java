package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    CommentRepository commentRepository;

    /*
     * 返信を全件取得処理
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentRepository.findAll();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをEntity→Formに詰め替え
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            // データセット。
            comment.setId(result.getId());
            comment.setComment(result.getComment());
            comment.setContentId(result.getContentId());
            comment.setCreatedDate(result.getCreatedDate());
            comment.setUpdatedDate(result.getUpdatedDate());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * レコード追加
     */
    @Transactional
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);

        // コメントが入力されたらreportテーブルのupdated_dateも更新（ブラウザでの表示順を管理するため）。
        reportRepository.updateUpdatedDate(saveComment.getContentId(), LocalDateTime.now());
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        // データセット。
        comment.setId(reqComment.getId());
        comment.setComment(reqComment.getComment());
        comment.setContentId(reqComment.getContentId());
        comment.setCreatedDate(reqComment.getCreatedDate());
        comment.setUpdatedDate(reqComment.getUpdatedDate());
        return comment;
    }

    /*
     * 編集対象のレコードを取得(1件のみ)
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }

    /*
     * レコード削除
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}

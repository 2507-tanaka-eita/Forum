package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    // GetMapping ---------------
    /*
     * コメントの編集画面表示
     */
    @GetMapping("/editComment/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();

        // 編集対象のコメントを取得してformに格納
        CommentForm comment = commentService.editComment(id);

        // 画面遷移先を指定
        mav.setViewName("/editComment");

        // 準備した空のFormを保管
        mav.addObject("formModel", comment);
        return mav;
    }


    // PostMapping ---------------
    /*
     * 返信処理
     */
    @PostMapping("/comment/{contentId}")
    public ModelAndView commentContent(@PathVariable Integer contentId, @ModelAttribute("commentForm") CommentForm commentForm) {
        // commentFormに投稿idの情報をセット
        commentForm.setContentId(contentId);

        // 投稿をテーブルに格納
        commentService.saveComment(commentForm);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // DeleteMapping ---------------
    /*
     * 投稿の削除処理
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id){
        // 投稿をテーブルに格納
        commentService.deleteComment(id);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // PutMapping ---------------
    /*
     * コメントの編集処理
     */
    @PutMapping("/updateComment/{id}")
    public ModelAndView updateComment(@PathVariable Integer id, @ModelAttribute("formModel") CommentForm comment){
        // 編集内容(textarea)をentityにセット
        comment.setId(id);

        // 編集した投稿の更新
        commentService.saveComment(comment);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

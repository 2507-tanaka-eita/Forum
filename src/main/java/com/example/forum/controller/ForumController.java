package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    @Autowired
    CommentService commentService;

    // GetMapping ---------------
    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top(@ModelAttribute("commentForm") CommentForm commentForm) {
        ModelAndView mav = new ModelAndView();

        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);

        // 返信を全件取得
        List<CommentForm> commentData = commentService.findAllComment();
        // 返信データオブジェクトを保管
        mav.addObject("comments", commentData);

        // 画面遷移先を指定
        mav.setViewName("/top");
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();

        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();

        // 画面遷移先を指定
        mav.setViewName("/new");

        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 投稿の編集画面表示
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();

        // 編集対象の投稿を取得してformに格納
        ReportForm report = reportService.editReport(id);

        // 画面遷移先を指定
        mav.setViewName("/edit");

        // 準備した空のFormを保管
        mav.addObject("formModel", report);
        return mav;
    }

    // PostMapping ---------------
    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // DeleteMapping ---------------
    /*
     * 投稿の削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id){
        // 投稿をテーブルに格納
        reportService.deleteReport(id);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // PutMapping ---------------
    /*
     * 投稿の編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id, @ModelAttribute("formModel") ReportForm report){
        // 編集内容(textarea)をentityにセット
        report.setId(id);

        // 編集した投稿の更新
        reportService.saveReport(report);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

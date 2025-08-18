package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.FilterForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import com.example.forum.service.dto.FilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;


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
    @GetMapping("/")
    // ModelMap model：Flashスコープに入れた値が、リクエスト時にModelMapへ自動コピー。
    public ModelAndView top(@ModelAttribute("filterForm") FilterForm filterForm, ModelMap model) {
        ModelAndView mav = new ModelAndView();

        // Flashスコープの commentForm に値が返ってきているかのチェック。
        // 値なし＝初回アクセス（初期化）、値あり＝Flashスコープの値を代入（コメント入力に関するバリデーションメッセージあり）
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm());
        }

        // FilterForm → FilterDtoへの詰め替え（ここでLocalDate → LocalDateTimeに型変換）。
        // FormはLocalDate型で受け取っているため、条件式はnullの判定のみ。
        LocalDate startDate = filterForm.getStartDate();
        LocalDate endDate = filterForm.getEndDate();
        FilterDto filterDto = new FilterDto();

        if (startDate != null) {
            // startDateに時刻の情報を追加　＊atStartOfDay()：指定日の0時00分のLocalDateTimeを返す。
            filterDto.setStartDateTime(startDate.atStartOfDay());
        } else {
            // 初期値　＊2020-01-01 00:00:00
            filterDto.setStartDateTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        }

        if (endDate != null) {
            // endDateに時刻の情報を追加。
            filterDto.setEndDateTime(endDate.atTime(23, 59, 59));
        } else {
            // 初期値　＊現在の日時を取得
            filterDto.setEndDateTime(LocalDateTime.now());
        }

        // 投稿を取得（日付条件なし＝全件取得／日付条件あり＝条件に合わせて絞り込んで取得）。
        List<ReportForm> contentData = reportService.findAllReport(filterDto);
        // 投稿データオブジェクトを保管。
        mav.addObject("contents", contentData);

        // コメントを全件取得。
        List<CommentForm> commentData = commentService.findAllComment();
        // コメントのデータオブジェクトを保管。
        mav.addObject("comments", commentData);

        // 画面遷移先を指定。
        mav.setViewName("/top");
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();

        // form用の空のentityを準備。
        ReportForm reportForm = new ReportForm();

        // 画面遷移先を指定。
        mav.setViewName("/new");

        // 準備した空のFormを保管。
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 投稿の編集画面表示
     */
    @GetMapping("/edit/{id}")
    // @PathVariable：HTTPリクエストのURLパスの値を引数にバインド。
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();

        // 編集対象の投稿を取得してformに格納。
        ReportForm report = reportService.editReport(id);

        // 画面遷移先を指定。
        mav.setViewName("/edit");

        // 準備した空のFormを保管。
        mav.addObject("formModel", report);
        return mav;
    }

    // PostMapping ---------------
    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") @Valid ReportForm reportForm,BindingResult bindingResult){
        // 投稿入力に関するバリデーション。
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、フォームを再表示。
            ModelAndView mav = new ModelAndView("new");
            // 入力値を保持して表示。
            mav.addObject("formModel", reportForm);
            return mav;
        }

        // 投稿をテーブルに格納。
        reportService.saveReport(reportForm);

        // rootへリダイレクト。
        return new ModelAndView("redirect:/");
    }

    // DeleteMapping ---------------
    /*
     * 投稿の削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id){
        // レコード削除。
        reportService.deleteReport(id);

        // rootへリダイレクト。
        return new ModelAndView("redirect:/");
    }

    // PutMapping ---------------
    /*
     * 投稿の編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id, @ModelAttribute("formModel") @Valid ReportForm report,BindingResult bindingResult){
        // 投稿編集に関するバリデーション。
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、フォームを再表示。
            ModelAndView mav = new ModelAndView("edit");
            // 入力値を保持して表示。
            mav.addObject("formModel", report);
            return mav;
        }

        // 編集内容(textarea)をentityにセット。
        report.setId(id);

        // 編集した投稿の更新。
        reportService.saveReport(report);

        // rootへリダイレクト。
        return new ModelAndView("redirect:/");
    }
}

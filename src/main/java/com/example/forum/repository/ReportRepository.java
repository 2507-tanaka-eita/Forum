package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import com.example.forum.service.dto.FilterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    // JpaRepositoryを継承。
    // Service側でJpaRepositoryクラスのメソッド(findAllとか)を使用する場合は、このクラス内にメソッドの記述は不要。

    // メソッドを使ったSQL構文（reportテーブルのupdated_dateを対象にstartDate～endDateで絞り込みし、レコードを抽出）。
    List<Report> findByCreatedDateBetweenOrderByUpdatedDateDesc(LocalDateTime startDate, LocalDateTime endDate);

    // コメント内容をDBへ登録する際に、投稿（reportテーブル）のupdated_dateも更新。
    // 更新系の処理はメソッド名でのクエリ生成ができないため下記の記述に。
    @Modifying
    @Query("UPDATE Report r SET r.updatedDate = :updatedDate WHERE r.id = :id")
    void updateUpdatedDate(@Param("id") int id, @Param("updatedDate") LocalDateTime updatedDate);
}

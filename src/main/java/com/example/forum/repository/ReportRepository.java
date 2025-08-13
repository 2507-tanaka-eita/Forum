package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    // JpaRepositoryを継承。
    // Service側でJpaRepositoryクラスのメソッド(findAllとか)を使用する為、このクラス内にメソッドの記述はなし。
    // findAllメソッドを実行する事で「SELECT * FROM report」と同じような処理が実行される。
}

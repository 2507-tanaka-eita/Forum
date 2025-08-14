package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import com.example.forum.service.dto.FilterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    // JpaRepositoryを継承。
    // Service側でJpaRepositoryクラスのメソッド(findAllとか)を使用する場合は、このクラス内にメソッドの記述は不要。

    List<Report> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

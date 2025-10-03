package com.ss.breg.repository;

import com.ss.breg.model.AllInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllInfoRepository extends JpaRepository<AllInfo, Long> {
    
}

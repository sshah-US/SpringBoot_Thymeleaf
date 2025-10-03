package com.ss.breg.service;

import com.ss.breg.model.AllInfo;
import com.ss.breg.repository.AllInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllInfoService {

    private final AllInfoRepository repository;

    public AllInfoService(AllInfoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean registerUser(AllInfo info) {
        AllInfo saved = repository.save(info);
        return saved != null && saved.getId() != null;
    }

    // optional helper
    public AllInfo save(AllInfo info) {
        return repository.save(info);
    }
}

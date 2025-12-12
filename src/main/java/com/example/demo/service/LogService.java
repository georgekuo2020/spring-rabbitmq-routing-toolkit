package com.example.demo.service;

import com.example.demo.entity.LogRecord;
import com.example.demo.repository.LogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LogService {

    @Autowired
    LogRecordRepository logRecordRepository;

    @Transactional
    public void save(String message) {

        LogRecord logRecord = LogRecord.createInstance(message);

        logRecordRepository.save(logRecord);
    }
}

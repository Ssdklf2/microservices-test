package com.example.jobservice.controller;

import com.example.jobservice.models.Job;
import com.example.jobservice.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<Job> getList() {
        return jobService.getList();
    }

    @PostMapping
    public Job create(@RequestBody Job job) {
        return jobService.create(job);
    }
}

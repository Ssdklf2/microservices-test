package com.example.jobservice.controllers;

import com.example.jobservice.models.Job;
import com.example.jobservice.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Job> getList() {
        return jobService.getList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Job getJobByID(@PathVariable String id) {
        return jobService.getById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Job saveJob(@RequestBody Job job) {
        return jobService.save(job);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Job updateJob(@RequestBody Job job, @PathVariable String id) {
        return jobService.update(job, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable String id) {
        jobService.delete(id);
    }

}

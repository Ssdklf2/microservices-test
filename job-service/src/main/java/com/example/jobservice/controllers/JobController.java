package com.example.jobservice.controllers;

import com.example.jobservice.models.Job;
import com.example.jobservice.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getList() {
        return ResponseEntity.ok()
                .body(jobService.getList().stream()
                        .map(job -> addSelfLink(String.valueOf(job.getId()), job))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobByID(@PathVariable String id) {
        Job job = jobService.getById(id);
        addLinks(job.getId(), job);
        return ResponseEntity.ok().body(job);
    }

    @PostMapping
    public ResponseEntity<Job> saveJob(@RequestBody Job job) {
        Job jobResponse = jobService.save(job);
        String id = String.valueOf(jobResponse.getId());
        return new ResponseEntity<>(addSelfLink(id, jobResponse), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@RequestBody Job job, @PathVariable String id) {
        Job jobResponse = jobService.update(job, id);
        return ResponseEntity.ok().body(addSelfLink(id, jobResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Job> deleteJob(@PathVariable String id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static void addLinks(UUID uuid, Job job) {
        String id = String.valueOf(uuid);
        job.add(
                linkTo(methodOn(JobController.class).getJobByID(id)).withSelfRel(),
                linkTo(methodOn(JobController.class).updateJob(job, id)).withRel("update"),
                linkTo(methodOn(JobController.class).deleteJob(id)).withRel("delete")
        );
    }

    private Job addSelfLink(String id, Job jobResponse) {
        return jobResponse.add(linkTo(methodOn(JobController.class).getJobByID(id)).withSelfRel());
    }
}

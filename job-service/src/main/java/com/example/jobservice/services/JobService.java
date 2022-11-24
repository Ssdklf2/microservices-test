package com.example.jobservice.services;

import com.example.jobservice.models.Address;
import com.example.jobservice.models.Job;
import com.example.jobservice.repositories.AddressRepository;
import com.example.jobservice.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final AddressRepository addressRepository;


    public List<Job> getList() {
        return jobRepository.findAll();
    }

    @Transactional
    public Job create(Job job) {
        job.setId(UUID.randomUUID());
        Address address = job.getAddress();
        address.setId(UUID.randomUUID());
        Address addressSave = addressRepository.save(address);
        job.setAddress(addressSave);
        return jobRepository.save(job);
    }
}

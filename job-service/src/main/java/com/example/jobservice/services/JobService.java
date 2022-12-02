package com.example.jobservice.services;

import com.example.jobservice.exceptions.NotFoundException;
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
    public Job save(Job jorRequest) {
        jorRequest.setId(UUID.randomUUID());
        Address address = jorRequest.getAddress();
        address.setId(UUID.randomUUID());
        Address addressSave = addressRepository.save(address);
        jorRequest.setAddress(addressSave);
        return jobRepository.save(jorRequest);
    }

    public Job update(Job jobRequest, String id) {
        Job job = getJob(id);
        Address addressRequest = jobRequest.getAddress();
        Address address = job.getAddress();
        address
                .setCity(addressRequest.getCity())
                .setCountry(addressRequest.getCountry())
                .setHouseNumber(addressRequest.getHouseNumber())
                .setStreetName(addressRequest.getStreetName());
        job
                .setTitle(jobRequest.getTitle())
                .setSalary(jobRequest.getSalary())
                .setDescription(jobRequest.getDescription())
                .setAddress(address);
        return jobRepository.save(job);
    }

    public void delete(String id) {
        Job job = getJob(id);
        jobRepository.deleteById(job.getId());
    }

    public Job getById(String id) {
        return getJob(id);
    }

    private Job getJob(String id) {
        return jobRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(id));
    }
}

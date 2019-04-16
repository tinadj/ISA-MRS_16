package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.repository.BranchOfficeRepository;

import java.util.List;

@Service
public class BranchOfficeService {

    @Autowired
    private BranchOfficeRepository repository;

    public BranchOffice findOne(Integer id) {
        return repository.getOne(id);
    }
    public List<BranchOffice> findAll() {
        return repository.findAll();
    }
    public BranchOffice create(BranchOffice branchOffice) {
        return repository.save(branchOffice);
    }
    public BranchOffice update(BranchOffice branchOffice) {
        return repository.save(branchOffice);
    }
    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

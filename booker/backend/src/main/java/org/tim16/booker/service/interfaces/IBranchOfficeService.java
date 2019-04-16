package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.rent_a_car.BranchOffice;

import java.util.List;

public interface IBranchOfficeService {
    public BranchOffice findOne(Integer id);
    public List<BranchOffice> findAll();
    public BranchOffice create(BranchOffice vehicle);
    public BranchOffice update(BranchOffice vehicle);
    public void remove(Integer id);
}

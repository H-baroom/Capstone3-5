package com.example.capstone3.Service;


import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Admin;
import com.example.capstone3.Model.Company;
import com.example.capstone3.Model.MaintenanceExpert;
import com.example.capstone3.OutDTO.AdminOutDTO;
import com.example.capstone3.Repository.AdminRepository;
import com.example.capstone3.Repository.CompanyRepository;
import com.example.capstone3.Repository.MaintenanceExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final CompanyRepository companyRepository;
    private final MaintenanceExpertRepository maintenanceExpertRepository;

    public List<AdminOutDTO> getAllAdmins(){
        List<Admin> admins = adminRepository.findAll();
        List<AdminOutDTO> adminOutDTOS = new ArrayList<>();

        for(Admin admin:admins){
            AdminOutDTO adminOutDTO = new AdminOutDTO(admin.getName(),admin.getEmail());
            adminOutDTOS.add(adminOutDTO);
        }
        return adminOutDTOS;
    }


    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id,Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin==null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setName(admin.getName());
        adminRepository.save(oldAdmin);
    }


    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if(admin==null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }


    public void approveCompany(Integer adminId,Integer companyId){
        Admin admin = adminRepository.findAdminById(adminId);
        Company company = companyRepository.findCompanyById(companyId);
        if(admin==null||company==null){
            throw new ApiException("Can't approve");
        }
        if(!company.getIsApproved()){
            company.setIsApproved(true);
            companyRepository.save(company);
        }else throw new ApiException("Company is already approved");


    }

    public void approveExpert(Integer adminId,Integer expertId){
        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertById(expertId);
        Admin admin = adminRepository.findAdminById(adminId);
        if(expert ==null|| admin==null){
            throw new ApiException("Can't approve");
        }

        if(!expert.getIsApproved()){
            expert.setIsApproved(true);
            maintenanceExpertRepository.save(expert);
        }else throw new ApiException("Maintenance Expert is already approved");
    }



}

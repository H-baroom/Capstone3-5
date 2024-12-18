package com.example.capstone3.Service;


import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Event;
import com.example.capstone3.OutDTO.CompanyOutDTO;
import com.example.capstone3.Repository.CompanyRepository;
import com.example.capstone3.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.capstone3.Model.Company;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EventRepository eventRepository;

    public List<CompanyOutDTO> getCompanies(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyOutDTO> companyDTOS= new ArrayList<>();
        for(Company company : companies){
            CompanyOutDTO companyOutDTO = new CompanyOutDTO(company.getName(),company.getEmail(),company.getContactInfo(),company.getIsApproved());
            companyDTOS.add(companyOutDTO);
        }
        return companyDTOS;
    }



    public void addCompany(Company company){
        companyRepository.save(company);
    }


    public void updateCompany(Integer id,Company company){
        Company oldCompany=companyRepository.findCompanyById(id);
        if(oldCompany==null){
            throw new ApiException("Company not found");
        }
        oldCompany.setName(company.getName());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setPassword(company.getPassword());
        oldCompany.setContactInfo(company.getContactInfo());
        oldCompany.setIsApproved(company.getIsApproved());
        companyRepository.save(oldCompany);

    }


    public void deleteCompany(Integer id){
        Company company=companyRepository.findCompanyById(id);
        if(company==null){
            throw new ApiException("Company not found");
        }
        companyRepository.delete(company);
    }



}

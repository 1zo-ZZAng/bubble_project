package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Admin;
import com.example.entity.Customer;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {


}

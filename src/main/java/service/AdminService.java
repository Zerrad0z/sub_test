package service;

import entities.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Long adminId);
    Admin saveAdmin(Admin admin);
    void deleteAdmin(Long adminId);
}


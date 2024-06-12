package com.java5.assignment.util;

import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;

public class AutoSetTime {

    @PrePersist
    public void onCreate(BaseEntity entity) {
        entity.setCreateAt(getCurrentTime());
        entity.setModifyAt(getCurrentTime());
        entity.setCreateBy(getNameStaff());
    }

    @PreUpdate
    public void onUpdate(BaseEntity entity) {
        entity.setModifyAt(getCurrentTime());
        entity.setModifyBy(getNameStaff());
    }

    public Long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public String getNameStaff() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else return "Systerm filled data";
    }
}

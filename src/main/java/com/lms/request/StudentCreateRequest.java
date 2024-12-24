package com.lms.request;

import com.lms.models.AccountStatus;
import com.lms.models.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotBlank
    private String name;

    private String contact;

    private String address;

    @NotBlank
    private String email;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .accountStatus(AccountStatus.ACTIVE)
                .address(this.address)
                .contact(this.contact)
                .email(this.email)
                .build();
    }
}

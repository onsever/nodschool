package com.onurcansever.nodschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "Name field must not be blank.")
    @Size(min = 3, message = "Name field must be at least 3 characters long.")
    private String name;

    @NotBlank(message = "Mobile number field must not be blank.")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number field must be 10 digits.")
    private String mobileNum;

    @NotBlank(message = "Email field must not be blank.")
    @Email(message = "Email field must be a valid email address.")
    private String email;

    @NotBlank(message = "Subject field must not be blank.")
    @Size(min = 5, message = "Subject field must be at least 5 characters long.")
    private String subject;

    @NotBlank(message = "Message field must not be blank.")
    @Size(min = 10, message = "Message field must be at least 10 characters long.")
    private String message;

    private String status;
}

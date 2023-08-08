package com.onurcansever.nodschool.model;

import com.onurcansever.nodschool.annotations.FieldsValueMatch;
import com.onurcansever.nodschool.annotations.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@FieldsValueMatch.List(value = {
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int userId;

    @NotBlank(message = "Name must not be empty!")
    @Size(min = 3, message = "Name must be at least 3 characters!")
    private String name;

    @NotBlank(message = "Surname must not be empty!")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Email must be valid!")
    private String email;

    @NotBlank(message = "Confirm Email must not be empty!")
    @Email(message = "Confirm Email must be valid!")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password must not be empty!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    @PasswordValidator
    private String password;

    @NotBlank(message = "Confirm Password must not be empty!")
    @Size(min = 6, message = "Confirm Password must be at least 6 characters!")
    @Transient
    private String confirmPassword;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;
}

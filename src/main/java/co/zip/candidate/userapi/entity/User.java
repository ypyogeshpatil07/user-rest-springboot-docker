package co.zip.candidate.userapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "USER_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "User name should not be blank or null")
    private String name;
    @Email(message = "Please provide valid email id")
    @NotBlank(message = "Email id should not be blank or null")
    private String email;
    @NotNull(message = "Salary cannot be null or blank")
    @Positive(message = "Salary cannot be negative")
    private Double salary;
    @NotNull(message = "Monthly Expense cannot be blank or null")
    @Positive(message = "Monthly Expense cannot be negative")
    private Double expenses;




}

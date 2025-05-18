package tech.mobl3lm.digitalbanking.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
private String customer_id;
private String name;
private String email;
private String password;
private String phone;
private String address;
private String city;
}

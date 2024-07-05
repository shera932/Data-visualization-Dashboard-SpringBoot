package dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @Column(name="user_id",length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(name="email",length = 255)
    private String email;

    @Column(name="password",length = 255)
    private String password;

}

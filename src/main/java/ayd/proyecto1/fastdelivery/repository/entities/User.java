package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "usuario")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String name;

    private String username;

    private String email;

    private String password;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "direccion")
    private String address;

    @Column(name = "autenticacion")
    private Boolean autentication = Boolean.TRUE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol",referencedColumnName = "id")
    private Role role;


}

package com.mdprasadeng.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@NamedQueries(
    {
        @NamedQuery(
            name = "findUserByPhoneNumber",
            query = "Select u from UserEntity u where u.phoneNumber = :phoneNumber"
        )
    }
)
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

}

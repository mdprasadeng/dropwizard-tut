package com.mdprasadeng.hibernate;

import com.google.inject.Inject;

import com.mdprasadeng.hibernate.entities.UserEntity;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

import io.dropwizard.hibernate.AbstractDAO;

public class UserEntityDAO extends AbstractDAO<UserEntity> {

  @Inject
  public UserEntityDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public UserEntity create(UserEntity user) {
    return super.persist(user);
  }

  public Optional<UserEntity> findByPhoneNumber(String phoneNumber) {
    Query query = namedQuery("findUserByPhoneNumber");
    query.setString("phoneNumber", phoneNumber);
    List<UserEntity> users = query.list();
    if (users.size() == 1) {
      return Optional.of(users.get(0));
    } else {
      return Optional.empty();
    }
  }

}

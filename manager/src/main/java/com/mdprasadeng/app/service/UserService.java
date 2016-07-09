package com.mdprasadeng.app.service;

import com.mdprasadeng.app.models.User;
import com.mdprasadeng.hibernate.UserEntityDAO;
import com.mdprasadeng.hibernate.entities.UserEntity;

import java.util.Optional;

public class UserService {

  private final UserEntityDAO userEntityDAO;

  public UserService(UserEntityDAO userEntityDAO) {
    this.userEntityDAO = userEntityDAO;
  }

  public void saveUser(User user) {
    Optional<UserEntity> userEntityOptional = userEntityDAO.findByPhoneNumber(user.getPhoneNumber());
    if (userEntityOptional.isPresent()) {
      userEntityOptional.get().setEmail(user.getEmailId());
    } else {
      userEntityDAO.create(userToUserEntity(user));
    }
  }

  public Optional<User> fetchUser(String phoneNumber) {
    Optional<UserEntity> userEntityOptional = userEntityDAO.findByPhoneNumber(phoneNumber);
    return userEntityOptional.map(e -> userEntityToUser(e));
/*
    if (userEntityOptional.isPresent()) {
      return userEntityToUser(userEntityOptional.get());
    } else {
      return Optional.empty();
    }
*/
  }

  private User userEntityToUser(UserEntity userEntity) {
    User user = new User();
    user.setEmailId(userEntity.getEmail());
    user.setPhoneNumber(userEntity.getPhoneNumber());
    return user;
  }

  private UserEntity userToUserEntity(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(user.getEmailId());
    userEntity.setPhoneNumber(user.getPhoneNumber());
    return userEntity;
  }
}

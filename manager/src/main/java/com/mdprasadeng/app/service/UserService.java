package com.mdprasadeng.app.service;

import com.google.inject.Inject;

import com.mdprasadeng.app.models.User;
import com.mdprasadeng.hibernate.UserEntityDAO;
import com.mdprasadeng.hibernate.entities.UserEntity;

import java.util.Optional;

import javax.ws.rs.InternalServerErrorException;

public class UserService {

  private final UserEntityDAO userEntityDAO;
  private final PhoneNetworkClientAdapter pnAdapter;

  @Inject
  public UserService(UserEntityDAO userEntityDAO,
                     PhoneNetworkClientAdapter pnAdapter) {
    this.userEntityDAO = userEntityDAO;
    this.pnAdapter = pnAdapter;
  }

  public void saveUser(User user) {
    Optional<UserEntity> userEntityOptional = userEntityDAO.findByPhoneNumber(user.getPhoneNumber());
    if (userEntityOptional.isPresent()) {
      userEntityOptional.get().setEmail(user.getEmailId());
    } else {
      userEntityDAO.create(userToUserEntity(user));
    }
    if (user.getNetwork() != null) {
      try {
        pnAdapter.savePhoneNetwork(user.getPhoneNumber(), user.getNetwork());
      } catch (Exception e) {
        throw new InternalServerErrorException();
      }
    }
  }

  public Optional<User> fetchUser(String phoneNumber) {
    Optional<UserEntity> userEntityOptional = userEntityDAO.findByPhoneNumber(phoneNumber);
    if (userEntityOptional.isPresent()) {
      User user = userEntityToUser(userEntityOptional.get());
      Optional<User.PhoneNetwork> userPNOptional = fetchNetwork(phoneNumber);
      if (userPNOptional.isPresent()) {
        user.setNetwork(userPNOptional.get());
      }
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }

  private Optional<User.PhoneNetwork> fetchNetwork(String phoneNumber) {
    try {
      return pnAdapter.getPhoneNetwork(phoneNumber);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
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

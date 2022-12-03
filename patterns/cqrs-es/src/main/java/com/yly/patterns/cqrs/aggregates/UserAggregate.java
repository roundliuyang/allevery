package com.yly.patterns.cqrs.aggregates;


import com.yly.patterns.cqrs.commands.CreateUserCommand;
import com.yly.patterns.cqrs.commands.UpdateUserCommand;
import com.yly.patterns.cqrs.repository.UserWriteRepository;
import com.yly.patterns.domain.User;


public class UserAggregate {

    private UserWriteRepository writeRepository;

    public UserAggregate(UserWriteRepository repository) {
        this.writeRepository = repository;
    }

    public User handleCreateUserCommand(CreateUserCommand command) {
        User user = new User(command.getUserId(), command.getFirstName(), command.getLastName());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }

    public User handleUpdateUserCommand(UpdateUserCommand command) {
        User user = writeRepository.getUser(command.getUserId());
        user.setAddresses(command.getAddresses());
        user.setContacts(command.getContacts());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }

}

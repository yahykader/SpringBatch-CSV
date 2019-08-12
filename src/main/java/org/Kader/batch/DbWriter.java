package org.Kader.batch;

import org.Kader.Entities.User;
import org.Kader.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DbWriter implements ItemWriter<User> {

  @Autowired
  private UserRepository userRepository;
  private Object User;

  @Override
    public void write(List<? extends User> users) throws Exception {

         System.out.println("Data saved for users :"+users);
         userRepository.saveAll(users);
    }
}

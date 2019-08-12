package org.Kader.batch;

import org.Kader.Entities.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User, User> {

    private static  final Map<String,String> DEPT_NAMES =new HashMap<>();

    public Processor() {
        DEPT_NAMES.put("001","Paris 18,75018");
        DEPT_NAMES.put("002","Paris 19,75019");
        DEPT_NAMES.put("003","Paris 17,75017");
        DEPT_NAMES.put("004","Paris 16,75016");
        DEPT_NAMES.put("005","Paris 15,75015");
    }

    @Override
    public User process(User user) throws Exception {

        String codeDept=user.getDept();
        String code=DEPT_NAMES.get(codeDept);
        user.setDept(code);
        System.out.println(String.format("converted [%s] to [%s]" +codeDept,code));
        return user;
    }
}

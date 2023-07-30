package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;


    public Boolean checkUserId(Users users) throws Exception {
        try{
            return usersRepository.existsByUserId(users.getUserId());
        } catch (Exception e){
            throw new Exception();
        }
    }
    @Transactional
    public void signUp(Users users) throws Exception {
        try{
            usersRepository.save(users);
        } catch (Exception e){
            throw new Exception();
        }
    }

    @Transactional
    public Users signIn(Users users) throws Exception {
        try{
            return usersRepository.findOneByUserId(users.getUserId());
        } catch (Exception e){
            throw new Exception();
        }
    }
}

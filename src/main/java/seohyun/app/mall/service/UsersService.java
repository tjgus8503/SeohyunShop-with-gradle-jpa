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
            throw new Exception(e);
        }
    }
    @Transactional
    public void signUp(Users users) throws Exception {
        try{
            usersRepository.save(users);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Users findUserId(String userId) throws Exception {
        try{
            return usersRepository.findOneByUserId(userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Users checkUserIdAndPassword(Users users) throws Exception {
        try{
            return usersRepository.findByUserIdAndPassword(users.getUserId(), users.getPassword());
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateUser(Users users) throws Exception {
        try{
            usersRepository.save(users);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void unRegister(Users users) throws Exception {
        try{
            usersRepository.deleteById(users.getId());
        } catch (Exception e){
            throw new Exception(e);
        }
    }
    @Transactional
    public int updateRole(String userId) throws Exception {
        try{
            return usersRepository.updateRole(userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}

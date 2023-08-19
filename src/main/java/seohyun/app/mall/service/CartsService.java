package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.repository.CartsRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartsService {
    private final CartsRepository cartsRepository;

    @Transactional
    public void creatCart(Carts carts) throws Exception {
        try{
            cartsRepository.save(carts);
        } catch (Exception e){
            throw new Exception(e);
            }
        }

    @Transactional
    public void updateCart(Carts carts) throws Exception {
        try{
            cartsRepository.save(carts);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteCart(String id, String decoded) throws Exception {
        try{
            cartsRepository.deleteByIdAndUserId(id, decoded);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<Carts> getByUserId(String userId, Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return cartsRepository.findByUserId(userId, pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}


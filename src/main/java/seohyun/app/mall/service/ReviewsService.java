package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Reviews;
import seohyun.app.mall.repository.ReviewsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Transactional
    public void createReview(Reviews reviews) throws Exception {
        try{
            reviewsRepository.save(reviews);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Reviews getById(String id) throws Exception {
        try{
            return reviewsRepository.findOneById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateReview(Reviews reviews) throws Exception {
        try{
            reviewsRepository.save(reviews);
        } catch(Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteReview(String id, String userId) throws Exception {
        try{
            reviewsRepository.deleteByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}

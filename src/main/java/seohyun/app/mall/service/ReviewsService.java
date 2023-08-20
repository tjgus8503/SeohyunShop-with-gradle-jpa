package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Comments;
import seohyun.app.mall.models.ReviewComments;
import seohyun.app.mall.models.Reviews;
import seohyun.app.mall.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final ReviewCommentsRepository reviewCommentsRepository;

    @Transactional
    public void createReview(Reviews reviews) throws Exception {
        try{
            reviewsRepository.save(reviews);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Reviews getByIdAndUserId(String id, String userId) throws Exception {
        try{
            return reviewsRepository.findOneByIdAndUserId(id, userId);
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
    public void deleteReview(String id) throws Exception {
        try{
            reviewsRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteReviewByProductId(String id) throws Exception {
        try{
            reviewsRepository.deleteAllByProductId(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void createComment(ReviewComments reviewComments) throws Exception {
        try{
            reviewCommentsRepository.save(reviewComments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public ReviewComments findByIdAndUserId(String id, String userId) throws Exception {
        try{
            return reviewCommentsRepository.findOneByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateComment(ReviewComments reviewComments) throws Exception {
        try{
            reviewCommentsRepository.save(reviewComments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteComment(String id) throws Exception {
        try{
            reviewCommentsRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteReviewCommentByProductId(String id) throws Exception {
        try{
            reviewCommentsRepository.deleteAllByProductId(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }


    public ReviewComments getByReviewsId(String reviewsId) throws Exception {
        try{
            return reviewCommentsRepository.findOneByReviewsId(reviewsId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}

package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Comments;
import seohyun.app.mall.repository.CommentsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {
    private final CommentsRepository commentsRepository;

    @Transactional
    public void createComment(Comments comments) throws Exception {
        try{
            commentsRepository.save(comments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}

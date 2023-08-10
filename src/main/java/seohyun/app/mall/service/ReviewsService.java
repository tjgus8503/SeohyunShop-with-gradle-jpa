package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.repository.ReviewsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewsService {
    private ReviewsRepository reviewsRepository;

}

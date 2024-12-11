package orderProcessingSystem.services.impl;

import orderProcessingSystem.entity.Good;
import orderProcessingSystem.repository.GoodRepository;
import orderProcessingSystem.services.GoodService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    private GoodRepository goodRepository;

    public GoodServiceImpl(GoodRepository goodRepository) {
        super();
        this.goodRepository = goodRepository;
    }

    private void checkDuplicate(Good good){
        if (goodRepository.findByNazva(good.getNazva()) != null) {
            throw new IllegalArgumentException("Good with this name already exists.");
        }

        if (goodRepository.findByArticle(good.getArticle()) != null) {
            throw new IllegalArgumentException("Good with this article already exists.");
        }

    }

    @Override
    public List<Good> getAllGoods() {
        return goodRepository.findAll();
    }

    @Override
    public void addGood(Good good) {
        checkDuplicate(good);
        goodRepository.save(good);
    }

    @Override
    public void updateGood(long id, Good department) {
        Good old = getGoodById(id);

        if (goodRepository.existsGoodByNazvaAndNotId(department.getNazva(), id)) {
            throw new IllegalArgumentException("Good with this name already exists");
        }

        if (goodRepository.existsGoodByArticleAndNotId(department.getArticle(), id)) {
            throw new IllegalArgumentException("Good with this article already exists");
        }

        department.setId(id);
        goodRepository.save(department);
    }


    @Override
    public void deleteGood(Long id) {
        goodRepository.deleteById(id);
    }

    @Override
    public Good getGoodById(Long id) {
        return goodRepository.findById(id).get();
    }
}

package orderProcessingSystem.services;

import orderProcessingSystem.entity.Good;

import java.util.List;

public interface GoodService{
    List<Good> getAllGoods();

    void addGood(Good good);

    void updateGood(long id, Good good);

    void deleteGood(Long id);

    Good getGoodById(Long id);
}

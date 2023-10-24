package com.coderkan.services;

import com.coderkan.models.Asset;
import com.coderkan.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PricingEngineService {

    @Autowired
    private AssetService assetService;

    static final Logger LOGGER =
            Logger.getLogger(PricingEngineService.class.getName());

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void updatePrice() throws InterruptedException {

        List<Asset> assets = assetService.getAll();

        if(assets.size() > 0) {
            int randomNumberAsset = getRandomNumber(0, assets.size());
            Asset randomAsset = assets.get(randomNumberAsset);
            int randomNumberPrice = getRandomNumber(1, 10);
            BigDecimal currentPrice = randomAsset.getUsdPrice();
            BigDecimal newPrice = new BigDecimal(0);

            if (randomNumberPrice % 2 == 0) {
                LOGGER.info("Par newUsdPrice " + " "+ randomNumberPrice + " "+
                        randomAsset.getUsdPrice());
                LOGGER.info("currentPrice " + currentPrice);
                BigDecimal finalPrice = currentPrice.add(new BigDecimal(randomNumberPrice)).setScale(2);
                LOGGER.info("finalPrice " + finalPrice);
                newPrice = finalPrice;
            } else {
                LOGGER.info("Impar newUsdPrice " + " "+ randomNumberPrice + " "+
                        randomAsset.getUsdPrice());
                LOGGER.info("currentPrice " + currentPrice);
                BigDecimal finalPrice = currentPrice.subtract(new BigDecimal(randomNumberPrice));
                LOGGER.info("finalPrice " + finalPrice);
                newPrice = finalPrice;
            }
            randomAsset.setUsdPrice(newPrice);

            assetService.update(randomAsset);

            LOGGER.info("computing price at " +
                    LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            LOGGER.info("asset " +
                    randomAsset.getName());
            LOGGER.info("newUsdPrice " +
                    randomAsset.getUsdPrice());
        } else{
            LOGGER.info("assets size = 0" );
        }

    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
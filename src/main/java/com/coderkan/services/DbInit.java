package com.coderkan.services;

import com.coderkan.models.Asset;
import com.coderkan.models.Operation;
import com.coderkan.models.User;
import com.coderkan.models.Wallet;
import com.coderkan.repositories.AssetRepository;
import com.coderkan.repositories.OperationRepository;
import com.coderkan.repositories.UserRepository;
import com.coderkan.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Component
public class DbInit {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private OperationRepository operationRepository;

    private static final Logger LOGGER
                = Logger.getLogger(DbInit.class.getName());

    @PostConstruct
    private void init(){
        //Assets
        Asset assetBitcoin = new Asset();
        assetBitcoin.setName("Bitcoin");
        assetBitcoin.setSymbol("BTC");
        assetBitcoin.setUsdPrice(new BigDecimal(29509.60));
        assetRepository.save(assetBitcoin);

        Asset assetEthereum = new Asset();
        assetEthereum.setName("Ethereum");
        assetEthereum.setSymbol("ETH");
        assetEthereum.setUsdPrice(new BigDecimal(1608.49));
        assetRepository.save(assetEthereum);

        Asset assetSolana = new Asset();
        assetSolana.setName("Solana");
        assetSolana.setSymbol("SOL");
        assetSolana.setUsdPrice(new BigDecimal(26.77));
        assetRepository.save(assetSolana);

        Asset assetPolygon = new Asset();
        assetPolygon.setName("Polygon");
        assetPolygon.setSymbol("MATIC");
        assetPolygon.setUsdPrice(new BigDecimal(0.53));
        assetRepository.save(assetPolygon);

        //Users
        User userAlice = new User();
        userAlice.setName("Alice");
        userAlice.setCity("New York");
        userAlice.setAddress("Palace Avenue");
        userAlice.setPostalCode("01125");
        userAlice.setCountry("United States");
        userRepository.save(userAlice);

        User userBob = new User();
        userBob.setName("Bob");
        userBob.setCity("Bacelona");
        userBob.setAddress("Calle Independecia");
        userBob.setPostalCode("08022");
        userBob.setCountry("Spain");
        userRepository.save(userBob);

        User userCarlos = new User();
        userCarlos.setName("Carlos");
        userCarlos.setCity("Sao Paulo");
        userCarlos.setAddress("Avenida Paulista");
        userCarlos.setPostalCode("09866001");
        userCarlos.setCountry("Brazil");
        userRepository.save(userCarlos);

        //Wallets
        Wallet walletBitcoinAlice = new Wallet();
        walletBitcoinAlice.setName("walletBitcoinAlice");
        walletBitcoinAlice.setAmount(10);
        walletBitcoinAlice.setOwner(userAlice);
        walletBitcoinAlice.setAsset(assetBitcoin);
        walletRepository.save(walletBitcoinAlice);

        Wallet walletEthereumAlice = new Wallet();
        walletEthereumAlice.setName("walletEthereumAlice");
        walletEthereumAlice.setAmount(5);
        walletEthereumAlice.setOwner(userAlice);
        walletEthereumAlice.setAsset(assetEthereum);
        walletRepository.save(walletEthereumAlice);

        Wallet walletBitcoinBob = new Wallet();
        walletBitcoinBob.setName("walletBitcoinBob");
        walletBitcoinBob.setAmount(20);
        walletBitcoinBob.setOwner(userBob);
        walletBitcoinBob.setAsset(assetBitcoin);
        walletRepository.save(walletBitcoinBob);

        Wallet walletBitcoinCarlos = new Wallet();
        walletBitcoinCarlos.setName("walletBitcoinCarlos");
        walletBitcoinCarlos.setAmount(15);
        walletBitcoinCarlos.setOwner(userCarlos);
        walletBitcoinCarlos.setAsset(assetBitcoin);
        walletRepository.save(walletBitcoinCarlos);

        Wallet walletSolanaCarlos = new Wallet();
        walletSolanaCarlos.setName("walletSolanaCarlos");
        walletSolanaCarlos.setAmount(15);
        walletSolanaCarlos.setOwner(userCarlos);
        walletSolanaCarlos.setAsset(assetSolana);
        walletRepository.save(walletSolanaCarlos);

        //Operations
        Operation operationAliceToBobBitcoin = new Operation();
        operationAliceToBobBitcoin.setSourceWallet(walletBitcoinAlice);
        operationAliceToBobBitcoin.setTargetWallet(walletBitcoinBob);
        operationAliceToBobBitcoin.setAmount(2);
        operationAliceToBobBitcoin.setAsset(assetBitcoin);
        operationRepository.save(operationAliceToBobBitcoin);

        Operation operationBobToCarlosBitcoin = new Operation();
        operationBobToCarlosBitcoin.setSourceWallet(walletBitcoinBob);
        operationBobToCarlosBitcoin.setTargetWallet(walletBitcoinCarlos);
        operationBobToCarlosBitcoin.setAmount(5);
        operationBobToCarlosBitcoin.setAsset(assetBitcoin);
        operationRepository.save(operationBobToCarlosBitcoin);

        LOGGER.info("Asset DB initialized (PostContruct invoked)");

    }

    @PreDestroy
    private void destroy(){

        operationRepository.deleteAll();
        walletRepository.deleteAll();
        assetRepository.deleteAll();
        userRepository.deleteAll();


        LOGGER.info("Asset DB destroyed (PreDestroy invoked)");

    }
}

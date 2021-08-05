package cn.tedu.sdstorage.service;

public interface StorageService {
    void decrease(Long productId, Integer count) throws InterruptedException, Exception;
}

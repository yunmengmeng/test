package cn.tedu.sdstorage.mapper;

import cn.tedu.sdstorage.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface StorageMapper extends BaseMapper<Storage> {
    void decrease(Long productId, Integer count);
}

package cn.tedu.sdorder.mapper;

import cn.tedu.sdorder.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface OrderMapper extends BaseMapper {
    void create(Order order);
}

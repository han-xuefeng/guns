package TTT;

import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import TTT.IMoocUserTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author woods
 * @since 2019-11-28
 */
@Service
public class MoocUserTServiceImpl extends ServiceImpl<MoocUserTMapper, MoocUserT> implements IMoocUserTService {

}

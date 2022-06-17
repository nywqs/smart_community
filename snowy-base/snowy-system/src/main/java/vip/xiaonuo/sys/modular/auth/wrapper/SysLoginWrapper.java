package vip.xiaonuo.sys.modular.auth.wrapper;

import cn.hutool.core.util.ObjectUtil;
import vip.xiaonuo.core.context.constant.ConstantContextHolder;
import vip.xiaonuo.core.pojo.base.wrapper.BaseWrapper;
import vip.xiaonuo.core.pojo.login.SysLoginUser;
import vip.xiaonuo.core.util.CryptogramUtil;
import vip.xiaonuo.sys.modular.user.result.SysUserResult;
import java.util.HashMap;
import java.util.Map;


/**
 * 登录成功之后脱敏某些字段的类
 *
 * @author yubaoshan
 */
public class SysLoginWrapper implements BaseWrapper<SysLoginUser> {

    @Override
    @SuppressWarnings("all")
    public Map<String, Object> doWrap(SysLoginUser sysLoginUser) {
        Map<String, Object> map = new HashMap<>();

        // 是否开启用户表字段加解密，如果是被加密的，返回列表时需要脱敏
        if (ConstantContextHolder.getCryptogramConfigs().getFieldEncDec()) {
            if (ObjectUtil.isNotEmpty(sysLoginUser.getPhone())) {
                map.put("phone", CryptogramUtil.doDecrypt(sysLoginUser.getPhone()));
            }
        }

        // 下面这里其他的字段，需要做翻译脱敏的，下面处理即可

        return map;
    }

}

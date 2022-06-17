/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dbs.core.dao.sqls;

import vip.xiaonuo.core.enums.DbIdEnum;

/**
 * 异构sql获取
 *
 * @author xuyuxiang
 * @date 2019-07-16-13:13
 */
public abstract class AbstractSql {

    /**
     * 获取异构sql
     *
     * @author xuyuxiang
     * @date 2019-07-16 13:14
     */
    public String getSql(String jdbcUrl) {
        if (jdbcUrl.contains(DbIdEnum.ORACLE.getName())) {
            return oracle();
        } else if (jdbcUrl.contains(DbIdEnum.PG_SQL.getName())) {
            return pgSql();
        } else if (jdbcUrl.contains(DbIdEnum.MS_SQL.getName())) {
            return sqlServer();
        } else if (jdbcUrl.contains(DbIdEnum.DM_SQL.getName())) {
            return dm();
        } else if (jdbcUrl.contains(DbIdEnum.KINGBASE_ES.getName())) {
            return kingbase();
        } else {
            return mysql();
        }
    }

    /**
     * 获取mysql
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String mysql();

    /**
     * 获取sqlServer
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String sqlServer();

    /**
     * 获取pgSql
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String pgSql();

    /**
     * 获取oracle
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String oracle();

    /**
     * 获取达梦
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String dm();

    /**
     * 获取人大金仓
     *
     * @return java.lang.String
     * @author xuyuxiang
     * @date 2020/9/1 14:50
     **/
    protected abstract String kingbase();

}

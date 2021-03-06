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
package vip.xiaonuo.dbs.modular.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.dbs.modular.param.DatabaseInfoParam;
import vip.xiaonuo.dbs.modular.service.DatabaseInfoService;

import javax.annotation.Resource;


/**
 * 数据库信息表控制器
 *
 * @author yubaoshan
 * @date 2019-06-15 17:05:23
 */
@RestController
public class DatabaseInfoController {

    @Resource
    private DatabaseInfoService databaseInfoService;

    /**
     * 查询列表
     *
     * @author yubaoshan
     * @date 2019-06-15
     */
    @GetMapping("/databaseInfo/page")
    public ResponseData page(DatabaseInfoParam databaseInfoParam) {
        return ResponseData.success(databaseInfoService.page(databaseInfoParam));
    }

    /**
     * 新增接口
     *
     * @author yubaoshan
     * @date 2019-06-15
     */
    @PostMapping("/databaseInfo/add")
    public ResponseData add(@RequestBody @Validated(DatabaseInfoParam.add.class) DatabaseInfoParam databaseInfoParam) {
        databaseInfoService.add(databaseInfoParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author yubaoshan
     * @date 2019-06-15
     */
    @PostMapping("/databaseInfo/edit")
    public ResponseData edit(@RequestBody @Validated(DatabaseInfoParam.edit.class) DatabaseInfoParam databaseInfoParam) {
        databaseInfoService.edit(databaseInfoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author yubaoshan
     * @date 2019-06-15
     */
    @PostMapping("/databaseInfo/delete")
    public ResponseData delete(@RequestBody @Validated(DatabaseInfoParam.delete.class) DatabaseInfoParam databaseInfoParam) {
        databaseInfoService.delete(databaseInfoParam);
        return ResponseData.success();
    }

}



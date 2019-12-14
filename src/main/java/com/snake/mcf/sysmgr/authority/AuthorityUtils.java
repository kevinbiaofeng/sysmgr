package com.snake.mcf.sysmgr.authority;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthorityUtils
 * @Author 大帅
 * @Date 2019/7/22 14:59
 */
public class AuthorityUtils {

    private static Map<String,Integer> AUTHORITY_MAP = new HashMap<>();

    /**
     * [{
     * 	"id": "06bbcde3c2b94743a2e6296896091bc7",
     * 	"text":"新增权限",
     * 	"desc":"具有新增权限"
     * },{
     * 	"id": "eadc19ecba59431892bd3eb2a55d9a16",
     * 	"text":"修改权限",
     * 	"desc":"具有修改权限"
     * },{
     * 	"id": "df6b5fa5cf224d3787c7b4197829fdac",
     * 	"text":"删除权限",
     * 	"desc":"具有删除权限"
     * }]
     * 0 -- 查询
     * 1 -- 新增
     * 2 -- 修改
     * 4 -- 删除
     */
    static {
        AUTHORITY_MAP.put("06bbcde3c2b94743a2e6296896091bc7",1);
        AUTHORITY_MAP.put("eadc19ecba59431892bd3eb2a55d9a16",2);
        AUTHORITY_MAP.put("df6b5fa5cf224d3787c7b4197829fdac",4);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public static int getAuthorityCode(String key){
        Integer value = AUTHORITY_MAP.get(key);
        if(value == null){
            return 0;
        }
        return value.intValue();
    }
}

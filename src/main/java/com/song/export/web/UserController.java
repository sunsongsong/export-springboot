package com.song.export.web;

import com.song.export.dao.UserMapper;
import com.song.export.model.bean.User;
import com.song.export.model.common.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {

    /**
     * 验证是否建立成功
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    /**
     * 获取json返回值
     * @return
     */
    @RequestMapping(value = "/helloJson")
    public String helloJson() {
        return JsonResult.okResult("helloJson");
    }

    @Value("${properties_name}")
    public String properties_name;

    /**
     * 验证获取多环境配置文件
     * @return
     */
    @RequestMapping(value = "getPro")
    public String getProperties_name(){
        return JsonResult.okResult(properties_name);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 验证数据库配置是否正确
     * @return
     */
    @RequestMapping("/getUsers")
    public String getDbType(){
        String sql = "select * from appuser";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet( );
            if(entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator( );
                while(iterator.hasNext( )) {
                    Map.Entry<String, Object> entry = iterator.next( );
                    Object key = entry.getKey( );
                    Object value = entry.getValue();
                    System.out.println(key+":"+value);
                }
            }
        }
        return JsonResult.okResult(list);
    }

    @Autowired
    UserMapper userMapper;

    /**
     * 验证mybatis配置是否正确
     * @return
     */
    @RequestMapping("/mybatis")
    public String mybatis(){
        User user = userMapper.selectByPrimaryKey(1L);
        return JsonResult.okResult(user);
    }





}

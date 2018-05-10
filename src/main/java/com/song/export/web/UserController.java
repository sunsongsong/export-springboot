package com.song.export.web;

import com.song.export.cache.RedisService;
import com.song.export.dao.master.UserMapper;
import com.song.export.dao.slave.AppuserMapper;
import com.song.export.enums.RedisPrefixEnum;
import com.song.export.model.bean.master.User;
import com.song.export.model.bean.slave.Appuser;
import com.song.export.model.common.JsonResult;
import com.song.export.util.validat.ValidatCodeUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {

//    public static final Logger logger = Logger.getLogger(UserController.class);
    /**
     * 验证是否建立成功
     * @return
     */
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    /**
     * 获取json返回值
     * @return
     */
    @RequestMapping(value = "/helloJson",method = RequestMethod.GET)
    public String helloJson() {
        return JsonResult.okResult("helloJson");
    }

    @Value("${properties_name}")
    public String properties_name;

    /**
     * 验证获取多环境配置文件
     * @return
     */
    @RequestMapping(value = "getPro",method = RequestMethod.GET)
    public String getProperties_name(){
        return JsonResult.okResult(properties_name);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 验证数据库配置是否正确
     * @return
     */
    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
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
    @RequestMapping(value = "/mybatis",method = RequestMethod.GET)
    public String mybatis(){
        User user = userMapper.selectByPrimaryKey(1L);
        return JsonResult.okResult(user);
    }


    @Autowired
    AppuserMapper appuserMapper;
    /**
     * 验证多数据源的使用
     * @return
     */
    @RequestMapping(value = "/dataSource",method = RequestMethod.GET)
    public String dataSource(){
        User user = userMapper.selectByPrimaryKey(1L);
        Appuser appuser = appuserMapper.selectByPrimaryKey(1);
        Map map = new HashMap();
        map.put("user",user);
        map.put("appuser",appuser);
        return JsonResult.okResult(map);
    }

    @Autowired
    RedisService redisService;

    /**
     * 验证redis的使用
     * @return
     */
    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    public String redis(){
        long id = 1L;
        String key = "user:"+id;
        String msg = "";
        //先从缓存中查找
        User user = null;
        user = (User) redisService.get(key);
        //如果没找到,再查数据库,并且放入缓存中
        if(user == null){
            msg = "缓存中没找到";
            user = userMapper.selectByPrimaryKey(id);
            redisService.set(key,user,500L);
        }else{
            msg = "缓存中找到了";
        }
        Map map = new HashMap();
        map.put("msg",msg);
        map.put("user",user);
        return JsonResult.okResult(map);
    }

    /**
     * 测试 自动根据方法生成缓存--自定义key
     * key默认不写:自动生成key 类名+方法名+参数
     * @return
     */
    @RequestMapping(value = "/getUser" ,method = RequestMethod.GET)
    @Cacheable(value="userCache",key="'id_'+#id")
    public String getUser(@RequestParam(required=true)Long id) {
        User user=userMapper.selectByPrimaryKey(id);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        System.out.println(JSONObject.fromObject(user));
        return JsonResult.okResult(user);
    }
    /**
     * 测试delete 自动根据方法生成缓存--自定义key
     * @return
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    @CacheEvict(value ="userCache",key="'id_'+#id")
    public String deleteUser(@RequestParam(required=true)Long id) {
        User user=userMapper.selectByPrimaryKey(id);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        Map map = new HashMap();
        map.put("user",user);
        return JsonResult.okResult(map);
    }

    /**
     * 测试get 自动根据方法生成缓存--自定义key 类名+方法名+参数
     * @return
     */
    @RequestMapping(value = "/getUserBykey",method = RequestMethod.GET)
    @Cacheable(value ="userCache",key="#root.targetClass.getName() + #root.methodName + #id")
    public String getUserBykey(@RequestParam(required=true)Long id) {
        User user=userMapper.selectByPrimaryKey(id);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return JsonResult.okResult(user);
    }

    /**
     * 测试get 自动根据方法生成缓存--自定义key 类名+方法名+参数
     * @return
     */
    @RequestMapping(value = "/getUserBykeyMap",method = RequestMethod.GET)
    @Cacheable(value ="userCache",key="#root.targetClass.getName() + #root.methodName + #id")
    public String getUserBykeyMap(@RequestParam(required=true)Long id) {
        User user=userMapper.selectByPrimaryKey(id);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        Map map = new HashMap();
        map.put("user",user);
        return JsonResult.okResult(map);
    }



    /**
     * 常用的缓存注解：https://blog.csdn.net/u014381863/article/details/48788199
     *      @Cacheable
     *          表明spring在调用方法之前,首先从缓存中查找方法的返回值,如果这个值能给找到,就会返回缓存的值.
     *          否则这个方法就会调用,返回值也会存入缓存中
     *      @CachePut
     *          表明spring应该将方法的返回值放到缓存中.在方法调用前并不会检查缓存,方法始终都会被调用
     *      @CacheEvict
     *          表明spring应该在清除一条或者多条类目
     *      @Caching
     *          这是一个分组的注解,能够同时应用多个其他的缓存注解
     *
     *  参数 value
     *
     *  参数 key
     *
     *  查询结果返回值为null 会自动不加入缓存
     *
     */

    /**
     * 整合swagger测试
     * @param name
     * @return
     */
    @RequestMapping(value = "/testSwagger",method = RequestMethod.GET)
    @ApiOperation(value = "测试swagger", notes = "测试swagger")
    public String testSwagger(@ApiParam(value = "姓名", required = true) @RequestParam String name){
        return JsonResult.okResult("姓名：" + name);
    }

    /**
     * 测试异常
     * @return
     */
    @RequestMapping(value = "/e",method = RequestMethod.GET)
    public String e(){
        int num = 10 / 0;
        return JsonResult.okResult("OK");
    }

    /**
     * 全局测试异常
     *     只需要增加全局异常处理 GlobalExceptionHandler
     * @return
     */
    @RequestMapping(value = "/exception",method = RequestMethod.GET)
    public String exception(){
        int num = 10 / 0;
        return JsonResult.okResult("OK");
    }

    /**
     * 生成验证码图片
     */
    @RequestMapping(value = "/getVerifyImge", method = RequestMethod.GET)
    public void getVerifyImge(HttpServletRequest request, HttpServletResponse response) {
        try {
            ValidatCodeUtils.create(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 生成验证码Base64编码
     * 返回验证码的编码和生产id
     */
    @RequestMapping(value = "/getVerify", method = RequestMethod.GET)
    public String getVerify() {
        try {
            Map map = ValidatCodeUtils.createImage();
            //增加缓存
            String code = map.get("code").toString();
            String uuid = ValidatCodeUtils.generateUUID();
            String key = RedisPrefixEnum.VERIFY_CODE.getPrefix() + uuid;
            redisService.set(key,code,300l);
            logger.info("VerifyCode:key="+key);
            logger.info("VerifyCode:code="+code);

            Map resultMap = new HashMap();
            resultMap.put("image",map.get("image"));
            resultMap.put("uuid",uuid);
            return JsonResult.okResult(resultMap);
        } catch (Exception e) {
            logger.error("获取验证码失败", e);
            e.printStackTrace();
        }
        return JsonResult.errorResult(-1,"获取验证码失败");
    }
    //前台获取
    /*getimgcode() {
        this.http.get("/user/getVerify").subscribe((data: any) => {
            if (data) {
                let imageSrc = data.image;
                this.user.uuid = data.uuid;
                this.verifyImg.nativeElement.src = "data:image/png;base64," + imageSrc;
            }
        });
    }*/




}

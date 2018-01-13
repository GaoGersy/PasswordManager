  
package com.gersion.superlock.utils;

/**
 * ClassName: NewsCenterBean <br/> 
 * Function: TODO ADD FUNCTION. <br/>  
 * date: 2016年8月9日 下午7:36:01 <br/> 
 * 
 * @作者 Gers 
 * @版本  
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 TODO
 * 
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public interface MyConstants {

    //是否完成引导界面
    String IS_FINISH_GUIDE = "is_finish_guide";
    //悬浮工具
    String ENABLE_FLOAT_BALL = "enable_float_ball";
    //是否是更改主密码
    String IS_CHANGE_PWD = "is_change_pwd";
    //加密的时候加盐
    String ADD_SALT = "Lx*T@3eRa4yt";
    //记录第一次完成引导的时间
    String FIRST_TIME = "first_time";

    //是否在输入了密码长度的字符后自动登录
    String IS_AUTO_LOGIN = "is_auto_login";
    //是否需要输入密码登录程序
    String IS_LOCK = "is_lock";
    //是否在详情页显示密码为明文
    String IS_SHOW_PWD = "is_show_pwd";
    //是否显示密码的更新时间
    String IS_SHOW_UPDATE_TIME = "is_show_update_time";
    //密码长度
    String LENGTH = "length";
    //指纹解锁
    String FINGER_PRINT = "finger_print";
    //用户名
    String USER_NAME = "user_name";

    String SUPER_PASSWORD = "super_password";

    String PATTERN_STRING = "pattern_string";

    String APP_PASSWORD = "app_password";

    String LOCK_TYPE = "lock_type";

    String SUPER_PASSWORD_SETED = "super_password_seted";

    String BACKUP_EMAIL_ADDRESS = "backup_email_address";

    String CREATE_DB_DATE = "create_db_date";

    String DATA_LIST_COUNT = "data_list_count";

    String CREATE_LOCK_DATE = "create_lock_date";
    String TEST = "test";

    String AES_PASSWORD = "jmn5k29ok78&ssa@!$%&";

    String BACKUP_PATH = "superlock/backup";

    String BACKUP_FILE_NAME= "data";

    String SALT = "9ok78&ssa@";

    String FILE_TYPE = "slock";

    interface LockMode{
        int MODE_INIT=0;//初始设置
        int MODE_LOCK=1;//锁屏
        int MODE_RESET=2;//重置密码
    }

    interface LockType{
        int TYPE_PIN=0;//pin
        int TYPE_PATTERN=1;//图案
        int TYPE_FINGER_PRINT=2;//指纹
    }

    interface RegisterMode{
        int MODE_INIT=0;//pin
        int MODE_RESET=1;//图案
        int MODE_CHANGE_LOCK_TYPE=2;//更换到
    }
}
  
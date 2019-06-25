# 效果图
![](http://upload-images.jianshu.io/upload_images/6722970-a84d3a33f18b7c57.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)

# 使用方法
## 1.gradle配置
```
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}
​
dependencies {
      ...
      implementation 'com.alibaba:arouter-api:1.4.0'
      annotationProcessor 'com.alibaba:arouter-compiler:1.2.1'
}
```
## 2.Application初始化sdk
```
 if (BuildConfig.DEBUG) {
    // 这两行必须写在init之前，否则这些配置在init过程中将无效
    ARouter.openLog(); // 打印日志
    ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)}
 }
 ARouter.init(this);// 尽可能早，推荐在Application中初始化
```
## 3.添加注解
```
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在baseActivity自动注入属性
        ARouter.getInstance().inject(this);
    }
}
```
```
//对应的Activity 添加@Route注解 path路径 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = RoutePath.FIRST_PATH)
public class FirstActivity extends BaseActivity {

    @Autowired
    public String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ((TextView)findViewById(R.id.tv_msg)).setText(msg);
    }
}
```
## 4.拦截器的使用面向切面编程
```
// 在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行  
@Interceptor(name = "login", priority = 6)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        LogUtils.e(path);
        boolean isLogin = SPUtils.getInstance().getBoolean(RoutePath.SP_IS_LOGIN, false);

        if (isLogin) { // 如果已经登录不拦截
            callback.onContinue(postcard);
        } else {  // 如果没有登录
            switch (path) {
                // 不需要登录的直接进入这个页面
                case RoutePath.LOGIN_PATH:
                case RoutePath.FIRST_PATH:
                    callback.onContinue(postcard);
                    break;
                // 需要登录的直接拦截下来
                default:
                    callback.onInterrupt(null);
                    break;
            }
        }

    }

    @Override
    public void init(Context context) {//此方法只会走一次
        LogUtils.e("路由登录拦截器初始化成功");
    }
}
```
```
//启动Activity
ARouter.getInstance().build(RoutePath.SECOND_PATH)
                        .withString("msg", "ARouter传递过来的需要登录的参数msg")
                        .navigation(this,new LoginNavigationCallbackImpl());//第二个参数是路由跳转的回调
```
```
public class LoginNavigationCallbackImpl  implements NavigationCallback {
    @Override //找到了
    public void onFound(Postcard postcard) {

    }

    @Override //找不到了
    public void onLost(Postcard postcard) {

    }

    @Override    //跳转成功了
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        String path = postcard.getPath();
        LogUtils.v(path);
        Bundle bundle = postcard.getExtras();
        // 被登录拦截了下来了 
        // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
        ARouter.getInstance().build(RoutePath.LOGIN_PATH)
                .with(bundle)
                .withString(RoutePath.PATH, path)
                .navigation();
    }
}
```

博客地址：https://www.jianshu.com/p/d5a83ccf1135
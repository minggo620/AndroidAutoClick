##微信公众号:

![学习流程图](https://github.com/minggo620/iOSRuntimeLearn/blob/master/picture/gongzhonghao.jpg?raw=true)  

“Action！”，欢迎收看这期大型扯淡实用类技术节目，《小明讲故事》，大家好我是小明！今天的主题是如果孙膑、诸葛亮和曾国藩是程序员，会怎么实现Android模拟自动点击。此处可以有Android手机广告，没有赞助商提供，我们继续。很难想象那三位历史大佬是程序员吧？且不说他们，我们撸清下Android模拟点击对我们产生的价值，“唯利是图”一下下！

![内容思维导图](http://upload-images.jianshu.io/upload_images/1252638-a3890571d7675936.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###一.模拟点击的价值
1. 有这么种情况，手机邮件每天都有很多邮件，邮件里也有一些不想去看或者是过期了的未读邮件，莫名的强迫症都想去掉那些罪恶的小红点，我都懒得的点击全选设置成已读，如果有个自动点击该多好；  
2. 又有这么种情况，有个手游我很喜欢玩，但是升级有很慢，刷怪有很单调，如果有个小外挂帮我点击，自己玩其他的事去该多好？这时，你是不是想回答“嗯，是的，最好有个帮忙点击微信红包什么的就真真是极好极好的了”。
3. 最后这样的情况，尽管我们懒，尽管我们考试想带小抄作弊，但我们也是能站在宇宙中心呼唤爱的嘛。对于有些身体不便的手机使用人群确实要需要使用某些手机功能，模拟点击是不是特别有爱。爱，又是多方面的，在你们的TA手机上一旦是接受自己的信息就自动点击打开，然后播放一句“亲爱的，我来了，你在哪？”，又是多浪漫的事情。

**好，模拟点击的价值就显而易见了，基本是以下3点：**
>**1. 无障碍爱心服务。[爱还是排在第一位比较合适]**  
**2. 更自动完成特定日常使用。**  
**3. 作弊外挂。[难免有些小兴奋吧？]**

###二.MotionEvent实现模拟点击  
####1. 实现原理
**1）获取被点击的View。**  
**2）模拟点击事件MotionEvent.ACTION_DOWN和MotionEvent.ACTION_UP。**
####2. 实现过程

哦，冷落了历史三位大佬挺久了。有请鬼谷先生得意弟子孙膑！  
**1）孙膑瞒天过海**
>自从孙膑被同窗庞涓嫉妒猜忌而被陷害落得一个膑刑（他从轻只被挑了膝盖骨），庞涓为了掩盖他的罪恶和野性，把孙膑供养起来一心想套出孙膑从鬼谷先生得到的《孙子兵法》，后来孙膑从外人得知这一切的受罪都是庞涓所为，除了绝望和无奈外，还是想尽快逃离庞涓的魔爪。  
就这样情况下，孙膑来了一招孙子兵法的瞒天过海，装疯卖傻骗过了庞涓不再逼他默写孙子兵法同时也减去对自己提防，最后在一个夜黑风高的夜晚被齐国使臣淳于髡救走。

①.孙膑逃离就是犹如一次MotionEvent事件。  
②.装疯卖傻就犹如假装点击触动MotionEvent.ACTION_DOWN和MotionEvent.ACTION_UP事件。  
  
**2）Show Code**  
	
	//孙膑瞒天过海
	private void manTianGuoHai(View view, float x, float y) {

		long downTime = SystemClock.uptimeMillis();
		
		//装疯
		MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_DOWN, x, y, 0);
		downTime += 1000;
		
		//卖傻
		MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_UP, x, y, 0);
				
		view.onTouchEvent(downEvent);
		view.onTouchEvent(upEvent);
		downEvent.recycle();
		upEvent.recycle();
	}
**2)查看效果**  
![Motionevent模拟点击](http://upload-images.jianshu.io/upload_images/1252638-5c0464dc630a06fe.gif?imageMogr2/auto-orient/strip)

哎哟喂，这也行。我也装一个傻卖个萌什么的！现在都流行语音支付，如果小明捡到志玲姐姐的手机，我一定要试试她手机语音支付，对着志玲姐姐的手机娇滴滴的喊“萌萌站起来，加油，萌萌站起来~”。那为人兄，嘿~嘿嘿，呕吐袋已准备了在座椅右边！！
借着有志玲姐姐语音的高德地图导航，我们回到了三国赤壁。正好碰见孔明先生。“晚生小明，久仰诸葛先生大名！”，孔明见短发短袖短裤短腿短拖鞋的小明惊悚道：“何方妖怪？”，“噗~~

###三.AccessibilityService实现模拟点击
####1. 基本概念
AccessibilityService是Android提供有某些障碍手机人群使用的辅助服务，可以帮助用户实现点击屏幕等一些帮助。是一个很有爱的服务。
官方对这个功能介绍如下：  
>Many Android users have different abilities that require them to interact with their Android devices in different ways. These include users who have visual, physical or age-related limitations that prevent them from fully seeing or using a touchscreen, and users with hearing loss who may not be able to perceive audible information and alerts.     
  
####2. 实现原理  
**1）通过resource-id获取对应的View。**  

	nodeInfo.findAccessibilityNodeInfosByViewId(resId);  

**2）或者通过带text属性的View。**  
	
	nodeInfo.findAccessibilityNodeInfosByText(text);
	
**3）执行AccessibleService的指定方法实现点击。**
	
	targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

####3. 实现过程
**1）诸葛亮草船借箭**  

这个大家再熟悉不过了，舌战群儒后的草船借箭啦。
>周瑜说“我这里只有船和水军，我没有‘贱’，孔明兄还是你来‘贱’吧！”，孔明君子之躯何贱之有啊，“短时间要我‘贱’，可以，你我通力合作可否？”。“行，只要你拿出‘贱’来，我同意，立下下军令状吧”周瑜得意挑衅。智比天高的诸葛亮想“我又作‘贱’，还是找个‘贱’人来借一把吧！”    

这样妙极了吧，曹操这么多‘贱’，草船使过来了，抖一抖‘贱’掉了船，拿去！

①.舌战群儒后的合作击退曹操好比`AccessibleService`服务。  
②.周瑜提供船和水军就是具体的`findAccessibilityNodeInfosByViewId(resId)`和`findAccessibilityNodeInfosByText(text)`。  
③.诸葛亮去敲鼓正是`performAction(AccessibilityNodeInfo.ACTION_CLICK)`;  

**2）如何获取resource-id或text**  
①.如何你是获取自己应用的`resource-id`，那就是布局xml定义的`@id/shuijun`或者`@+id/chuan`
②.别人的软件，那就通过dump出view层级outline查找出resource-id或text。
![dump页面分析](http://upload-images.jianshu.io/upload_images/1252638-6967e80e0591e15a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**3）申明使用的服务要求**
诸葛亮要申明我可以用兵用船。
①click_config.xml文件配置

    <?xml version="1.0" encoding="utf-8"?>
    <accessibility-service    xmlns:android="http://schemas.android.com/apk/res/android"
        android:description="@string/click_auto"
        android:accessibilityEventTypes="typeNotificationStateChanged|typeWindowStateChanged"
        android:packageNames="com.youmi.android.addemo"
        android:accessibilityFeedbackType="feedbackGeneric"
        android:notificationTimeout="100"
        android:accessibilityFlags=""
        android:canRetrieveWindowContent="true"/>
②AndroidManifest.xml文件配置
  
    <service
        android:label="@string/app_name"
        android:name="com.minggo.autoclick.ClickService"
        android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
        <intent-filter>
             <action android:name="android.accessibilityservice.AccessibilityService"/>
        </intent-filter>
        <meta-data
            android:name="android.accessibilityservice"
            android:resource="@xml/click_config"/>
    </service> 
**4）授权使用辅助服务应用**
合作达成，又要授权给诸葛亮办事才行。
![授权启动辅助服务](http://upload-images.jianshu.io/upload_images/1252638-0c2fe431c8d82ee3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
**5）Show Code**
	最后就约上鲁肃去借箭

    //借箭（点击）
    private void performClick(String resourceId) {

        Log.i("mService","点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        targetNode = findNodeInfosById(nodeInfo,"com.youmi.android.addemo:id/"+resourceId);
        if (targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


    //调用兵力（通过id查找）
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }
    
    //调用船只（通过文本查找）
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
**6）查看效果图** 
![Accessibility服务点击](http://upload-images.jianshu.io/upload_images/1252638-96b753c8eab31a0a.gif?imageMogr2/auto-orient/strip)
‘贱’周瑜还是拿到了十万，不过那口来到喉咙边上的老血差点没有喷出来。估计周瑜恨不得借这次庆功约诸葛亮到自家澡堂，赠与他一个，能为您的家庭提供24小时全面保护的舒肤佳。

这样共享半边天的日子估计是不长久的，很快就会瓦解合作闹得翻天覆地，天翻了我们回到地上吧！人生多变啊，不久将又开始仰望“蓝蓝的~天空~~哎呀！”，顿悟不要逆天，上天还是可以的，心情大好，去剃个头重新开始吧。

###四.借助反射实现模拟点击
####1. 基本概念
>JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法和属性；这种动态获取的信息以及动态调用对象的方法的功能称为Java语言的反射机制。

注意：反射本身是违背面向对象的封装原则的，所以它有破坏性；反射被使用也是很广泛的Gson，Afinal框架，Spring依赖注入注解等等。两面性看待这个机制，切勿死板。

####2.基本操作
**1)获取实体类型**

    Class clazz = spotManager.getClass();

**2)获取类的变量**

    Field f = clazz.getDeclaredField("F");
    f.setAccessible(true);//加上这个才能读取private属性

**3)获取变量值**

    o oInstance = f.get(spotManager);

####3. 实现过程
**1)曾国藩反间计**
《曾剃头发廊》眼前一亮啊，进去剃个头。“客官，老生看您天堂饱满，眉清目秀...”，“哎哟~您老应该就是曾国藩，曾大人吧，晚生小明，失敬失敬啊？辣椒来一根不咯，要不我请您吃臭豆腐好不咯？”。曾大人舔舔舌头，“好，我们先去吃臭豆腐，到此处不吃臭豆腐等于白来。”，“曾大人，可否讲讲您如何策反韦俊呢？”，“小明，我们边走边聊”...
>“天京事变”，韦俊受到二个韦昌辉牵连，随时会被叫回去问斩。
韦俊好猎和下棋，我叫康福去山上等他与之相遇，再跟他下棋并且要赢他。棋下了一头半个月了，终于逮到机会，天国来旨要求韦俊回京（南京），康福游说他，并且挑明自己的身份，表达我对他的赏识和恳邀之意。
这样的事态，他选择了我，并告知他们打战之术，我记录为“长毛战术”。池州就算是我涤生的了。

"曾大人，您让晚生佩服不已，康福他着~么厉害！晚生寻他千百度，那人却不在灯火阑珊处撒！"。曾大人沉思一下，“这，，，你得问问唐浩明咯！”。。。

①韦俊潜在背叛的可能性就是反射机制破坏性。
②韦俊好猎好下棋就是侧面暴漏让人接近的`spotManager.getClass()`和`f.setAccessible(true);`
③韦俊对康福暴漏自己身份也只能是放下手中的剑被明目张胆策反`o oInstance = f.get(spotManager);`

**2)如何找到要反射的那个实体**
要测策反韦俊，不能跑错山头了，要不然只能演一出“皇上，你还记得当年大明湖畔的夏雨荷吗？”。这可以重头戏啊，我们好好找找山头先。

###五.分析有米插屏广告
整一块天都不是自家打下来的，我们来顺应天意吧。找找策反大道，看能不能得道成仙。
####1. dump一下有米插屏广告页面
![有米插屏广告.png](http://upload-images.jianshu.io/upload_images/1252638-2224eabf9143f61e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们从图中只能发现横屏广告的webview（得到这个已经可以用之前两种方法实现点击这个地方，干你想干的事），但没有插屏广告view的层级信息。天无绝人之路，好戏都在后头。
####2反编译查找位置
**1)确定启动广告类入手**

    btnShowSpot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 展示插屏广告，可以不调用预加载方法独立使用
				//从这里入手，分析插屏广告代码，找到对应的广告view
				SpotManager.getInstance(mContext)
						.showSpotAds(mContext, new SpotDialogListener() {
    ...
确定是在SpotManager类里边折腾。
**2)反编译SpotManager**
①看看大概有什么属性：
    
    private o F;
    protected SplashView n;
这两个属性最为可能，其他是String，int，boolean属性。Splash顾名思义是闪屏这个是开屏广告，先留着，分析o F这个地。

![反编译找到调用了b方法](http://upload-images.jianshu.io/upload_images/1252638-cdb5d2fbfb5e11da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

②接着我们进入a方法中
![a方法中的重要信息](http://upload-images.jianshu.io/upload_images/1252638-cb4a0729b77cc83f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
果然有o F这个实例新增和使用k方法。
**3)反编译o类中查看属性**

![反编译o类](http://upload-images.jianshu.io/upload_images/1252638-bbf28bd9094b42d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

韦俊放下剑跟富康去见曾大人吧，一切都结束了。放下屠剑立地成仁吧。
####3. 瞄准目标反射带出
    
    /**
	 * 1.通过反编译[studio自带有]查看分析o F(o混淆后的类名称,F是o的实例)是广告实例。
	 * 2.o类中的变量w是Imageview类型。
	 * 3.就以上足够可以获取插屏广告图片实体，逐一反射获取对应的属性值。
	 * 4.扩展，如果反编译出现很多属性，那样编写一个反射遍历其中属性，逐一暴力破解。
	 */
	private void getO(){
		SpotManager spotManager = SpotManager.getInstance(mContext);
		Class clazz = spotManager.getClass();
		Field f;
		try {
			f = clazz.getDeclaredField("F");
			f.setAccessible(true);
			Class deClazz = f.getType();
			Log.i("mService","F类型-->"+deClazz.getSimpleName());
			if(f.get(spotManager)==null){
				Log.i("mService","广告类为空");
			}else if(f.get(spotManager) instanceof o){
				Log.i("mService","是统一类型");
				o adO = (o) f.get(spotManager);
				Class oClazz = adO.getClass();
				Field imgField = oClazz.getDeclaredField("w");
				imgField.setAccessible(true);

				if (imgField.get(adO) instanceof ImageView){
					Log.i("mService","属性m是Imageview");
					final ImageView adImageView = (ImageView) imgField.get(adO);
					Log.i("mService","广告image-->"+adImageView.getId());
					if (adImageView==null){
						Log.i("mService","广告image 为null");
					}else {
						if(adImageView.isClickable()){
							Log.i("mService","广告image可以点击");
						}else{
							Log.i("mService","广告image不可以点击");
						}
						Log.i("mService","广告宽高-->"+adImageView.getLayoutParams().width+","+adImageView.getLayoutParams().height);

						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {

								setSimulateClick(adImageView,adImageView.getWidth()/2,adImageView.getHeight()/2);
							}
						},3000);
					}
				}
				//adImageView.setDrawingCacheEnabled(true);
				//adImageView.getDrawable();

				//ImageView imageViewxml = (ImageView) findViewById(R.id.imageView);
				//imageViewxml.setImageDrawable(adImageView.getDrawable());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
####4. 查看效果
![反射有米插屏广告自动点击效果图](http://upload-images.jianshu.io/upload_images/1252638-87297809ad8c1077.gif?imageMogr2/auto-orient/strip)

“曾大人，难怪你的一生就像开挂似的，你咋不上天啊！！”话音未落，曾大人胸口中央一团紫光一闪一闪，“叮咚，叮咚，叮咚...”哇还会作响，“嚓~”的一声曾大人不留一丝青烟冲进云中。

###六.总结和扩展
**1. 如果天是你们家的就直接瞒天过海。**
**2. 如果天一半是你们家的就草船借箭。**
**3. 如果天跟你没半毛钱关系的就反间计。**
记住了，我重来也没有提倡大学生特别是计算机专业的特别是有班长一职的同学，组织班上同学以这种方式挣得班会费哈。因为我大学的做班长期间就没做。
节目到了尾声了，此处应该有掌声。噼里啪啦...谢谢，谢大家，让我们再次以热烈的掌声感谢今天重量级嘉宾孙膑，诸葛亮和曾国藩老师，各位老师慢走~~！

###七.源码地址
#####*[https://github.com/minggo620/AndroidAutoClick](https://github.com/minggo620/AndroidAutoClick)*
######谢谢收看这期大型扯淡实用技术类节目，《小明讲故事》。哦，忘了~~曾大人，您还在吗？您还没有给我剃头呢！！！

***
>#####【原创出品 未经授权 禁止转载】
#####【欢迎微友分享转发 禁止公号等未经授权的转载】

# 谈Android模拟点击的价值和实现
“Action！”，欢迎收看这期大型扯谈实用类技术节目，《小明讲故事》，大家好我是小明！今天的主题是如果孙膑、诸葛亮和曾国藩是程序员，会怎么实现Android模拟自动点击。此处可以有Android手机广告，没有赞助商提供，我们继续。很难想象那三位历史大佬是程序员吧？且不说他们，我们撸清下Android模拟点击对我们产生的价值，“唯利是图”一下下！

![内容思维导图](http://upload-images.jianshu.io/upload_images/1252638-a3890571d7675936.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###一.模拟点击的价值
1. 有这么种情况，手机邮件每天都有很多邮件，邮件里也有一些不想去看或者是过期了的未读邮件，莫名的强迫症都想去掉那些罪恶的小红点，我都懒得的点击全选设置成已读，如果有个自动点击该多好；  
2. 又有这么种情况，有个手游我很喜欢玩，但是升级有很慢，刷怪有很单调，如果有个小外挂帮我点击，自己玩其他的事去该多好？这时，你是不是想回答“嗯，是的，最好有个帮忙点击微信红包什么的就真真是极好极好的了”。
3. 最后这样的情况，尽管我们懒，尽管我们考试想带小抄作弊，但我们也是能站在宇宙中心互换爱的嘛。对于有些身体不便的手机使用人群确实要需要使用某些手机功能，模拟点击是不是特别有爱。爱，又是多方面的，在你们的TA手机上一旦是接受自己的信息就自动点击打开，然后播放一句“亲爱的，我来了，你在哪？”，又是多浪漫的事情。

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

哎哟喂，这也行。我也装一个傻卖个萌什么的！现在都流行语音支付，如果小明捡到志玲姐姐的手机，我一定要试试她手机语音支付，对着志玲姐姐的手机娇滴滴的喊“萌萌站起来，加油，萌萌站起来~”。嘿~嘿嘿，座椅右边已准备了呕吐袋！！借着志玲姐姐的高德地图导航，我们回到了三国赤壁。正好碰见孔明先生。“晚生小明，久仰诸葛先生大名！”，“何方妖怪？”，“噗~~~”

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
**1）诸葛亮草船借箭步**  

这个大家再熟悉不过了，舌战群儒后的草船借箭啦。
>周瑜说“我这里只有船和水军，我没有‘贱’，孔明兄还是你来‘贱’吧！”，孔明君子之躯何贱之有啊，“短时间要我‘贱’，可以，你我通力合作可否？”。“行，只要你拿出‘贱’来，我同意，立下下军令状吧”周瑜得意挑衅。智比天高的诸葛亮想“我又作‘贱’，还是找个‘贱’人来借一把吧！”    

这样妙极了吧，曹操这么多‘贱’，草船来了抖一抖拿去！

①.舌战群儒后的合作击退曹操好比AccessibleService服务。  
②.周瑜提供船和水军就是具体的findAccessibilityNodeInfosByViewId(resId)和findAccessibilityNodeInfosByText(text)。  
③.诸葛亮去敲鼓正是performAction(AccessibilityNodeInfo.ACTION_CLICK);  

**2）如何获取resource-id**  
①.如何你是获取自己应用的resource-id，那就是布局xml定义的@id/shuijun或者@+id/chuan
②.别人的软件，那就通过dump出view层级outline查找出resource-id或text。
以下是通过DDMS工具里的Dump View Hierarchy For UI Automator 分析View层级：  
![dump页面分析](http://upload-images.jianshu.io/upload_images/1252638-6967e80e0591e15a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**3）授权使用辅助服务应用**
合作达成，又要授权给诸葛亮办事才行。

![授权启动辅助服务](http://upload-images.jianshu.io/upload_images/1252638-0c2fe431c8d82ee3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  

**4）Show Code**
	
	//执行点击
    private void performClick(String resourceId) {

        Log.i("mService","点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        targetNode = findNodeInfosById(nodeInfo,"com.youmi.android.addemo:id/"+resourceId);
        if (targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


    //通过id查找
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }
    
    //通过文本查找
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }  
  
**5）查看效果图**  
![Accessibility服务点击](http://upload-images.jianshu.io/upload_images/1252638-96b753c8eab31a0a.gif?imageMogr2/auto-orient/strip)  
  
![反射有米插屏广告.gif](http://upload-images.jianshu.io/upload_images/1252638-87297809ad8c1077.gif?imageMogr2/auto-orient/strip)
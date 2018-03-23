# skin

Android换肤(动态设置view属性) <br>  

工程结构：<br>  
Ui ： 应用工程<br>  
skin ： AndroidLibrary 资源更换的主逻辑 <br>
SkinPluginApk ： android工程，主要用于生成资源插件包<br>  

原理：<br>
 在原LayoutInflater createView后加入自己的处理逻辑。用于设置View的属性  <br>
 比如：background textColor textSize TypeFace selector等 <br>

流程：<br>  
注册Activity生命周期回调<br>  
在onActivityCreated中，给LayouInflater设置自定义的Factory。 <br>
1.1自定义Factory中大部分逻辑是LayoutInflater的createViewFromTag方法的分解，直接反射调用对应方法。 <br>
1.2在Factory成功创建View后，根据App中资源的名称和自定义的映射关系，找到插件包中对应资源的名称。获取插件包中对应的资源，设置给该view。<br>
1.3记录属性有更改的View，用于用户设置不同皮肤后及时更改View的属性到设置的值。<br>
1.4根据记录修改StatusBar和NavigationBar的属性。<br>

2.在onActivityDestroyed中，清除该Activity中对应的记录。<br>  


使用：<br>
1.在Application中调用,ResPluginImpl.getsInstance().init(Application); 用于注册Activity的周期回调方法。<br>
2.在Activity的super.onCreate前调用ResPluginImpl.getsInstance().load("插件路径", "包名")方法，用于创建插件的Resouce对象。super.onCreate中将会调用我们注册的onActivityCreated方法。在该方法中设置StatusBar和NavigationBar的属性，如果对应的资源是来自于插件包，而此时还没有插件包的Resouce对象就GG了。当然，若果并不是，打开应用马上就需要加载插件包中的资源时，你只需要保证在运行下面方法前调用就该方法就ok。<br>

//设置所有的TextView及其子类的字体 <br>
 //fzxz.TTF：插件中字体文件的名称。放在assets/font目录下 <br>
 ResPluginImpl.getsInstance().setTypeFace(view.getContext(), "fzxz.TTF");<br>
 
 //设置statusBar颜色，需要更改Window类的属性所以必须是Activity。Acivity attach方法中创建PhoneWindow<br>
 //status_bar_color_pf1 :插件中设置的颜色<br>
 ResPluginImpl.getsInstance().setStatusBarColor(MainActivity.this, "status_bar_color_pf1");<br>
 
 //设置NavigateBar颜色，需要Activity
 //navigation_bar_color_pf1 :插件中设置的颜色<br>
 ResPluginImpl.getsInstance().setNavigateBarColor(MainActivity.this, "navigation_bar_color_pf1");<br>
 
 //之所有是Activity是应为 需要马上更新当前Activity中的指定View的属性 <br>
 //pf1 : 仅仅只是一个后缀名，和个人设计的根据原资源匹配插件资源的映射逻辑有关系<br>
 ResPluginImpl.getsInstance().setRes(MainActivity.this, "pf1");<br>
 
 在本列中其映射关系是:<br>
  1.在创建出View后，获取原资源对应的名称比如"status_bar_color_normal"，取改名称从0到最后一个'_'(包括)处的字符，得到新的字符status_bar_color_。<br>
  2.status_bar_color_字符加设置时指定的后缀名pf1，组成新的字符status_bar_color_pf1，该字符串及时在插件包中对应的名称。通过该名称在插件包中寻找对应的资源。找到则给该View设置找到的资源，否则不做更改。<br>
  3.View的属性很多，为了加快筛选的速度，只对感兴趣View的属性进行设置。因此你需要设置那些View是你感兴趣的，期望其属性得到改变。设置方法如下：<br>
  1.在View，布局Xml文件中引入命名空间: xmlns:skin="http://schemas.android.com/apk/skin"<br>
  2.View中添加一个新的属性 skin:enable="true"<br>
  

打包SkinPluginApk工程将生成的Apk包放在load时指定的目录下运行。通过右上角按键选择不同皮肤，就能看到效果。<br>
其原理并不复杂，相信你看了运行效果后马上自己就会写了。<br>

注意：不同的字体文件，在不同版本上不一定适配，比如可能不一定支持中文, 动态权限不要忘了添加。<br>
如果你不知道怎么让一个Android Library独立运行（不能被依赖）,这是一个最直白的列子<br>

运行效果截图：<br>
![image](https://github.com/tyongsong1231/skin/blob/master/app/src/main/assets/screenshots/yunxing1.png) 
![image](https://github.com/tyongsong1231/skin/blob/master/app/src/main/assets/screenshots/yunxing2.png) <br>


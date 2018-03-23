# skin

Android换肤(动态设置view属性) <br>  
 HOOK LayoutInflater createView后设置属性  <br>
   background textColor textSize TypeFace...... <br>

Application：<br>
//注册Activity的周期回调，设置LayouInflater <br>
ResPluginImpl.getsInstance().init(this); <br>

//根据路径，包名构建Resources
ResPluginImpl.getsInstance().load(  <br>
                Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",<br>
                "com.example.tys.skinpluginapk");<br>

//根据规则设置资源包 <br>

skin ： AndroidLibrary 资源更换的主逻辑
SkinPluginApk ： android工程，主要用于生成资源插件包

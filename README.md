# Android-LoadingView
progress 简单的无限旋转的进度条
***  
  - 使用方法
  * 根build.gradle添加
   ```
   allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
   ```
  * 使用项目的build.gradle中添加 
   ```
   dependencies {
	        compile 'com.github.xuanu:Android-LoadingView:0.0.1'
	}
   ```
   
  自定义一个Loading效果，无限旋转。
  可以自定义两个参数
  ```
  <attr name="border_color" format="color"></attr>//颜色
  <attr name="border_width" format="dimension"></attr>//宽度
  ```
    
- 如果宽度>半径，宽度=半径  
![image](https://github.com/xuanu/Android-LoadingView/raw/master/screenshots/loading2.gif)

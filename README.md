## Hook-animation

&ensp;&ensp;&ensp;&ensp;仿支付宝，微信支付成功后的打钩动画，暂时实现两种不同动画，只需要在gradle中引入即可使用。用户可以自己决定动画开启时间，即需要用户手动控制动画。

#### 使用



#### 属性

1. ZFinishImage

&ensp;&ensp;&ensp;&ensp;其中circleR是圆的半径，高度和宽度最好定义为circleR的2倍或者定义为wrap会更好的显示。

```
<com.milkz.zfinishimage.ZFinishImage
    android:id="@+id/ZFinishImage"
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:circleR="50dp" />
```

- 自定义属性

属性|含义
--|--
circleR|圆的半径，默认30dp
colorCircle|圆的颜色，默认蓝色
colorMark|对勾颜色，默认白色
colorBG|圆形之外的背景色，默认透明
animTime|动画持续时间，默认2000ms
widthHook|对勾宽度，默认自动适配，如没有需求，最好不要修改
startWhenInit|是否手动控制动画，false表示否，手工控制开始打开。true表示是，UI显示出来即表示开始动画。

# RapidFloatingActionButton
Floating Action Button的快速实现，RapidFloatingActionButton（以下简称RFAB）</br>
<img src='https://raw.githubusercontent.com/wangjiegulu/RapidFloatingActionButton/master/screenshot/rfab_label_list.gif' height='500px'/>
<img src='https://raw.githubusercontent.com/wangjiegulu/RapidFloatingActionButton/master/screenshot/rfabg.gif' height='500px'/>
<img src='https://raw.githubusercontent.com/wangjiegulu/RapidFloatingActionButton/master/screenshot/rfab_01.png' height='500px'/>
<img src='https://raw.githubusercontent.com/wangjiegulu/RapidFloatingActionButton/master/screenshot/rfab_03.png' height='500px'/>

# 使用方式：
依赖：</br>
[AndroidBucket](https://github.com/wangjiegulu/AndroidBucket)：基础工具包</br>
[AndroidInject](https://github.com/wangjiegulu/androidInject)：注解框架</br>
[NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)：兼容低版本的动画框架</br>
## activity_main.xml：
```
<com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout
      xmlns:rfal="http://schemas.android.com/apk/res-auto"
      android:id="@+id/activity_main_rfal"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      rfal:rfal_frame_color="#ffffff"
      rfal:rfal_frame_alpha="0.7"
      >
  <com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton
          xmlns:rfab="http://schemas.android.com/apk/res-auto"
          android:id="@+id/activity_main_rfab"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_alignParentRight="true"
          android:layout_alignParentBottom="true"
          android:layout_marginRight="15dp"
          android:layout_marginBottom="15dp"
          android:padding="8dp"
          rfab:rfab_size="normal"
          rfab:rfab_drawable="@drawable/rfab__drawable_rfab_default"
          rfab:rfab_color_normal="#37474f"
          rfab:rfab_color_pressed="#263238"
          rfab:rfab_shadow_radius="7dp"
          rfab:rfab_shadow_color="#999999"
          rfab:rfab_shadow_dx="0dp"
          rfab:rfab_shadow_dy="5dp"
          />
</com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout>
```
在需要增加RFAB最外层使用`<com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout>`，按钮使用`<com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton>`

###属性解释
####RapidFloatingActionLayout:</br>
      rfal_frame_color: 展开RFAB时候最外覆盖层的颜色，默认是纯白色
      rfal_frame_alpha: 展开RFAB时候最外覆盖层的透明度(0 ~ 1)，默认是0.7
####RapidFloatingActionButton:</br>
      rfab_size: RFAB的尺寸大小，只支持两种尺寸（Material Design规范）：
              normal: 直径56dp
              mini: 直径40dp
      rfab_drawable: RFAB中间的图标，默认是一个"+"图标
      rfab_color_normal: RFAB背景的普通状态下的颜色。默认是白色
      rfab_color_pressed: RFAB背景的触摸按下状态的颜色。默认颜色是"#dddddd"
      rfab_shadow_radius: RFAB的阴影半径。默认是0，表示没有阴影
      rfab_shadow_color: RFAB的阴影颜色。默认是透明，另外如果rfab_shadow_radius为0，则该属性无效
      rfab_shadow_dx: RFAB的阴影X轴偏移量。默认是0
      rfab_shadow_dy: RFAB的阴影Y轴偏移量。默认是0


## MainActivity：
```
@AILayout(R.layout.activity_main)
public class MainActivity extends AIActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentListener {

    @AIView(R.id.activity_main_rfal)
    private RapidFloatingActionLayout rfaLayout;
    @AIView(R.id.activity_main_rfab)
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionButtonHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Github: wangjiegulu")
                        .setResId(R.mipmap.ico_test_d)
                        .setIconNormalColor(0xffd84315)
                        .setIconPressedColor(0xffbf360c)
                        .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("tiantian.china.2@gmail.com")
                        .setResId(R.mipmap.ico_test_c)
                        .setIconNormalColor(0xff4e342e)
                        .setIconPressedColor(0xff3e2723)
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                        .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("WangJie")
                        .setResId(R.mipmap.ico_test_b)
                        .setIconNormalColor(0xff056f00)
                        .setIconPressedColor(0xff0d5302)
                        .setLabelColor(0xff056f00)
                        .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Compose")
                        .setResId(R.mipmap.ico_test_a)
                        .setIconNormalColor(0xff283593)
                        .setIconPressedColor(0xff1a237e)
                        .setLabelColor(0xff283593)
                        .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(context, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }
    
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked icon: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }
}
```

除了xml中设置的`RapidFloatingActionLayout`和`RapidFloatingActionButton`之外，还需要`RapidFloatingActionContent`的实现类来填充和指定RFAB的内容和形式。</br>
这里提供了一个快速的`RapidFloatingActionContent`的实现解决方案:`RapidFloatingActionContentLabelList`。你可以加入多个item（RFACLabelItem，当然，不建议加太多的item，导致超过一个屏幕），然后设置每个item的颜色、图标、阴影、label的背景图片、字体大小颜色甚至动画。</br>
它的效果可参考[最上面的效果图片](https://github.com/wangjiegulu/RapidFloatingActionButton/tree/master/screenshot)或者[Google的Inbox](https://play.google.com/store/apps/details?id=com.google.android.apps.inbox)的效果。</br>
除此之外，你还需要使用`RapidFloatingActionButtonHelper`来把以上所有零散的组件组合起来。

#关于扩展：
如果你不喜欢默认提供的`RapidFloatingActionContentLabelList`，理论上你可以扩展自己的内容样式。方法是继承`com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent`，然后初始化内容布局和样式，并调用父类的`setRootView(xxx);`方法即可。如果你需要增加动画，可以重写如下方法：</br>
```
public void onExpandAnimator(AnimatorSet animatorSet);
public void onCollapseAnimator(AnimatorSet animatorSet);
```
把需要的Animator增加到animatorSet中即可</br>
`另外，作者也会不定期增加更多的RapidFloatingActionContent的扩展`



License
=======

    Copyright 2015 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
